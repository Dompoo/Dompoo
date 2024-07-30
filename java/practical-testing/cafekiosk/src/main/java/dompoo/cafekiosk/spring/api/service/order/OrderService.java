package dompoo.cafekiosk.spring.api.service.order;

import dompoo.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import dompoo.cafekiosk.spring.api.service.order.response.OrderResponse;
import dompoo.cafekiosk.spring.domain.order.Order;
import dompoo.cafekiosk.spring.domain.order.OrderRepository;
import dompoo.cafekiosk.spring.domain.product.Product;
import dompoo.cafekiosk.spring.domain.product.ProductRepository;
import dompoo.cafekiosk.spring.domain.product.ProductType;
import dompoo.cafekiosk.spring.domain.stock.Stock;
import dompoo.cafekiosk.spring.domain.stock.StockRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {
    
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final StockRepository stockRepository;
    
    @Transactional
    public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime now) {
        List<String> productNumbers = request.getProductNumbers();
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);
        
        deductStockQuantity(products, productNumbers);
        
        Map<String, Product> productMap = products.stream()
            .collect(Collectors.toMap(Product::getProductNumber, product -> product));
        
        List<Product> productsWithDuplicate = productNumbers.stream()
            .map(productMap::get)
            .toList();
        
        // Order
        Order order = Order.create(productsWithDuplicate, now);
        Order savedOrder = orderRepository.save(order);
        
        return OrderResponse.of(savedOrder);
    }
    
    //todo 동시성 문제 고려 필요, lock 관련 공부 필요
    private void deductStockQuantity(List<Product> products, List<String> productNumbers) {
        //재고 차감이 필요한 상품번호만 필터링
        List<String> stockProductNumbers = extractStockProductNumber(products);
        //주문 상품의 재고 entity 조회
        Map<String, Stock> productNumberToStock = createStockMapBy(stockProductNumbers);
        //주문 상품 종류별 개수 카운팅
        Map<String, Long> productNumberToCount = createCountingMapBy(productNumbers);
        //재고 차감 시도
        for (String productNumber : stockProductNumbers) {
            Stock stock = productNumberToStock.get(productNumber);
            Long requiedStock = productNumberToCount.get(productNumber);
            if (stock.isQuantityLessThan(requiedStock)) {
                throw new IllegalArgumentException("재고가 부족한 상품이 있습니다.");
            }
            stock.deductQuantity(requiedStock);
        }
    }
    
    private static List<String> extractStockProductNumber(List<Product> products) {
        return products.stream().filter(product -> ProductType.containsStockType(product.getType()))
            .map(Product::getProductNumber).toList();
    }
    
    private Map<String, Stock> createStockMapBy(List<String> productNumbers) {
        return stockRepository.findAllByProductNumberIn(productNumbers).stream()
            .collect(Collectors.toMap(Stock::getProductNumber, s -> s));
    }
    
    private static Map<String, Long> createCountingMapBy(List<String> productNumbers) {
        Map<String, Long> productNumberToCount = productNumbers.stream()
            .collect(Collectors.groupingBy(p -> p, Collectors.counting()));
        return productNumberToCount;
    }
}
