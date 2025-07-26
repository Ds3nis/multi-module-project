package ag.selm.catalog.repository;

import ag.selm.catalog.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Optional<List<Product>> findAll();

    void save(Product product);

    Optional<Product> findById(int id);

    void deleteById(int id);
}
