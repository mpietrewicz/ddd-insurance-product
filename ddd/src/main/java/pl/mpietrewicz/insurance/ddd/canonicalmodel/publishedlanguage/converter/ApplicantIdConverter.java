package pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ApplicantId;

@Component
public class ApplicantIdConverter implements Converter<String, ApplicantId> {

    @Override
    public ApplicantId convert(String source) {
        return new ApplicantId(source);
    }

}