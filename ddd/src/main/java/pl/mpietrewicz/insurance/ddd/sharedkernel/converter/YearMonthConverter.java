package pl.mpietrewicz.insurance.ddd.sharedkernel.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

@Converter(autoApply = true)
public class YearMonthConverter implements AttributeConverter<YearMonth, String> {

    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");

    @Override
    public String convertToDatabaseColumn(YearMonth attribute) {
        return attribute.format(FORMATTER);
    }

    @Override
    public YearMonth convertToEntityAttribute(String dbDate) {
        return YearMonth.parse(dbDate, FORMATTER);
    }

}