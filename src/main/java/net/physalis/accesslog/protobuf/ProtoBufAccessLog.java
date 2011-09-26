package net.physalis.accesslog.protobuf;

import com.google.common.io.Closeables;
import com.google.protobuf.CodedInputStream;
import net.physalis.accesslog.AccessLog;
import net.physalis.accesslog.AccessLogEntry;
import net.physalis.accesslog.Method;
import net.physalis.accesslog.protobuf.schema.Accesslog;
import org.jboss.xnio.channels.UnsupportedOptionException;
import org.joda.time.DateTime;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class ProtoBufAccessLog implements AccessLog {

    private InputStream in;
    private CodedInputStream cin;

    public ProtoBufAccessLog(File file) throws FileNotFoundException {
        in = new BufferedInputStream(new FileInputStream(file));
        cin = CodedInputStream.newInstance(in);
        cin.setSizeLimit(256 * 1024 * 1024);
    }

    @Override
    public Iterator<AccessLogEntry> iterator() {
        return new Iterator<AccessLogEntry>() {
            @Override
            public boolean hasNext() {
                try {
                    return in.available() > 0;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public AccessLogEntry next() {
                try {
                    int size = cin.readInt32();
                    byte[] bytes = cin.readRawBytes(size);
                    Accesslog.AccessLogEntry e = Accesslog.AccessLogEntry.parseFrom(bytes);
                    AccessLogEntry entry = new AccessLogEntry(e.getHost(), new DateTime(e.getTimestamp()), Method.findByCode(e.getMethod().getNumber()), e.getRequestUrl(), e.getStatus(), e.getBytes());
                    return entry;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void remove() {
                throw new UnsupportedOptionException();
            }
        };
    }

    @Override
    public void close() throws IOException {
        Closeables.closeQuietly(in);
    }
}
