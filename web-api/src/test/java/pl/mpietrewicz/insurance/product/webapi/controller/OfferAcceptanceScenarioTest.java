package pl.mpietrewicz.insurance.product.webapi.controller;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ApplicantId;
import pl.mpietrewicz.insurance.product.TestApiApplication;
import pl.mpietrewicz.insurance.product.webapi.dto.request.ApplicantDataRequest;
import pl.mpietrewicz.insurance.product.webapi.dto.request.CreateOfferRequest;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApiApplication.class)
@ActiveProfiles("test")
public class OfferAcceptanceScenarioTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Nested
    @DisplayName("Get Applicant Links Scenarios")
    class GetApplicantLinks {

        @Test
        @DisplayName("Should return only 'create-offer' link for a new applicant")
        void shouldReturnOnlyCreateOfferLinkForNewApplicant() {
            // ARRANGE: Tworzymy dane dla nowego aplikanta, który może stworzyć ofertę
            ApplicantId applicantId = new ApplicantId("1");
            ApplicantDataRequest applicantData = ApplicantDataRequest.builder()
                    .applicantId(applicantId)
                    .birthDate(LocalDate.now().minusYears(30)) // Wiek pozwalający na ofertę
                    .chronicDiseases(false)
                    .occupation("developer")
                    .build();

            // ACT: Odpytujemy o dostępne akcje
            ResponseEntity<String> response = restTemplate.postForEntity("/applicant", applicantData, String.class);

            // ASSERT: Sprawdzamy, czy odpowiedź jest poprawna
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            String responseBody = response.getBody();

            // Sprawdzamy, czy link 'create-offer' istnieje
            String createOfferLink = JsonPath.read(responseBody, "$._links.create-offer.href");
            assertThat(createOfferLink).isNotNull();

            // Sprawdzamy, czy link 'offers' NIE istnieje
            assertThatThrownBy(() -> JsonPath.read(responseBody, "$._links.offers.href"))
                    .isInstanceOf(PathNotFoundException.class);
        }

        @Test
        @DisplayName("Should return both links when applicant has existing offers and can create more")
        void shouldReturnBothLinksWhenApplicantHasOffersAndCanCreateMore() {
            // ARRANGE 1: Tworzymy aplikanta i ofertę dla niego, aby spełnić warunek `existsByApplicantId`
            ApplicantId applicantId = new ApplicantId("1");
            ApplicantDataRequest applicantData = ApplicantDataRequest.builder()
                    .applicantId(applicantId)
                    .birthDate(LocalDate.now().minusYears(30))
                    .chronicDiseases(false)
                    .occupation("developer")
                    .build();

            CreateOfferRequest createOfferRequest = CreateOfferRequest.builder()
                    .startDate(LocalDate.now().plusDays(1))
                    .applicantData(applicantData)
                    .build();

            // Tworzymy ofertę, aby istniała w bazie danych
            restTemplate.postForEntity("/applicants/{applicantId}/offers", createOfferRequest, String.class, applicantId.getId());

            // ACT: Odpytujemy o dostępne akcje dla tego samego aplikanta
            ResponseEntity<String> response = restTemplate.postForEntity("/applicant", applicantData, String.class);

            // ASSERT: Sprawdzamy, czy odpowiedź zawiera oba linki
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            String responseBody = response.getBody();

            String createOfferLink = JsonPath.read(responseBody, "$._links.create-offer.href");
            assertThat(createOfferLink).isNotNull();

            String getOffersLink = JsonPath.read(responseBody, "$._links.offers.href");
            assertThat(getOffersLink).isNotNull();
        }
    }

}
