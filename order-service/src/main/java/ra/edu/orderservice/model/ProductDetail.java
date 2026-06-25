package ra.edu.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDetail {
    private Long id;
    private String name;
    private Double price;
    private Integer stock;
    private String description;
}
