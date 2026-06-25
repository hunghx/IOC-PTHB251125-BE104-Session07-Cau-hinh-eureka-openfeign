package ra.edu.orderservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import ra.edu.orderservice.model.Order;
import ra.edu.orderservice.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(OrderRepository orderRepository) {
        return args -> {
            if (orderRepository.count() == 0) {
                // Giả định Product ID 1 là iPhone 15 Pro ($1200)
                Order o1 = Order.builder()
                        .productId(1L)
                        .quantity(2)
                        .totalAmount(2400.0)
                        .orderDate(LocalDateTime.now())
                        .build();
                // Giả định Product ID 2 là Samsung Galaxy S24 ($1100)
                Order o2 = Order.builder()
                        .productId(2L)
                        .quantity(1)
                        .totalAmount(1100.0)
                        .orderDate(LocalDateTime.now())
                        .build();
                orderRepository.saveAll(List.of(o1, o2));
                System.out.println("Sample orders initialized");
            }
        };
    }

}
