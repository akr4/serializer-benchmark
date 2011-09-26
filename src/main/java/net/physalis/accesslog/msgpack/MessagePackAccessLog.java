package net.physalis.accesslog.msgpack;

import net.physalis.accesslog.AccessLog;
import net.physalis.accesslog.AccessLogEntry;
import org.msgpack.MessagePackObject;
import org.msgpack.Unpacker;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

public class MessagePackAccessLog implements AccessLog {

    private BufferedInputStream in;

    public MessagePackAccessLog(File file) throws FileNotFoundException {
        in = new BufferedInputStream(new FileInputStream(file));
    }

    @Override
    public Iterator<AccessLogEntry> iterator() {
        return new Iterator<AccessLogEntry>() {
            Iterator<MessagePackObject> underlying = new Unpacker(in).iterator();

            @Override
            public boolean hasNext() {
                return underlying.hasNext();
            }

            @Override
            public AccessLogEntry next() {
                return underlying.next().convert(AccessLogEntrySerializationAdapter.class).toAccessLogEntry();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public void close() throws IOException {
        in.close();
    }
}
