package net.physalis.accesslog.plain;

import com.google.common.io.Files;
import net.physalis.accesslog.AccessLog;
import net.physalis.accesslog.AccessLogEntry;
import net.physalis.accesslog.parser.AccessLogParser;
import net.physalis.accesslog.parser.CannotParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class PlainAccessLog implements AccessLog {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlainAccessLog.class);

    private BufferedReader in;

    public PlainAccessLog(File file) throws FileNotFoundException {
        in = Files.newReader(file, Charset.forName("UTF-8"));
    }

    @Override
    public void close() throws IOException {
        in.close();
    }

    @Override
    public Iterator<AccessLogEntry> iterator() {
        return new Iterator<AccessLogEntry>() {

            private AccessLogParser parser = new AccessLogParser();

            @Override
            public boolean hasNext() {
                try {
                    return in.ready();
                } catch (IOException ignore) {
                    return false;
                }
            }

            @Override
            public AccessLogEntry next() {
                String line;
                try {
                    line = in.readLine();
                } catch (IOException ignore) {
                    throw new NoSuchElementException();
                }
                try {
                    return parser.parseLine(line);
                } catch (CannotParseException e) {
                    LOGGER.warn(e.getMessage());
                    return null;
                }
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
