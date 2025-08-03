package pl.mpietrewicz.insurance.ddd.sharedkernel.util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.atomic.AtomicLong;

public class TimestampGenerator {

    private static final AtomicLong counter = new AtomicLong(0);
    private static Instant lastInstant = Instant.now();

    public static synchronized Instant getUniqueTimestamp() {
        Instant now = Instant.now();
        if (now.equals(lastInstant)) {
            return now.plus(counter.incrementAndGet(), ChronoUnit.NANOS);
        } else {
            lastInstant = now;
            counter.set(0);
            return now;
        }
    }

}