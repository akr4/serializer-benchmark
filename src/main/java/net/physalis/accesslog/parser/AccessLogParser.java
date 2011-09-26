package net.physalis.accesslog.parser;

import net.physalis.accesslog.AccessLogEntry;
import net.physalis.accesslog.Method;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccessLogParser {
    // 199.72.81.55 - - [01/Jul/1995:00:00:01 -0400] "GET /history/apollo/ HTTP/1.0" 200 6245
    private static final Pattern PATTERN = Pattern.compile("^(.+)"
            + " - - "
            + "\\[(\\d{2}/\\w{3}/\\d{4}:\\d{2}:\\d{2}:\\d{2} [+-][0-9]{4})\\]"
            + " \"([GET|POST|HEAD|PUT|DELETE]+) ([^ ]+)(?:.*)?\""
            + " (\\d+)"
            + " (.+)$");
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormat.forPattern("dd/MMM/yyyy:HH:mm:ss Z").withLocale(Locale.US);

    public AccessLogEntry parseLine(String line) throws CannotParseException {
        if (line == null) {
            throw new CannotParseException("empty line");
        }
        Matcher m = PATTERN.matcher(line);
        if (m.matches()) {
            String host = m.group(1);
            String timestamp = m.group(2);
            String method = m.group(3);
            String requestUrl = m.group(4);
            String status = m.group(5);
            String bytes = m.group(6);
            return new AccessLogEntry(host, DATE_TIME_FORMAT.parseDateTime(timestamp), Method.valueOf(method), requestUrl, Integer.parseInt(status),
                    bytes.equals("-") ? 0 : Integer.parseInt(bytes));
        } else {
            throw new CannotParseException("cannot parse [" + line + "]");
        }
    }
}
