package ag.selm.feedback.repository;

import ag.selm.feedback.entity.FavoriteProduct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface FavoriteProductsRepository {


    Mono<Void> deleteByProductId(int productId);

    Mono<FavoriteProduct> save(FavoriteProduct favoriteProduct);

    boolean isFavoriteProductByProductId(int productId);

    Mono<FavoriteProduct> findByProductId(int productId);

    Flux<FavoriteProduct> findAll();
}
