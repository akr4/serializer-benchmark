package net.physalis.accesslog.util;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.PeriodFormatterBuilder;
import sun.jvm.hotspot.utilities.Interval;

public class StopWatch {

    private DateTime start;
    private DateTime end;

    public void start() {
        start = new DateTime();
    }

    public void end() {
        end = new DateTime();
    }

    public long millis() {
        return new Duration(start, end).getMillis();
    }

    public String print() {
        return new Duration(start, end).toPeriod().toString(new PeriodFormatterBuilder().appendSecondsWithMillis().toFormatter());
    }
}
