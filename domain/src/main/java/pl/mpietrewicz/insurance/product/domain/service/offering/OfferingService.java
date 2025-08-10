package pl.mpietrewicz.insurance.product.domain.service.offering;

import pl.mpietrewicz.insurance.product.domain.agregate.contract.Contract;
import pl.mpietrewicz.insurance.product.domain.agregate.offer.Offer;
import pl.mpietrewicz.insurance.product.domain.agregate.product.Product;
import pl.mpietrewicz.insurance.product.domain.dto.OfferingContext;
import pl.mpietrewicz.insurance.product.domainapi.dto.ApplicantData;
import pl.mpietrewicz.insurance.product.domainapi.dto.offering.AvailableOffering;
import pl.mpietrewicz.insurance.product.domainapi.dto.offering.OfferingKey;

import java.util.List;

public interface OfferingService {

    List<AvailableOffering> getAvailableOfferings(ApplicantData applicantData, List<Product> allProducts,
                                                  List<Contract> allContracts, Offer offer);

    OfferingKey addOffering(OfferingContext offeringContext, ApplicantData applicantData,
                            List<Contract> allContracts, List<Product> allProducts);

}
