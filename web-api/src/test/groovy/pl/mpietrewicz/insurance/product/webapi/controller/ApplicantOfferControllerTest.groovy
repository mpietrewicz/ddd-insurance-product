package pl.mpietrewicz.insurance.product.webapi.controller

import org.springframework.core.io.ClassPathResource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.InsuredId
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.OfferId
import pl.mpietrewicz.insurance.product.domainapi.offer.OfferApplicationService
import pl.mpietrewicz.insurance.product.webapi.dto.response.OfferModel
import pl.mpietrewicz.insurance.product.webapi.service.model.OfferModelService
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDate

import static org.hamcrest.Matchers.endsWith
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class ApplicantOfferControllerTest extends Specification {

    def offerApiService = Mock(OfferApplicationService)

    def offerModelProvider = Mock(OfferModelService)

    @Subject
    MockMvc mockMvc

    def setup() {
        def insuredOfferController = new ApplicantOfferController(offerApiService, offerModelProvider)
        mockMvc = MockMvcBuilders.standaloneSetup(insuredOfferController).build()
    }

    def "should return insured offers"() {
        given:
        def insuredId = new InsuredId("1");
        def offerModel1 = OfferModel.builder()
                .id(new OfferId("21"))
                .insuredId(insuredId)
                .startDate(LocalDate.parse("2025-01-01"))
                .offerings([])
                .build()
        def offerModel2 = OfferModel.builder()
                .id(new OfferId("22"))
                .insuredId(insuredId)
                .startDate(LocalDate.parse("2025-04-01"))
                .offerings([])
                .build()

        offerModelProvider.getByApplicantId(insuredId) >> [offerModel1, offerModel2]

        def expectedResponse = new ClassPathResource("products/response/get_insured_offers.json").getFile().getText()

        expect:
        mockMvc.perform(get("/insured/1/offers"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse))
    }

    def "should create offer for insured and return HTTP 201"() {
        given:
        def request = new ClassPathResource("products/request/create_offer.json").getFile().getText()

        when:
        def response = mockMvc.perform(post("/insured/1/offers")
                .contentType("application/json")
                .content(request))

        then:
        response.andExpect(status().isCreated())
                .andExpect(header().string("Location", endsWith("/offers/101")))

        and:
        1 * offerApiService.createOffer(new InsuredId("1"), LocalDate.parse("2023-03-01")) >>
                new OfferId("101")
    }


}
