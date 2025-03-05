package pl.mpietrewicz.insurance.product.webapi.controller

import org.springframework.core.io.ClassPathResource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import pl.mpietrewicz.insurance.ddd.canonicalmodel.publishedlanguage.ProductId
import pl.mpietrewicz.insurance.ddd.sharedkernel.valueobject.Premium
import pl.mpietrewicz.insurance.product.webapi.dto.response.ProductModel
import pl.mpietrewicz.insurance.product.webapi.service.model.ProductModelService
import pl.mpietrewicz.insurance.product.webapi.dto.request.CreateProductRequest
import pl.mpietrewicz.insurance.product.webapi.service.adapter.ProductServiceAdapter
import spock.lang.Specification
import spock.lang.Subject

import static org.hamcrest.Matchers.endsWith
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class ProductControllerTest extends Specification {

    def productModelProvider = Mock(ProductModelService)

    def productCreatorService = Mock(ProductServiceAdapter)

    @Subject
    MockMvc mockMvc

    def setup() {
        def productController = new ProductController(productModelProvider, productCreatorService)
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build()
    }

    def "should return all products"() {
        given:
        def product = ProductModel.builder()
                .id(new ProductId("1"))
                .name("Product P")
                .premium(Premium.valueOf(new BigDecimal("10.50")))
//                .maxEntryAge(75)
                .build()

        productModelProvider.getAll() >> [product]
        def expectedResponse = new ClassPathResource("products/response/get_products.json").getFile().getText()

        expect:
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse))
    }

    def "should return product by id"() {
        given:
        def product = ProductModel.builder()
                .id(new ProductId("1"))
                .name("Product P")
                .premium(new Premium(new BigDecimal("22.55")))
//                .maxEntryAge(99)
                .build()

        productModelProvider.getBy(new ProductId("1")) >> product
        def expectedResponse = new ClassPathResource("products/response/get_product_by_id.json").getFile().getText()

        expect:
        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse))
    }

    def "should create product and return HTTP 201"() {
        given:
        def request = new ClassPathResource("products/request/create_product.json").getFile().getText()

        when:
        def response = mockMvc.perform(post("/products")
                .contentType("application/json")
                .content(request))

        then:
        response.andExpect(status().isCreated())
                .andExpect(header().string("Location", endsWith("/products/1")))

        and:
        1 * productCreatorService.createProduct(_ as CreateProductRequest) >> new ProductId("1")
    }

}
