package ag.selm.feedback.service;

import ag.selm.feedback.entity.FavoriteProduct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FavoriteProductsService {

    Mono<?> removeFromFavorites(int productId);

    Mono<FavoriteProduct> addToFavorites(FavoriteProduct favoriteProduct);

//    boolean isFavoriteProduct(int productId);

    Mono<FavoriteProduct> findFavoriteProductByProductId(int productId);

    Flux<FavoriteProduct> findAllFavoriteProducts();
}
