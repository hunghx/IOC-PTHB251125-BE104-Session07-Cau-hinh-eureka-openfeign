package ra.edu.orderservice.service;

import ra.edu.orderservice.model.Order;
import ra.edu.orderservice.model.OrderDetail;

import java.io.IOException;
import java.util.List;

public interface IOrderService {
    List<Order> findAll();
    OrderDetail findById(boolean isError,Long id);
    Order save(Order order);
    void deleteById(Long id);
    void test(boolean isError) throws IOException;
}
