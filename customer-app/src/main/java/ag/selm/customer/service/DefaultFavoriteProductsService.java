package ag.selm.customer.service;

import ag.selm.customer.entity.FavoriteProduct;
import ag.selm.customer.repository.FavoriteProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultFavoriteProductsService implements FavoriteProductsService{

    private final FavoriteProductsRepository favoriteProductsRepository;

    @Override
    public Mono<?> removeFromFavorites(int productId) {
        favoriteProductsRepository.deleteByProductId(productId);
        return Mono.empty();
    }

    @Override
    public Mono<FavoriteProduct> addToFavorites(int productId) {
        return favoriteProductsRepository.save(new FavoriteProduct(UUID.randomUUID(), productId));
    }

    @Override
    public boolean isFavoriteProduct(int productId) {
        return favoriteProductsRepository.isFavoriteProductByProductId(productId);
    }

    @Override
    public Flux<FavoriteProduct> findAllFavoriteProducts() {
        return favoriteProductsRepository.findAll();
    }
}
