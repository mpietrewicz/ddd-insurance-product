package pl.mpietrewicz.insurance.product.domain.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ApplicantId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.InsuredId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.OfferId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;
import pl.mpietrewicz.insurance.ddd.sharedkernel.exception.OfferNotFoundException;
import pl.mpietrewicz.insurance.ddd.sharedkernel.exception.ProductNotFoundException;
import pl.mpietrewicz.insurance.product.domain.agregate.contract.Contract;
import pl.mpietrewicz.insurance.product.domain.agregate.offer.Offer;
import pl.mpietrewicz.insurance.product.domain.agregate.product.Product;
import pl.mpietrewicz.insurance.product.domain.repository.ContractRepository;
import pl.mpietrewicz.insurance.product.domain.repository.OfferRepository;
import pl.mpietrewicz.insurance.product.domain.repository.ProductRepository;
import pl.mpietrewicz.insurance.product.domain.service.promotion.PromotionService;
import pl.mpietrewicz.insurance.product.domainapi.PromotionApplicationService;
import pl.mpietrewicz.insurance.product.domainapi.dto.offering.OfferingId;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PromotionApplicationServiceImpl implements PromotionApplicationService {

    private final OfferRepository offerRepository;

    private final ContractRepository contractRepository;

    private final ProductRepository productRepository;

    private final PromotionService promotionService;

    @Override
    public List<PromotionType> getAvailablePromotions(OfferingId offeringId) {
        Offer offer = getOffer(offeringId.getOfferId());
        ProductId productId = offer.getProductId(offeringId.getOfferingId());
        Product product = getProduct(productId);
        ApplicantId applicantId = offer.getApplicantId();
        InsuredId insuredId = new InsuredId(applicantId);
        List<Contract> allContracts = contractRepository.findBy(insuredId);

        return promotionService.getAvailablePromotions(offer, product, allContracts);
    }

    @Override
    public void addPromotion(PromotionType promotionType, OfferingId offeringId) {
        Offer offer = getOffer(offeringId.getOfferId());
        ProductId productId = offer.getProductId(offeringId.getOfferingId());
        Product product = getProduct(productId);
        ApplicantId applicantId = offer.getApplicantId();
        InsuredId insuredId = new InsuredId(applicantId);
        List<Contract> allContracts = contractRepository.findBy(insuredId);

        promotionService.addPromotion(promotionType, offer, product, allContracts);
    }

    @Override
    public void removePromotion(PromotionType promotionType, OfferingId offeringId) {
        Offer offer = getOffer(offeringId.getOfferId());
        ProductId productId = offer.getProductId(offeringId.getOfferingId());

        offer.removePromotion(promotionType, productId);
    }

    private Offer getOffer(OfferId offerId) {
        return offerRepository.load(offerId)
                .orElseThrow(() -> new OfferNotFoundException(offerId));
    }

    private Product getProduct(ProductId productId) {
        return productRepository.load(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

}
