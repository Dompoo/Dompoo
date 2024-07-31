package dompoo.cafekiosk.spring.api.controller.product;

import dompoo.cafekiosk.spring.api.ApiResponse;
import dompoo.cafekiosk.spring.api.controller.product.request.ProductCreateRequest;
import dompoo.cafekiosk.spring.api.service.product.ProductService;
import dompoo.cafekiosk.spring.api.service.product.response.ProductResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {
    
    private final ProductService productService;
    
    @PostMapping("/api/v1/products/new")
    public ApiResponse<ProductResponse> createProduct(@RequestBody @Valid ProductCreateRequest request) {
        return ApiResponse.ok(productService.createProduct(request));
    }
    
    @GetMapping("/api/v1/products/selling")
    public ApiResponse<List<ProductResponse>> getSellingProducts() {
        return ApiResponse.ok(productService.getSellingProducts());
    }
}
