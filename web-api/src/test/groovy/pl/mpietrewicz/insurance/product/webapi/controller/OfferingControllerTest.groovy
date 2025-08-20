package pl.mpietrewicz.insurance.product.webapi.controller

import org.springframework.core.io.ClassPathResource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.OfferId
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId
import pl.mpietrewicz.insurance.product.domainapi.offer.OfferApplicationService
import pl.mpietrewicz.insurance.product.webapi.service.adapter.OfferServiceAdapter
import pl.mpietrewicz.insurance.product.webapi.service.adapter.OfferingServiceAdapter
import pl.mpietrewicz.insurance.product.webapi.dto.request.InsuredEligibilityRequest
import pl.mpietrewicz.insurance.product.webapi.dto.response.AvailableOfferingModel
import pl.mpietrewicz.insurance.product.webapi.dto.response.OfferingModel
import pl.mpietrewicz.insurance.product.webapi.service.model.OfferingModelService
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDate

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class OfferingControllerTest extends Specification {

    def offerApiService = Mock(OfferApplicationService)

    def offeringModelProvider = Mock(OfferingModelService)

    def offerAvailabilityService = Mock(OfferServiceAdapter)

    def offeringServiceAdapter = Mock(OfferingServiceAdapter)

    @Subject
    MockMvc mockMvc

    def setup() {
        def offerController = new OfferingController(offerApiService, offeringModelProvider, offerAvailabilityService,
                offeringServiceAdapter)
        mockMvc = MockMvcBuilders.standaloneSetup(offerController).build()
    }

    def "should return all offerings in offer"() {
        given:
        def offerId = new OfferId("1")

        def offeringModel1 = createOfferingModel(11L, "101", "2025-01-01", false)
        def offeringModel2 = createOfferingModel(12L, "112", "2025-02-01", true)
        def offeringModel3 = createOfferingModel(13L, "123", "2025-05-01", true)

        offeringModelProvider.getBy(offerId) >> [offeringModel1, offeringModel2, offeringModel3]
        def expectedResponse = new ClassPathResource("products/response/get_all_offerings.json").getFile().getText()

        expect:
        mockMvc.perform(get("/offers/1/offerings"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse))
    }

    def "should return offering by id"() {
        given:
        def offerId = new OfferId("1")
        def offeringId = 3L

        def offeringModel = createOfferingModel(offeringId, "101", "2025-01-01", false)

        offeringModelProvider.getBy(offerId, offeringId) >> offeringModel
        def expectedResponse = new ClassPathResource("products/response/get_offering_by_id.json").getFile().getText()

        expect:
        mockMvc.perform(get("/offers/1/offerings/3"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse))
    }

    def "should return available offerings"() {
        given:
        def offerId = new OfferId("1")
        def request = new ClassPathResource("products/request/get_available_offerings.json").getFile().getText()
        def expectedResponse = new ClassPathResource("products/response/available_offerings.json").getFile().getText()

        offeringServiceAdapter.getAvailableOfferings(offerId, _ as InsuredEligibilityRequest) >>
                new AvailableOfferingModel([
                        createAvailableOffering("101"),
                        createAvailableOffering("202")
                ])

        mockMvc.perform(post("/offers/1/offerings/available")
                .contentType("application/json")
                .content(request))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse))
    }

    private static OfferingModel createOfferingModel(Long id, String productId, String startDate, boolean promotion) {
        return OfferingModel.builder()
                .id(id)
                .productId(new ProductId(productId))
                .startDate(LocalDate.parse(startDate))
                .promotion(promotion)
                .build()
    }

    private static AvailableOfferingModel createAvailableOffering(String productId) {
        AvailableOfferingModel.builder()
                .productId(new ProductId(productId))
                .startDateOptions([LocalDate.parse("2025-01-01"), LocalDate.parse("2025-02-01")])
                .promotionOptions([true, false])
                .build()
    }

}
