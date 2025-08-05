package ag.selm.manager.controller;

import ag.selm.manager.client.BadRequestException;
import ag.selm.manager.client.ProductsRestClient;
import ag.selm.manager.client.RestClientProductsRestClient;
import ag.selm.manager.dto.NewProductDto;
import ag.selm.manager.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ConcurrentModel;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Modulární testy ProductsController")
class ProductsControllerTest {

    @Mock
    ProductsRestClient productsRestClient;

    ProductsController productsController = new ProductsController(productsRestClient);


    @BeforeEach
    void setUp() {
        productsController = new ProductsController(productsRestClient);
    }

    @Test
    void getNewProductPage() {

    }

    @Test
    @DisplayName("vytvoří nový produkt a přesměruje na stránku produktu")
    void createProduct_RequestIsValid_ReturnsRedirectToProductPage() {
        //given
        var newProductDto = new NewProductDto("title", "details");
        var model = new ConcurrentModel();

        when(productsRestClient.createProduct(newProductDto))
                .thenReturn(new Product(1, "title", "details"));
        //when
        var res = this.productsController.createProduct(newProductDto, model);

        //then
        assertEquals("redirect:/catalog/products/get", res);

        verify(this.productsRestClient).createProduct(newProductDto);
        verifyNoMoreInteractions(this.productsRestClient);
    }

    @Test
    @DisplayName("přesměruje na stránku vytvoření nového produktu s chybami")
    void createProduct_RequestIsNotValid_ReturnsNewProductPageWithErrors() {
        //given
        var newProductDto = new NewProductDto("ff", null);
        var model = new ConcurrentModel();

        when(this.productsRestClient.createProduct(newProductDto)).thenThrow(new BadRequestException(List.of("error1", "error2")));
        //when
        var res = this.productsController.createProduct(newProductDto, model);
        //then
        assertEquals("catalog/products/create", res);
        assertEquals(newProductDto, model.getAttribute("newProductDto"));
        assertEquals(List.of("error1", "error2"), model.getAttribute("errors"));

        verify(this.productsRestClient).createProduct(newProductDto);
        verifyNoMoreInteractions(this.productsRestClient);
    }
}