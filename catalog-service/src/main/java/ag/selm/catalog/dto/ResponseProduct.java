package ag.selm.catalog.dto;

import ag.selm.catalog.entity.Product;
import org.springframework.lang.Contract;

public record ResponseProduct(

        int id,
        String title,
        String details
) {

    public ResponseProduct(Product product) {
        this(product.getId(), product.getTitle(), product.getDetails());
    }
}
