package net.physalis.accesslog.processor;

import com.google.common.io.Closeables;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import net.physalis.accesslog.AccessLogSerializer;
import net.physalis.accesslog.msgpack.AccessLogEntrySerializationAdapter;
import net.physalis.accesslog.msgpack.MessagePackAccessLogSerializer;
import net.physalis.accesslog.parser.AccessLogParser;
import net.physalis.accesslog.parser.CannotParseException;
import net.physalis.accesslog.protobuf.ProtoBufAccessLogSerializer;
import net.physalis.accesslog.protobuf.schema.Accesslog;
import net.physalis.accesslog.util.StopWatch;
import org.msgpack.MessagePack;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.DecimalFormat;

public class AccessLogPacker {
    public void pack(final AccessLogSerializer serializer, File src, File dst) throws IOException {
        final AccessLogParser parser = new AccessLogParser();

        final BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(dst));
        int count = 0;
        try {
            count = Files.readLines(src, Charset.forName("UTF-8"), new LineProcessor<Integer>() {
                private int count;

                @Override
                public boolean processLine(String line) throws IOException {
                    try {
                        byte[] bytes = serializer.serialize(parser.parseLine(line));
                        out.write(bytes);
                        count++;
                    } catch (CannotParseException ignore) {
                    }
                    return true;
                }

                @Override
                public Integer getResult() {
                    return count;
                }
            });
        } finally {
            Closeables.closeQuietly(out);
        }
        System.out.format("serialized %s entries\n", DecimalFormat.getInstance().format(count));
    }

    public static void main(String[] args) throws IOException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        new AccessLogPacker().pack(new ProtoBufAccessLogSerializer(), new File("access_log_Jul95"), new File("access_log.protobuf"));
//        new AccessLogPacker().pack(new MessagePackAccessLogSerializer(), new File("access_log_Jul95"), new File("access_log.msgpack"));
        stopWatch.end();
    }
}
