package pl.mpietrewicz.insurance.product.domain.agregate.product;

import pl.mpietrewicz.insurance.ddd.annotations.domain.DomainFactory;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;
import pl.mpietrewicz.insurance.product.domain.agregate.product.eligibility.AgeEligibility;
import pl.mpietrewicz.insurance.product.domain.agregate.product.eligibility.HealthEligibility;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.InsuredRequirements;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.ProductData;

import java.util.List;

@DomainFactory
public class ProductFactory {

    public Product create(ProductData productData) {
        ProductId productId = ProductId.generate();
        BasicProductData basicProductData = BasicProductData.builder()
                .name(productData.getName())
                .premium(productData.getPremium())
                .validFrom(productData.getValidFrom())
                .validTo(productData.getValidTo())
                .promotionType(productData.getPromotionType())
                .build();
        List<Eligibility> eligibilityList = createEligibilityList(productData.getInsuredRequirements());

        return new Product(productId, basicProductData, eligibilityList);
    }

    private List<Eligibility> createEligibilityList(InsuredRequirements insuredRequirements) {
        int maxEntryAge = insuredRequirements.getMaxEntryAge();
        boolean forHealthyOnly = insuredRequirements.isForHealthyOnly();
        return List.of(
                new AgeEligibility(0, maxEntryAge),
                new HealthEligibility(forHealthyOnly)
        );
    }

}