package pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;

@Component
public class ProductIdConverter implements Converter<String, ProductId> {

    @Override
    public ProductId convert(String source) {
        return new ProductId(source);
    }

}