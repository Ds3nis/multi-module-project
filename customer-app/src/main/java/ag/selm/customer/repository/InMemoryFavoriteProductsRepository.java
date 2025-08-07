package ag.selm.customer.repository;

import ag.selm.customer.entity.FavoriteProduct;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

@Repository
public class InMemoryFavoriteProductsRepository implements FavoriteProductsRepository{

    private List<FavoriteProduct> favoriteProducts = Collections.synchronizedList(new LinkedList<>());

    @Override
    public boolean isFavoriteProductByProductId(int productId) {
        return favoriteProducts.stream().anyMatch(favoriteProduct -> favoriteProduct.getProductId() == productId);
    }

    @Override
    public Flux<FavoriteProduct> findAll() {
        return Flux.fromIterable(favoriteProducts);
    }

    @Override
    public Mono<FavoriteProduct> save(FavoriteProduct favoriteProduct) {
        this.favoriteProducts.add(favoriteProduct);
        return Mono.just(favoriteProduct);
    }

    @Override
    public Mono<Void> deleteByProductId(int productId) {
        favoriteProducts.removeIf(favoriteProduct -> favoriteProduct.getProductId() == productId);
        return Mono.empty();
    }
}
