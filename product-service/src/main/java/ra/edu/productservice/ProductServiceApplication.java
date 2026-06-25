package ra.edu.productservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import ra.edu.productservice.model.Product;
import ra.edu.productservice.repository.ProductRepository;

import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(ProductRepository productRepository) {
        return args -> {
            if (productRepository.count() == 0) {
                Product p1 = Product.builder().name("iPhone 15 Pro").price(1200.0).stock(50).description("Apple Smartphone").build();
                Product p2 = Product.builder().name("Samsung Galaxy S24").price(1100.0).stock(40).description("Samsung Smartphone").build();
                Product p3 = Product.builder().name("MacBook Pro M3").price(2500.0).stock(20).description("Apple Laptop").build();
                productRepository.saveAll(List.of(p1, p2, p3));
                System.out.println("Sample products initialized");
            }
        };
    }

}
