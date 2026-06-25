package ra.edu.orderservice.model;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class OrderDetail {
    private Long id;
    private Long productId;
    private Integer quantity;
    private Double totalAmount;
    private LocalDateTime orderDate;
    private String productName;
    private Double productUnitPrice;
    private Integer productStock;
    private String productDescription;
}
