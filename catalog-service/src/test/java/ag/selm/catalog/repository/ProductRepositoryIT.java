package ag.selm.catalog.repository;

import ag.selm.catalog.entity.Product;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
@Sql("/sql/products.sql" )
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryIT {


    @Autowired
    ProductRepository productRepository;

    @Test
    public void findAllProducts_ReturnsListOfProducts() {
        //given

        //when
            var products = productRepository.findAll();
        //then
        assertEquals(List.of(
                new Product(1, "title1", "details1"),
                new Product(2, "title2", "details2")), products);
    }
}