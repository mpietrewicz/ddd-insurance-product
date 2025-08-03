package pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.OfferId;

@Component
public class OfferIdConverter implements Converter<String, OfferId> {

    @Override
    public OfferId convert(String source) {
        return new OfferId(source);
    }

}