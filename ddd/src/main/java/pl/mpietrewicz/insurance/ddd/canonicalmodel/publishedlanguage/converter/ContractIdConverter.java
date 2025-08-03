package pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ContractId;

@Component
public class ContractIdConverter implements Converter<String, ContractId> {

    @Override
    public ContractId convert(String source) {
        return new ContractId(source);
    }

}