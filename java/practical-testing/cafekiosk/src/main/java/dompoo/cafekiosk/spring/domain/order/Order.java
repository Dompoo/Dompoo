package dompoo.cafekiosk.spring.domain.order;

import dompoo.cafekiosk.spring.domain.BaseEntity;
import dompoo.cafekiosk.spring.domain.orderproduct.OrderProduct;
import dompoo.cafekiosk.spring.domain.product.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    
    private Order(List<Product> products, LocalDateTime now) {
        this.status = OrderStatus.INIT;
        this.totalPrice = calculateTotalPrice(products);
        this.registedDateTime = now;
        this.orderProducts = products.stream()
            .map(product -> new OrderProduct(this, product))
            .toList();
    }
    
    private static int calculateTotalPrice(List<Product> products) {
        return products.stream().mapToInt(Product::getPrice).sum();
    }
    
    public static Order create(List<Product> products, LocalDateTime now) {
        return new Order(products, now);
    }
}
