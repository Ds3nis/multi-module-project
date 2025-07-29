//package ag.selm.catalog.repository;
//
//import ag.selm.catalog.entity.Product;
//import org.springframework.stereotype.Repository;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.IntStream;
//
//@Repository
//public class InMemoryProductRepository implements ProductRepository{
//
//    private final List<Product> products = Collections.synchronizedList(new ArrayList<>());
//
//    public InMemoryProductRepository() {
//        IntStream.range(1, 4).forEach(i -> this.products.add(new Product(i, "Product %d".formatted(i),
//                "Product %d".formatted(i))));
//    }
//
//    @Override
//    public List<Product> findAll() {
//        return List.copyOf(products);
//    }
//
//    @Override
//    public void deleteById(int id) {
//        products.removeIf(product -> product.getId() == id);
//    }
//
//    @Override
//    public Optional<Product> findById(int id) {
//        return products.stream().filter(p -> p.getId() == id).findFirst();
//    }
//
//    @Override
//    public Product save(Product product) {
//        int id = products.stream().mapToInt(Product::getId).max().orElse(0) + 1;
//        product.setId(id);
//        products.add(product);
//        return product;
//    }
//}
