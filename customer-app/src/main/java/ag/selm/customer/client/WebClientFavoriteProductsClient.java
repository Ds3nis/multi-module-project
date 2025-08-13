package ag.selm.customer.client;


import ag.selm.customer.dto.NewFavoriteProductDto;
import ag.selm.customer.entity.FavoriteProduct;
import ag.selm.customer.exception.ClientBadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class WebClientFavoriteProductsClient implements FavoriteProductsClient{

    private final WebClient webClient;

    @Override
    public Flux<FavoriteProduct> getFavoriteProducts() {
        return webClient
                .get()
                .uri("feedback-api/favorite-products")
                .retrieve()
                .bodyToFlux(FavoriteProduct.class);
    }

    @Override
    public Mono<FavoriteProduct> findFavoriteProductByProductId(int productId) {
        return webClient
                .get()
                .uri("feedback-api/favorite-products/by-productId/{productId}", productId)
                .retrieve()
                .bodyToMono(FavoriteProduct.class);
    }

    @Override
    public Mono<FavoriteProduct> addToFavorites(NewFavoriteProductDto newFavoriteProductDto) {
        return webClient
                .post()
                .uri("feedback-api/favorite-products")
                .bodyValue(newFavoriteProductDto)
                .retrieve()
                .bodyToMono(FavoriteProduct.class)
                .onErrorMap(WebClientResponseException.BadRequest.class,
                        exception -> new ClientBadRequestException(
                                exception,
                                (List<String>) exception.getResponseBodyAs(ProblemDetail.class).getProperties().get("errors")
                        )
                );
    }

    @Override
    public Mono<Void> removeFromFavorites(int productId) {
        return webClient
                .delete()
                .uri("feedback-api/favorite-products/by-productId/{productId}", productId)
                .retrieve()
                .toBodilessEntity()
                .then();
    }
}
