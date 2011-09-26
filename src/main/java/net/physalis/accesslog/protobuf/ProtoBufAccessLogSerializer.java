package net.physalis.accesslog.protobuf;

import com.google.common.io.Closeables;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import net.physalis.accesslog.AccessLogEntry;
import net.physalis.accesslog.AccessLogSerializer;
import net.physalis.accesslog.protobuf.schema.Accesslog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ProtoBufAccessLogSerializer implements AccessLogSerializer {
    @Override
    public byte[] serialize(AccessLogEntry entry) {
        Accesslog.AccessLogEntry.Builder builder = Accesslog.AccessLogEntry.getDefaultInstance().newBuilderForType();
        builder.setHost(entry.getHost());
        builder.setTimestamp(entry.getTimestamp().getMillis());
        builder.setMethod(Accesslog.AccessLogEntry.Method.valueOf(entry.getMethod().getCode()));
        builder.setRequestUrl(entry.getRequestUrl());
        builder.setStatus(entry.getStatus());
        builder.setBytes(entry.getBytes());
        Accesslog.AccessLogEntry e = builder.build();

        ByteArrayOutputStream bytes = new ByteArrayOutputStream(200);
        CodedOutputStream cout = CodedOutputStream.newInstance(bytes);
        try {
            int size = e.getSerializedSize();
            cout.writeInt32NoTag(size);
            e.writeTo(cout);
            cout.flush();
            return bytes.toByteArray();
        } catch (IOException ee) {
            throw new RuntimeException(ee);
        }
    }
}
