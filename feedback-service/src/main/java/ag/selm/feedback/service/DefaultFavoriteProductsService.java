package ag.selm.feedback.service;

import ag.selm.feedback.entity.FavoriteProduct;
import ag.selm.feedback.repository.FavoriteProductsRepository;
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
    public Mono<FavoriteProduct> findFavoriteProductByProductId(int productId) {
        return this.favoriteProductsRepository.findByProductId(productId);
    }

    @Override
    public Mono<?> removeFromFavorites(int productId) {
        favoriteProductsRepository.deleteByProductId(productId);
        return Mono.empty();
    }

    @Override
    public Mono<FavoriteProduct> addToFavorites(FavoriteProduct favoriteProduct) {
        return favoriteProductsRepository.save(favoriteProduct);
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
