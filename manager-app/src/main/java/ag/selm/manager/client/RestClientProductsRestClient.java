package ag.selm.manager.client;

import ag.selm.manager.dto.NewProductDto;
import ag.selm.manager.dto.UpdateProductDto;
import ag.selm.manager.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class RestClientProductsRestClient implements ProductsRestClient {

    private final RestClient restClient;

    private static final ParameterizedTypeReference<List<Product>> PRODUCT_LIST_TYPE =
            new ParameterizedTypeReference<>() {};

    @Override
    public List<Product> findAllProducts() {
        return this.restClient.get().
                uri("catalog-api/products/get").
                retrieve().
                body(PRODUCT_LIST_TYPE);
    }

    @Override
    public Product createProduct(NewProductDto newProductDto) {
        try{
            return this.restClient.post().uri("catalog-api/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(newProductDto)
                    .retrieve()
                    .body(Product.class);
        }catch (HttpClientErrorException.BadRequest ex){
            ProblemDetail responseBodyAs = ex.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) responseBodyAs.getProperties().get("errors"));
        }
    }

    @Override
    public Product findProduct(int id) {
        try {
            return this.restClient.get().uri("catalog-api/products/{productId:\\d+}", id).
                    retrieve().
                    body(Product.class);
        }catch (HttpClientErrorException.NotFound ex){
            throw ex;
        }
    }

    @Override
    public Product updateProduct(Integer id, UpdateProductDto updateProductDto) {
        try {
            return this.restClient.put().uri("catalog-api/products/{productId:\\d+}/update", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(updateProductDto)
                    .retrieve().
                    body(Product.class);
        }catch (HttpClientErrorException.BadRequest ex){
            ProblemDetail responseBodyAs = ex.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) responseBodyAs.getProperties().get("errors"));
        }
    }

    @Override
    public void deleteProduct(int id) {
        try{
            this.restClient.delete().uri("catalog-api/products/{productId:\\d+}/delete", id)
                    .retrieve()
                    .toBodilessEntity();

        }catch (HttpClientErrorException.NotFound ex){
            throw new NoSuchElementException(ex.getMessage());
        }
    }
}
