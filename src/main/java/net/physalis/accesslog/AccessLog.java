package net.physalis.accesslog;

import java.io.Closeable;

public interface AccessLog extends Iterable<AccessLogEntry>, Closeable {
}
