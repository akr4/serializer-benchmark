package net.physalis.accesslog;

import net.physalis.accesslog.msgpack.MessagePackAccessLog;
import net.physalis.accesslog.msgpack.MessagePackAccessLogSerializer;
import net.physalis.accesslog.plain.PlainAccessLog;
import net.physalis.accesslog.processor.AccessLogCounter;
import net.physalis.accesslog.processor.AccessLogPacker;
import net.physalis.accesslog.protobuf.ProtoBufAccessLog;
import net.physalis.accesslog.protobuf.ProtoBufAccessLogSerializer;
import net.physalis.accesslog.util.StopWatch;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

public class Benchmark {

    static class Execution {
        private static final int WARMUP = 1;
        Execution(String name) {
            this.name = name;
        }
        private String name;
        private int count;
        private int countForComputation;
        private long elapsedMillisTotal;
        public void completed(long elapsedMillis) {
            count++;
            if (WARMUP < count) {
                elapsedMillisTotal += elapsedMillis;
                countForComputation++;
            }
        }
        public double getAverageMillis() {
            return (double) elapsedMillisTotal / countForComputation;
        }
        public int getCount() {
            return countForComputation;
        }

        public String getName() {
            return name;
        }
    }

    public static void main(String[] args) throws IOException {
        StopWatch stopWatch = new StopWatch();

        {
            Execution execution = new Execution("serialize-msgpack");
            for (int i = 0; i < 11; i++) {
                stopWatch.start();
                new AccessLogPacker().pack(new MessagePackAccessLogSerializer(), new File("access_log_Jul95"), new File("access_log.msgpack"));
                stopWatch.end();
                execution.completed(stopWatch.millis());
            }
            printResult(execution);
        }
        {
            Execution execution = new Execution("serialize-protobuf");
            for (int i = 0; i < 11; i++) {
                stopWatch.start();
                new AccessLogPacker().pack(new ProtoBufAccessLogSerializer(), new File("access_log_Jul95"), new File("access_log.protobuf"));
                stopWatch.end();
                execution.completed(stopWatch.millis());
            }
            printResult(execution);
        }
        {
            Execution execution = new Execution("deserialize-msgpack");
            for (int i = 0; i < 11; i++) {
                stopWatch.start();
                new AccessLogCounter().count(new MessagePackAccessLog(new File("access_log.msgpack")));
                stopWatch.end();
                execution.completed(stopWatch.millis());
            }
            printResult(execution);
        }
        {
            Execution execution = new Execution("deserialize-protobuf");
            for (int i = 0; i < 11; i++) {
                stopWatch.start();
                new AccessLogCounter().count(new ProtoBufAccessLog(new File("access_log.protobuf")));
                stopWatch.end();
                execution.completed(stopWatch.millis());
            }
            printResult(execution);
        }
        {
            Execution execution = new Execution("deserialize-plain");
            for (int i = 0; i < 11; i++) {
                stopWatch.start();
                new AccessLogCounter().count(new PlainAccessLog(new File("access_log_Jul95")));
                stopWatch.end();
                execution.completed(stopWatch.millis());
            }
            printResult(execution);
        }
    }

    private static void printResult(Execution e) {
        System.out.format("Result: %s\n", e.getName());
        System.out.println("====================================");
        System.out.format("execution: %s\n", DecimalFormat.getInstance().format(e.getCount()));
        System.out.format("average: %s\n", DecimalFormat.getInstance().format(e.getAverageMillis()));
        System.out.println("");
    }
}
