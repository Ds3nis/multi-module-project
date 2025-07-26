package ag.selm.manager.client;

import ag.selm.manager.dto.NewProductDto;
import ag.selm.manager.dto.UpdateProductDto;
import ag.selm.manager.entity.Product;

import java.util.List;

public interface ProductsRestClient {

    List<Product> findAllProducts();

    Product createProduct(NewProductDto newProductDto);

    Product findProduct(int id);

    Product updateProduct(Integer id, UpdateProductDto updateProductDto);

    void deleteProduct(int id);
}
