package pl.mpietrewicz.insurance.product.commonutil;

import java.time.LocalDate;

public final class DateComparison {

    private DateComparison() {
    }

    public static boolean isAfterOrEqual(LocalDate self, LocalDate other) {
        if (self == null || other == null) {
            throw new IllegalArgumentException("One of the arguments is null.");
        }
        return !self.isBefore(other);
    }

    public static boolean isBeforeOrEqual(LocalDate self, LocalDate other) {
        if (self == null || other == null) {
            throw new IllegalArgumentException("One of the arguments is null.");
        }
        return !self.isAfter(other);
    }

}
