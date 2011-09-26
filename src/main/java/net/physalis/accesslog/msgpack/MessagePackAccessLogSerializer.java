package net.physalis.accesslog.msgpack;

import net.physalis.accesslog.AccessLogEntry;
import net.physalis.accesslog.AccessLogSerializer;
import org.msgpack.MessagePack;

public class MessagePackAccessLogSerializer implements AccessLogSerializer {
    @Override
    public byte[] serialize(AccessLogEntry entry) {
        return MessagePack.pack(new AccessLogEntrySerializationAdapter(entry));
    }
}
