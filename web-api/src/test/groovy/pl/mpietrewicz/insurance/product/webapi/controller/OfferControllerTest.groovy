package pl.mpietrewicz.insurance.product.webapi.controller

import org.springframework.core.io.ClassPathResource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.InsuredId
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.OfferId
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId
import pl.mpietrewicz.insurance.product.domainapi.offer.OfferApplicationService
import pl.mpietrewicz.insurance.product.webapi.dto.response.OfferModel
import pl.mpietrewicz.insurance.product.webapi.dto.response.OfferingModel
import pl.mpietrewicz.insurance.product.webapi.service.model.OfferModelService
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDate

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class OfferControllerTest extends Specification {

    def offerService = Mock(OfferApplicationService)

    def offerModelProvider = Mock(OfferModelService)

    @Subject
    MockMvc mockMvc

    def setup() {
        def offerController = new OfferController(offerService, offerModelProvider)
        mockMvc = MockMvcBuilders.standaloneSetup(offerController).build()
    }

    def "should return offer by offer id"() {
        given:
        def offerId = new OfferId("1")
        def offeredProducts = [
                prepareOffering("11", "101", "2025-01-01"),
                prepareOffering("12", "102", "2025-03-01")
        ]
        def offer = OfferModel.builder()
                .id(offerId)
                .insuredId(new InsuredId("11"))
                .startDate(LocalDate.parse("2024-01-01"))
                .offerings(offeredProducts)
                .build()

        offerModelProvider.getBy(offerId) >> offer
        def expectedResponse = new ClassPathResource("products/response/get_offer_by_id.json").getFile().getText()

        expect:
        mockMvc.perform(get("/offers/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse))
    }

    private static OfferingModel prepareOffering(String id, String productId, String startDate) {
        OfferingModel.builder()
                .id(Long.valueOf(id))
                .productId(new ProductId(productId))
                .startDate(LocalDate.parse(startDate))
                .promotion(true)
                .build()
    }

}
