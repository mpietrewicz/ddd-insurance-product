package pl.mpietrewicz.insurance.product.init;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ApplicantId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.OfferId;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId;
import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.Premium;
import pl.mpietrewicz.insurance.product.domainapi.OfferService;
import pl.mpietrewicz.insurance.product.domainapi.OfferingService;
import pl.mpietrewicz.insurance.product.domainapi.ProductService;
import pl.mpietrewicz.insurance.product.domainapi.dto.ApplicantData;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.InsuredRequirements;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.ProductData;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@Profile("dev")
@RequiredArgsConstructor
@Slf4j
public class DevelopmentDataInitializer implements CommandLineRunner {

    private final ProductService productService;

    private final OfferingService offeringService;

    private final OfferService offerService;

    @Override
    public void run(String... args) throws Exception {
        log.info("Development profile active. Setting initial data...");

        log.info("Setting products...");
        ProductData productNw = preapareProductData("Product NW", "12.5", true,
                "2025-01-01", "2029-12-31", 70, false);
        ProductId productNwId = productService.createProduct(productNw);

        ProductData productTu = preapareProductData("Product TU", "9", true,
                "2020-01-01", "2025-12-31", 80, true);
        ProductId productTuId = productService.createProduct(productTu);

        ProductData productAu = preapareProductData("Product AU", "13.75", false,
                "1999-01-01", "2025-10-31", 99, false);
        ProductId productAuId = productService.createProduct(productAu);

        ProductData productHl = preapareProductData("Product HEALTH", "25", false,
                "2025-07-01", "2027-12-31", 65, true);
        ProductId productHlId = productService.createProduct(productHl);

        log.info("Setting offers...");
        ApplicantData driverApplicant = prepareApplicantData("driver", "1978-08-20", false, "driver");
        OfferId offerId = offerService.createOffer(driverApplicant, LocalDate.parse("2025-09-01"));

        ApplicantData nurseApplicant = prepareApplicantData("nurse", "1986-03-06", false, "nurse");
        offerService.createOffer(nurseApplicant, LocalDate.parse("2025-07-01"));

        ApplicantData analystApplicant = prepareApplicantData("analyst", "1969-09-01", true, "analyst");
        offerService.createOffer(analystApplicant, LocalDate.parse("2025-06-01"));

        offeringService.addOffering(offerId, productNwId, true);
        offeringService.addOffering(offerId, productTuId, false);
        offeringService.addOffering(offerId, productAuId, false);

//        offerService.acceptOffer(offerId);

        log.info("Data setting finished.");
    }

    private static ProductData preapareProductData(String name, String premium, boolean promotional, String validFrom,
                                                   String validTo, int maxEntryAge, boolean forHealthyOnly) {
        return ProductData.builder()
                .name(name)
                .premium(Premium.valueOf(new BigDecimal(premium)))
                .validFrom(LocalDate.parse(validFrom))
                .validTo(LocalDate.parse(validTo))
                .insuredRequirements(InsuredRequirements.builder()
                        .maxEntryAge(maxEntryAge)
                        .forHealthyOnly(forHealthyOnly)
                        .build())
                .promotionType(PromotionType.SINGLE_PROMOTION)
                .build();
    }

    private static ApplicantData prepareApplicantData(String applicantId, String birthDate, boolean chronicDiseases,
                                                      String occupation) {
        return ApplicantData.builder()
                .applicantId(new ApplicantId(applicantId))
                .birthDate(LocalDate.parse(birthDate))
                .chronicDiseases(chronicDiseases)
                .occupation(occupation)
                .build();
    }

}
