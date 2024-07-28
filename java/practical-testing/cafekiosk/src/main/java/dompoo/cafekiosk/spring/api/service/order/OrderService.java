package dompoo.cafekiosk.spring.api.service.order;

import dompoo.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import dompoo.cafekiosk.spring.api.service.order.response.OrderResponse;
import dompoo.cafekiosk.spring.domain.order.Order;
import dompoo.cafekiosk.spring.domain.order.OrderRepository;
import dompoo.cafekiosk.spring.domain.product.Product;
import dompoo.cafekiosk.spring.domain.product.ProductRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    
    public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime now) {
        List<String> productNumbers = request.getProductNumbers();
        
        // Product
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);
        
        // Order
        Order order = Order.create(products, now);
        Order savedOrder = orderRepository.save(order);
        
        return OrderResponse.of(savedOrder);
    }
}
