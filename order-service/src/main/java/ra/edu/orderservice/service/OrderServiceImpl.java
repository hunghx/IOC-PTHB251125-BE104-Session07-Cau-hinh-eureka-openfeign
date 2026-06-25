package ra.edu.orderservice.service;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ra.edu.orderservice.client.ProductClient;
import ra.edu.orderservice.model.Order;
import ra.edu.orderservice.model.OrderDetail;
import ra.edu.orderservice.model.Product;
import ra.edu.orderservice.repository.OrderRepository;

import java.io.IOException;
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
    @CircuitBreaker(name = "serviceA", fallbackMethod = "fallbackFindById")
    public OrderDetail findById(boolean isError,Long id) {
        if (isError) {
            throw new RuntimeException("Simulated error");
        }
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
            System.out.println("Load product detail for order: " + o.getId() + "successfully");
            return od;
        }
        return null;
    }
    public OrderDetail fallbackFindById(Long id, Throwable throwable){
        throwable.printStackTrace();
        System.out.println("Fallback method called for findById with id: " + id);
        return new OrderDetail();
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
    @Override
    public void test(boolean isError) throws IOException {
//        retry(isError);
        System.out.println("Test method called");
    }
    // ================================
    //               RETRY
    // ================================
    @Retry(name= "retryService", fallbackMethod = "retryFallback")
    public void retry(boolean isError) throws IOException {
        if (isError) {
            throw new IOException("Simulated error");
        }
        System.out.println("Retry logic executed successfully");
    }

    public void retryFallback(boolean isError, Throwable ex) {
        ex.printStackTrace();
        System.out.println("Retry fallback logic executed");
    }
    // ================================
    //               RATELIMITER
    // ================================
    @RateLimiter(name= "rateLimitService", fallbackMethod = "rateLimitFallback")
    public void rateLimit(boolean isError) throws IOException {
        if (isError) {
            throw new IOException("Simulated error");
        }
        System.out.println("Rate Limit logic executed successfully");
    }

    public void rateLimitFallback(boolean isError, Throwable ex) {
        ex.printStackTrace();
        System.out.println("Rate limit fallback logic executed");
    }

    // ================================
    //               BULKHEAD : chỉ cho phép bao nhiều luồng đc mở khi thực hiện chức năng
    // ================================
    @Bulkhead(name= "timeOutService", fallbackMethod = "timeOutFallback") //
    public void timeOut(boolean isError) throws IOException {
        if (isError) {
            throw new IOException("Simulated error");
        }
        System.out.println("Timeout logic executed successfully");
    }

    public void timeOutFallback(boolean isError, Throwable ex) {
        ex.printStackTrace();
        System.out.println("Timeout fallback logic executed");
    }
}

