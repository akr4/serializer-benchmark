package net.physalis.accesslog.msgpack;

import net.physalis.accesslog.AccessLogEntry;
import net.physalis.accesslog.Method;
import org.joda.time.DateTime;
import org.msgpack.annotation.MessagePackMessage;

@MessagePackMessage
public class AccessLogEntrySerializationAdapter {

    public String host;
    public long timestamp;
    public int method;
    public String requestUrl;
    public int status;
    public int bytes;

    public AccessLogEntrySerializationAdapter() {
    }

    public AccessLogEntrySerializationAdapter(AccessLogEntry entry) {
        host = entry.getHost();
        timestamp = entry.getTimestamp().getMillis();
        method = entry.getMethod().ordinal();
        requestUrl = entry.getRequestUrl();
        status = entry.getStatus();
        bytes = entry.getBytes();
    }

    public AccessLogEntry toAccessLogEntry() {
        return new AccessLogEntry(host, new DateTime(timestamp), Method.values()[method], requestUrl, status, bytes);
    }
}
