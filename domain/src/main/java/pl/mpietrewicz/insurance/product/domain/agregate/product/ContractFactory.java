package pl.mpietrewicz.insurance.product.domain.agregate.product;

import pl.mpietrewicz.insurance.ddd.annotations.domain.DomainFactory;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ContractId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.InsuredId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;
import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.Premium;
import pl.mpietrewicz.insurance.product.domain.agregate.contract.Component;
import pl.mpietrewicz.insurance.product.domain.agregate.contract.Contract;
import pl.mpietrewicz.insurance.product.domain.agregate.offer.dto.AcceptedOffer;
import pl.mpietrewicz.insurance.product.domain.agregate.offer.dto.AcceptedProduct;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;

import java.time.LocalDate;
import java.util.List;

@DomainFactory
public class ContractFactory {

    public Contract create(AcceptedOffer acceptedOffer) {
        ContractId contractId = ContractId.generate();
        InsuredId insuredId = InsuredId.generate();
        List<Component> components = createComponents(acceptedOffer);

        return new Contract(contractId, insuredId, components);
    }

    private List<Component> createComponents(AcceptedOffer acceptedOffer) {
        LocalDate offerStartDate = acceptedOffer.getStartDate();

        return acceptedOffer.getAcceptedProducts().stream()
                .map(acceptedProduct -> createComponent(offerStartDate, acceptedProduct))
                .toList();
    }

    private Component createComponent(LocalDate startDate, AcceptedProduct acceptedProduct) {
        ProductId productId = acceptedProduct.getProductId();
        List<PromotionType> usedPromotions = acceptedProduct.getUsedPromotions();
        Premium premium = acceptedProduct.getPremium();

        return new Component(productId, startDate, premium, usedPromotions);
    }

}