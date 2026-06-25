package ra.edu.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.edu.productservice.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
