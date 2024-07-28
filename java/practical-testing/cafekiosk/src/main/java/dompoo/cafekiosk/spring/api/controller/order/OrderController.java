package dompoo.cafekiosk.spring.api.controller.order;

import dompoo.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import dompoo.cafekiosk.spring.api.service.order.OrderService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {
    
    private final OrderService orderService;
    
    @PostMapping("/api/v1/orders/new")
    public void createOrder(@RequestBody OrderCreateRequest request) {
        LocalDateTime registerdDateTime = LocalDateTime.now();
        orderService.createOrder(request, registerdDateTime);
    }
}
