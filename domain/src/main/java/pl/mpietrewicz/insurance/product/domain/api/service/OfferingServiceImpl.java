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
import pl.mpietrewicz.insurance.product.domain.service.offer.OfferingAvailabilityService;
import pl.mpietrewicz.insurance.product.domain.service.offering.OfferingContextFactory;
import pl.mpietrewicz.insurance.product.domainapi.OfferingService;
import pl.mpietrewicz.insurance.product.domainapi.dto.ApplicantData;
import pl.mpietrewicz.insurance.product.domainapi.dto.AvailableOffering;

import java.util.List;

@ApplicationService
@RequiredArgsConstructor
public class OfferingServiceImpl implements OfferingService {

    private final OfferingAvailabilityService offeringAvailabilityService;

    private final ProductRepository productRepository;

    private final OfferRepository offerRepository;

    private final ContractRepository contractRepository;

    private final ApplicantDataProvider applicantDataProvider;

    private final OfferingContextFactory offeringContextFactory;

    @Override
    public List<AvailableOffering> getAvailableOfferings(OfferId offerId) {
        Offer offer = getOffer(offerId);
        ApplicantId applicantId = offer.getApplicantId();
        InsuredId insuredId = new InsuredId(applicantId);
        List<Contract> allContracts = contractRepository.getAllContractsFor(insuredId);
        List<Product> allProducts = productRepository.loadAll();
        ApplicantData applicantData = applicantDataProvider.get(applicantId);

        return offeringAvailabilityService.getAvailableOfferings(applicantData, allProducts, allContracts, offer);
    }

    @Override
    public Long addOffering(OfferId offerId, ProductId productId, boolean promotion) {
        Offer offer = getOffer(offerId);
        Product product = getProduct(productId);
        ApplicantId applicantId = offer.getApplicantId();
        InsuredId insuredId = new InsuredId(applicantId);

        List<Contract> allContracts = contractRepository.getAllContractsFor(insuredId);
        List<Product> allProducts = productRepository.loadAll();
        ApplicantData applicantData = applicantDataProvider.get(applicantId);
        OfferingContext offeringContext = offeringContextFactory.create(offer, product, promotion);

        return offeringAvailabilityService.addOffering(offeringContext, applicantData, allContracts, allProducts);
    }

    @Override
    public void removeOffering(OfferId offerId, Long offeringId) {
        Offer offer = getOffer(offerId);
        offer.removeOffering(offeringId);
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
