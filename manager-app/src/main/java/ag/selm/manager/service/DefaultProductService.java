package ag.selm.manager.service;

import ag.selm.manager.dto.NewProductDto;
import ag.selm.manager.dto.UpdateProductDto;
import ag.selm.manager.entity.Product;
import ag.selm.manager.repository.InMemoryProductRepository;
import ag.selm.manager.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class DefaultProductService implements ProductService{

    private final ProductRepository productRepository;

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll().orElseThrow(() -> new NoSuchElementException("catalog.errors.product.not_found"));
    }

    @Override
    public Product findProduct(int id) {
        return productRepository.findById(id).orElseThrow(() -> new NoSuchElementException("catalog.errors.product.not_found"));
    }

    @Override
    public Product updateProduct(Integer id, UpdateProductDto updateProductDto) {
         Product product = productRepository.findById(id).orElseThrow(() -> new NoSuchElementException("catalog.errors.product.not_found"));
         product.setTitle(updateProductDto.title());
         product.setDetails(updateProductDto.details());
         return product;
    }

    @Override
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product createProduct(NewProductDto newProductDto) {
        Product product = new Product(null, newProductDto.title(), newProductDto.details());
        productRepository.save(product);
        return product;
    }
}
