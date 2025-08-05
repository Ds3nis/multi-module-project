package ag.selm.manager.controller;

import ag.selm.manager.entity.Product;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import wiremock.org.apache.hc.client5.http.impl.Wire;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WireMockTest(httpPort = 54321)
@ActiveProfiles("test")
public class ProductsControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getProducts_ReturnsListOfProducts() throws Exception{
        //given
        var request = MockMvcRequestBuilders.get("/catalog/products/get").
                with(user("vasy abeton").roles("MANAGER"));

        WireMock.stubFor(WireMock.get(WireMock.urlPathMatching("/catalog-api/products/get"))
                .willReturn(WireMock.ok("""
                        [{"id": 3, "title": "dsfsdfsfdsf242", "details": "dfsssfd"},
                        {"id": 4, "title": "fsdfsf", "details": "afssdfsfs"}
                        ]""").withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)));

        //when
        mockMvc.perform(request)
                //then
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        view().name("catalog/products/list"),
                        model().attribute("products", List.of(
                                new Product(3, "dsfsdfsfdsf242", "dfsssfd"),
                                new Product(4, "fsdfsf", "afssdfsfs")
                        ))
                );
        WireMock.verify(WireMock.getRequestedFor(WireMock.urlPathMatching("/catalog-api/products/get")));

    }

    @Test
    public void getNewProductPage_ReturnsNewProductPage() throws Exception{
        //given
        var request = MockMvcRequestBuilders.get("/catalog/products/create").with(user("vasyabeton").roles("MANAGER"));
        //when
        mockMvc.perform(request)
        //then
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        view().name("catalog/products/create")
                );
    }

}
