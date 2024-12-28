package dompoo.cafekiosk.spring.domain.order;

import dompoo.cafekiosk.spring.domain.BaseEntity;
import dompoo.cafekiosk.spring.domain.orderproduct.OrderProduct;
import dompoo.cafekiosk.spring.domain.product.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private OrderStatus status;
    
    private int totalPrice;
    
    private LocalDateTime registedDateTime;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts = new ArrayList<>();
    
    @Builder
    private Order(List<Product> products, OrderStatus status, LocalDateTime registeredDateTime) {
        this.status = status;
        this.totalPrice = calculateTotalPrice(products);
        this.registedDateTime = registeredDateTime;
        this.orderProducts = products.stream()
                .map(product -> new OrderProduct(this, product))
                .toList();
    }
    
    public static Order create(List<Product> products, LocalDateTime now) {
        return Order.builder()
                .products(products)
                .status(OrderStatus.INIT)
                .registeredDateTime(now)
                .build();
    }
    
    private static int calculateTotalPrice(List<Product> products) {
        return products.stream().mapToInt(Product::getPrice).sum();
    }
    
    public void setOrderStatus(OrderStatus orderStatus) {
        this.status = orderStatus;
    }
}
