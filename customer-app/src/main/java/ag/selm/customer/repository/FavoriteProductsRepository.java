package ag.selm.customer.repository;

import ag.selm.customer.entity.FavoriteProduct;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface FavoriteProductsRepository {


    Mono<Void> deleteByProductId(int productId);

    Mono<FavoriteProduct> save(FavoriteProduct favoriteProduct);

    boolean isFavoriteProductByProductId(int productId);

    Flux<FavoriteProduct> findAll();
}
