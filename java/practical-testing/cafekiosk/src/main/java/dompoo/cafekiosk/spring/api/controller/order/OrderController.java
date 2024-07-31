package dompoo.cafekiosk.spring.api.controller.order;

import dompoo.cafekiosk.spring.api.ApiResponse;
import dompoo.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import dompoo.cafekiosk.spring.api.service.order.OrderService;
import dompoo.cafekiosk.spring.api.service.order.response.OrderResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class OrderController {
    
    private final OrderService orderService;
    
    @PostMapping("/api/v1/orders/new")
    public ApiResponse<OrderResponse> createOrder(@RequestBody @Valid OrderCreateRequest request) {
        LocalDateTime registerdDateTime = LocalDateTime.now();
        return ApiResponse.ok(orderService.createOrder(request, registerdDateTime));
    }
}
