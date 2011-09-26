package net.physalis.accesslog;

import org.joda.time.DateTime;

public class AccessLogEntry {
    private String host;
    private DateTime timestamp;
    private Method method;
    private String requestUrl;
    private int status;
    private int bytes;

    public AccessLogEntry(String host, DateTime timestamp, Method method, String requestUrl, int status, int bytes) {
        this.host = host;
        this.timestamp = timestamp;
        this.method = method;
        this.requestUrl = requestUrl;
        this.status = status;
        this.bytes = bytes;
    }

    public String getHost() {
        return host;
    }

    public DateTime getTimestamp() {
        return timestamp;
    }

    public Method getMethod() {
        return method;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public int getStatus() {
        return status;
    }

    public int getBytes() {
        return bytes;
    }

    @Override
    public String toString() {
        return "AccessLogEntry{" +
            "host='" + host + '\'' +
            ", timestamp=" + timestamp +
            ", method=" + method +
            ", requestUrl='" + requestUrl + '\'' +
            ", status=" + status +
            ", bytes=" + bytes +
            '}';
    }
}
