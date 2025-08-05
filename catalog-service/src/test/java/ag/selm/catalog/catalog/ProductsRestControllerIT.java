package ag.selm.catalog.catalog;


import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Locale;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductsRestControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    @Sql("/sql/products.sql")
    public void getProducts_ReturnsListOfProducts() throws Exception{
        //given
        var request = MockMvcRequestBuilders.get("/catalog-api/products/get")
                .with(jwt().jwt(jwt -> jwt.claim("scope", "view-catalog")));
        //when
        mockMvc.perform(request)
        //then
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
[
                                {"id": 1, "title": "title1", "details": "details1"},
                                {"id": 2, "title": "title2", "details": "details2"}
                              ]""")
                );
    }

    @Test
    public void createProduct_RequestIsValid_ReturnsNewProduct() throws Exception{
        //given
        var request = MockMvcRequestBuilders.post("/catalog-api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {"title":  "NewProduct", "details":  "NewProductDetails"}""")
                .with(jwt().jwt(jwt -> jwt.claim("scope", "edit-catalog")));
        //when
        mockMvc.perform(request)
        //then
                .andDo(print())
                .andExpectAll(
                        status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        header().string(HttpHeaders.LOCATION, "http://localhost/catalog-api/products/1"),
                        content().json("""             
                          {"id": 1, "title": "NewProduct", "details": "NewProductDetails"}
                        """)
                );
    }

    @Test
    public void createProduct_RequestIsInvalid_ReturnsException() throws Exception{
        //given
        var request = MockMvcRequestBuilders.post("/catalog-api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {"title":  "   ", "details":  "ffds"}
                """)
                .with(jwt().jwt(jwt -> jwt.claim("scope", "edit-catalog")));

        //when
        mockMvc.perform(request)
        //then
                .andDo(print())
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentType(MediaType.APPLICATION_PROBLEM_JSON),
                        content().json("""             
                          {"errors":  ["Název produktu musí byt od 3 po 50 symbolu"]}
                        """, false)
                );
    }

    @Test
    public void createProduct_UserIsNotAuthorized_ReturnsForbidden() throws Exception{
        //given
        var request = MockMvcRequestBuilders.post("/catalog-api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {"title":  "fdsfs", "details":  "ffds"}
                """)
                .with(jwt().jwt(jwt -> jwt.claim("scope", "view-catalog")));
        //when
        mockMvc.perform(request)
        //then
                .andDo(print())
                .andExpectAll(
                        status().isForbidden()
                );
    }
}
