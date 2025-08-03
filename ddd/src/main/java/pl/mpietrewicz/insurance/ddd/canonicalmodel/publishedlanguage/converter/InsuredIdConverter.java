package pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.InsuredId;

@Component
public class InsuredIdConverter implements Converter<String, InsuredId> {

    @Override
    public InsuredId convert(String source) {
        return new InsuredId(source);
    }

}