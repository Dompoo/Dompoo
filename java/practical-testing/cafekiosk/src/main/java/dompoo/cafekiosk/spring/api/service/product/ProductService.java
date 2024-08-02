package dompoo.cafekiosk.spring.api.service.product;

import dompoo.cafekiosk.spring.api.service.product.dto.request.ProductCreateServiceRequest;
import dompoo.cafekiosk.spring.api.service.product.dto.response.ProductResponse;
import dompoo.cafekiosk.spring.domain.product.Product;
import dompoo.cafekiosk.spring.domain.product.ProductRepository;
import dompoo.cafekiosk.spring.domain.product.ProductSellingStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    
    private final ProductRepository productRepository;
    private final ProductNumberFactory productNumberFactory;
    
    @Transactional
    public ProductResponse createProduct(ProductCreateServiceRequest request) {
        String nextProductNumber = productNumberFactory.createNextProductNumber();
        
        Product product = request.toProduct(nextProductNumber);
        Product savedProduct = productRepository.save(product);
        
        return ProductResponse.of(savedProduct);
    }
    
    
    
    /**
     * 읽기전용 트랜잭션
     * CRUD의 R만 작동하는 트랜잭션이다. (시도하면 예외발생)
     * -> 스냅샷 저장, 변경 감지 기능이 꺼지므로 성능 향상이 있다.
     * -> CQRS : Command와 Query를 분리하자. 애플리케이션에서도 서비스를 분리하거나, DB를 분리하는 등 분리해서 얻는 이점이 많다.
     */
    public List<ProductResponse> getSellingProducts() {
        List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());
        
        return products.stream()
            .map(ProductResponse::of)
            .toList();
    }
}
