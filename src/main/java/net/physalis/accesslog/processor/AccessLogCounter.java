package net.physalis.accesslog.processor;

import com.google.common.io.Closeables;
import net.physalis.accesslog.AccessLog;
import net.physalis.accesslog.AccessLogEntry;
import net.physalis.accesslog.plain.PlainAccessLog;
import net.physalis.accesslog.protobuf.ProtoBufAccessLog;
import net.physalis.accesslog.util.StopWatch;
import net.physalis.accesslog.msgpack.MessagePackAccessLog;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

public class AccessLogCounter {
    public long count(AccessLog accessLog) {
        int count = 0;
        try {
            for (AccessLogEntry e : accessLog) {
                if (e != null) {
                    e.getRequestUrl();
                    count++;
                }
            }
        } finally {
            Closeables.closeQuietly(accessLog);
        }
        return count;
    }

    public static void main(String[] args) throws IOException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        AccessLog accessLog = new ProtoBufAccessLog(new File("access_log.protobuf"));
//        AccessLog accessLog = new MessagePackAccessLog(new File("access_log.msgpack"));
//        AccessLog accessLog = new PlainAccessLog(new File("access_log_Jul95"));
        long count = new AccessLogCounter().count(accessLog);
        stopWatch.end();
        System.out.format("read %s entries in %s.", DecimalFormat.getInstance().format(count), stopWatch.print());
    }
}
