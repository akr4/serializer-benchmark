package net.physalis.accesslog;

public interface AccessLogSerializer {

    byte[] serialize(AccessLogEntry entry);
}
