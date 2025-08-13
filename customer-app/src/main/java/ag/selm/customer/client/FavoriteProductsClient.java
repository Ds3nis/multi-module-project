package ag.selm.customer.client;

import ag.selm.customer.dto.NewFavoriteProductDto;
import ag.selm.customer.entity.FavoriteProduct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FavoriteProductsClient {

    Flux<FavoriteProduct> getFavoriteProducts();

    Mono<FavoriteProduct> findFavoriteProductByProductId(int productId);

    Mono<FavoriteProduct> addToFavorites(NewFavoriteProductDto newFavoriteProductDto);

    Mono<Void> removeFromFavorites(int productId);
}
