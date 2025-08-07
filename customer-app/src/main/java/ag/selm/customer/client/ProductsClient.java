package ag.selm.customer.client;

import ag.selm.customer.entity.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductsClient {

    Flux<Product> getProducts();

    Mono<Product> getProduct(Integer productId);
}
