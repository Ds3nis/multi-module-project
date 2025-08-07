package ag.selm.customer.service;

import ag.selm.customer.entity.FavoriteProduct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FavoriteProductsService {

    Mono<?> removeFromFavorites(int productId);

    Mono<FavoriteProduct> addToFavorites(int productId);

    boolean isFavoriteProduct(int productId);

    Flux<FavoriteProduct> findAllFavoriteProducts();
}
