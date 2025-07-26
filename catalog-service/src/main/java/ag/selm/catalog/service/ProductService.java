package ag.selm.catalog.service;

import ag.selm.catalog.dto.NewProductDto;
import ag.selm.catalog.dto.UpdateProductDto;
import ag.selm.catalog.entity.Product;
import ag.selm.catalog.repository.InMemoryProductRepository;

import java.util.List;

public interface ProductService {

    List<Product> findAllProducts();

    Product createProduct(NewProductDto newProductDto);

    Product findProduct(int id);

    Product updateProduct(Integer id, UpdateProductDto updateProductDto);
    void deleteProduct(int id);
}
