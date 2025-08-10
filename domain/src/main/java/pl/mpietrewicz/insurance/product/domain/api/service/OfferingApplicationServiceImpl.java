package pl.mpietrewicz.insurance.product.domain.api.service;

import lombok.RequiredArgsConstructor;
import pl.mpietrewicz.insurance.ddd.annotations.application.ApplicationService;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ApplicantId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.InsuredId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.OfferId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;
import pl.mpietrewicz.insurance.ddd.sharedkernel.exception.OfferNotFoundException;
import pl.mpietrewicz.insurance.ddd.sharedkernel.exception.ProductNotFoundException;
import pl.mpietrewicz.insurance.product.domain.agregate.contract.Contract;
import pl.mpietrewicz.insurance.product.domain.agregate.offer.Offer;
import pl.mpietrewicz.insurance.product.domain.agregate.product.Product;
import pl.mpietrewicz.insurance.product.domain.dto.OfferingContext;
import pl.mpietrewicz.insurance.product.domain.repository.ContractRepository;
import pl.mpietrewicz.insurance.product.domain.repository.OfferRepository;
import pl.mpietrewicz.insurance.product.domain.repository.ProductRepository;
import pl.mpietrewicz.insurance.product.domain.service.aplicant.ApplicantDataProvider;
import pl.mpietrewicz.insurance.product.domain.service.offering.OfferingService;
import pl.mpietrewicz.insurance.product.domainapi.OfferingApplicationService;
import pl.mpietrewicz.insurance.product.domainapi.dto.ApplicantData;
import pl.mpietrewicz.insurance.product.domainapi.dto.offering.AvailableOffering;
import pl.mpietrewicz.insurance.product.domainapi.dto.offering.OfferingKey;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;

import java.util.List;

@ApplicationService
@RequiredArgsConstructor
public class OfferingApplicationServiceImpl implements OfferingApplicationService {

    private final OfferingService offeringService;

    private final ProductRepository productRepository;

    private final OfferRepository offerRepository;

    private final ContractRepository contractRepository;

    private final ApplicantDataProvider applicantDataProvider;

    @Override
    public List<AvailableOffering> getAvailableOfferings(OfferId offerId) {
        Offer offer = getOffer(offerId);
        ApplicantId applicantId = offer.getApplicantId();
        InsuredId insuredId = new InsuredId(applicantId);
        List<Contract> allContracts = contractRepository.findBy(insuredId);
        List<Product> allProducts = productRepository.loadAll();
        ApplicantData applicantData = applicantDataProvider.get(applicantId);

        return offeringService.getAvailableOfferings(applicantData, allProducts, allContracts, offer);
    }

    @Override
    public OfferingKey addOffering(OfferId offerId, ProductId productId, PromotionType promotionType) {
        Offer offer = getOffer(offerId);
        Product product = getProduct(productId);
        ApplicantId applicantId = offer.getApplicantId();
        InsuredId insuredId = new InsuredId(applicantId);

        List<Contract> allContracts = contractRepository.findBy(insuredId);
        List<Product> allProducts = productRepository.loadAll();
        ApplicantData applicantData = applicantDataProvider.get(applicantId);
        OfferingContext offeringContext = new OfferingContext(offer, product);

        return offeringService.addOffering(offeringContext, applicantData, allContracts, allProducts);
    }

    @Override
    public void removeOffering(OfferingKey offeringKey) {
        Offer offer = getOffer(offeringKey);
        offer.removeOffering(offeringKey);
    }

    private Offer getOffer(OfferId offerId) {
        return offerRepository.load(offerId)
                .orElseThrow(() -> new OfferNotFoundException(offerId));
    }

    private Offer getOffer(OfferingKey offeringKey) {
        OfferId offerId = offeringKey.getOfferId();
        return getOffer(offerId);
    }

    private Product getProduct(ProductId productId) {
        return productRepository.load(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

}
