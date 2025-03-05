package pl.mpietrewicz.insurance.product.webapi.controller

import org.springframework.core.io.ClassPathResource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.InsuredId
import pl.mpietrewicz.insurance.product.webapi.dto.request.InsuredEligibilityRequest
import pl.mpietrewicz.insurance.product.webapi.service.adapter.OfferServiceAdapter
import spock.lang.Specification
import spock.lang.Subject

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class ApplicantActionControllerTest extends Specification {

    def offerAvailabilityService = Mock(OfferServiceAdapter)

    @Subject
    MockMvc mockMvc

    def setup() {
        def productController = new ApplicantActionController(offerAvailabilityService)
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build()
    }

    def "should return insured links to contracts and offers"() {
        given:
        def insuredId = new InsuredId("1")
        offerAvailabilityService.canCreateOffer(insuredId, _ as InsuredEligibilityRequest) >> false

        def request = new ClassPathResource("products/request/get_insured_links.json").getFile().getText()
        def expectedResponse = new ClassPathResource("products/response/get_insured_contracts_and_offers_links.json").getFile().getText()

        expect:
        mockMvc.perform(post("/insured/1")
                .contentType("application/json")
                .content(request))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse))
    }

    def "should return only link to create-offer"() {
        given:
        def insuredId = new InsuredId("1")
        offerAvailabilityService.canCreateOffer(insuredId, _ as InsuredEligibilityRequest) >> true

        def request = new ClassPathResource("products/request/get_insured_links.json").getFile().getText()
        def expectedResponse = new ClassPathResource("products/response/get_insured_with_create_offer.json").getFile().getText()

        expect:
        mockMvc.perform(post("/insured/1")
                .contentType("application/json")
                .content(request))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse))
    }

}
