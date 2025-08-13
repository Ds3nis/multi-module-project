package ag.selm.feedback.repository;

import ag.selm.feedback.entity.FavoriteProduct;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;


public interface FavoriteProductsRepository extends ReactiveCrudRepository<FavoriteProduct, UUID> {


    void deleteByProductId(int productId);


//    boolean isFavoriteProductByProductId(int productId);

    Mono<FavoriteProduct> findByProductId(int productId);

}
