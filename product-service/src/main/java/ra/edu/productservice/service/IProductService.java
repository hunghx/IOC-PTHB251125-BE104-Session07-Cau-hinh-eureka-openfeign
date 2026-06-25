package ra.edu.productservice.service;

import ra.edu.productservice.model.Product;
import java.util.List;

public interface IProductService {
    List<Product> findAll();
    Product findById(Long id);
    Product save(Product product);
    void deleteById(Long id);
}
