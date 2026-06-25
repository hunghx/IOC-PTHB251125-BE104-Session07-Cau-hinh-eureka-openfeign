package ra.edu.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.edu.orderservice.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
