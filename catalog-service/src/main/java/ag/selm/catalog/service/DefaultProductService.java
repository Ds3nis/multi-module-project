package ag.selm.catalog.service;

import ag.selm.catalog.dto.NewProductDto;
import ag.selm.catalog.dto.UpdateProductDto;
import ag.selm.catalog.entity.Product;

import ag.selm.catalog.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class DefaultProductService implements ProductService{

    private final ProductRepository productRepository;

    @Override
    public Iterable<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product findProduct(int id) {
        return productRepository.findById(id).orElseThrow(() -> new NoSuchElementException("catalog.errors.product.not_found"));
    }

    @Override
    @Transactional
    public Product updateProduct(Integer id, UpdateProductDto updateProductDto) {
         Product product = productRepository.findById(id).orElseThrow(() -> new NoSuchElementException("catalog.errors.product.not_found"));
         product.setTitle(updateProductDto.title());
         product.setDetails(updateProductDto.details());
         this.productRepository.save(product);
         return product;
    }

    @Override
    @Transactional
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Product createProduct(NewProductDto newProductDto) {
        Product product = new Product(null, newProductDto.title(), newProductDto.details());
        productRepository.save(product);
        return product;
    }
}
