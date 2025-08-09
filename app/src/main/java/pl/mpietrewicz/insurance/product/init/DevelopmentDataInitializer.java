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
import pl.mpietrewicz.insurance.product.domainapi.OfferApplicationService;
import pl.mpietrewicz.insurance.product.domainapi.OfferingApplicationService;
import pl.mpietrewicz.insurance.product.domainapi.ProductApplicationService;
import pl.mpietrewicz.insurance.product.domainapi.dto.ApplicantData;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.InsuredRequirements;
import pl.mpietrewicz.insurance.product.domainapi.dto.product.ProductData;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType.DOUBLE_PROMOTION;
import static pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType.NO_PROMOTION;
import static pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType.PROMOTION_AFTER_TWO_YEARS;
import static pl.mpietrewicz.insurance.product.domainapi.dto.product.PromotionType.SINGLE_PROMOTION;

@Component
@Profile("dev")
@RequiredArgsConstructor
@Slf4j
public class DevelopmentDataInitializer implements CommandLineRunner {

    private final ProductApplicationService productApplicationService;

    private final OfferingApplicationService offeringApplicationService;

    private final OfferApplicationService offerApplicationService;

    @Override
    public void run(String... args) throws Exception {
        log.info("Development profile active. Setting initial data...");

        log.info("Setting products...");
        ProductData productNw = preapareProductData("Product NW", "12.5",
                "2025-01-01", "2029-12-31", 70, false);
        ProductId productNwId = productApplicationService.createProduct(productNw);

        ProductData productTu = preapareProductData("Product TU", "9",
                "2020-01-01", "2025-12-31", 80, true);
        ProductId productTuId = productApplicationService.createProduct(productTu);

        ProductData productAu = preapareProductData("Product AU", "13.75",
                "1999-01-01", "2025-10-31", 99, false);
        ProductId productAuId = productApplicationService.createProduct(productAu);

        ProductData productHl = preapareProductData("Product HEALTH", "25",
                "2025-07-01", "2027-12-31", 65, true);
        ProductId productHlId = productApplicationService.createProduct(productHl);

        log.info("Setting offers...");
        ApplicantData driverApplicant = prepareApplicantData("driver", "1978-08-20", false, "driver");
        OfferId offerId = offerApplicationService.createOffer(driverApplicant, LocalDate.parse("2025-09-01"));

        ApplicantData nurseApplicant = prepareApplicantData("nurse", "1986-03-06", false, "nurse");
        offerApplicationService.createOffer(nurseApplicant, LocalDate.parse("2025-07-01"));

        ApplicantData analystApplicant = prepareApplicantData("analyst", "1969-09-01", true, "analyst");
        offerApplicationService.createOffer(analystApplicant, LocalDate.parse("2025-06-01"));

        offeringApplicationService.addOffering(offerId, productNwId, SINGLE_PROMOTION);
        offeringApplicationService.addOffering(offerId, productTuId, DOUBLE_PROMOTION);
        offeringApplicationService.addOffering(offerId, productAuId, PROMOTION_AFTER_TWO_YEARS);

//        offerService.acceptOffer(offerId);

        log.info("Data setting finished.");
    }

    private static ProductData preapareProductData(String name, String premium, String validFrom, String validTo,
                                                   int maxEntryAge, boolean forHealthyOnly) {
        return ProductData.builder()
                .name(name)
                .premium(Premium.valueOf(new BigDecimal(premium)))
                .validFrom(LocalDate.parse(validFrom))
                .validTo(LocalDate.parse(validTo))
                .insuredRequirements(InsuredRequirements.builder()
                        .maxEntryAge(maxEntryAge)
                        .forHealthyOnly(forHealthyOnly)
                        .build())
                .promotionTypes(List.of(NO_PROMOTION, SINGLE_PROMOTION, DOUBLE_PROMOTION, PROMOTION_AFTER_TWO_YEARS))
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
