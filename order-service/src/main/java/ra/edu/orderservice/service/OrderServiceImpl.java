package ra.edu.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ra.edu.orderservice.client.ProductClient;
import ra.edu.orderservice.model.Order;
import ra.edu.orderservice.model.OrderDetail;
import ra.edu.orderservice.model.Product;
import ra.edu.orderservice.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor

public class OrderServiceImpl implements IOrderService {
    private final ProductClient productClient;

    private final OrderRepository orderRepository;

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public OrderDetail findById(Long id) {
        Order o = orderRepository.findById(id).orElse(null);
        // lấy thông tin chi tiết thì phải gọi APi sang product service
        if (o!=null){
            Product p = productClient.getProductById(o.getProductId());
            OrderDetail od = new OrderDetail();
            od.setId(o.getId());
            od.setProductId(o.getProductId());
            od.setOrderDate(o.getOrderDate());
            od.setProductName(p.getName());
            od.setProductStock(p.getStock());
            od.setTotalAmount(o.getTotalAmount());
            od.setProductUnitPrice(p.getPrice());
            od.setQuantity(o.getQuantity());
            od.setProductDescription(p.getDescription());
            return od;
        }
        return null;
    }

    @Override
    public Order save(Order order) {
        if (order.getOrderDate() == null) {
            order.setOrderDate(LocalDateTime.now());
        }
        return orderRepository.save(order);
    }

    @Override
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }
}
