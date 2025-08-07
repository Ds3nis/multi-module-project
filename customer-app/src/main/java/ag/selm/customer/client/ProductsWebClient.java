package ag.selm.customer.client;

import ag.selm.customer.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ProductsWebClient implements ProductsClient{

    private final WebClient webClient;

    @Override
    public Mono<Product> getProduct(Integer productId) {
        return webClient.get()
                .uri("/catalog-api/products/{productId:\\d+}", productId)
                .retrieve()
                .bodyToMono(Product.class)
                .onErrorComplete(WebClientResponseException.NotFound.class);
    }

    @Override
    public Flux<Product> getProducts() {
        return webClient.get()
                .uri("/catalog-api/products/get")
                .retrieve()
                .bodyToFlux(Product.class);
    }


}
