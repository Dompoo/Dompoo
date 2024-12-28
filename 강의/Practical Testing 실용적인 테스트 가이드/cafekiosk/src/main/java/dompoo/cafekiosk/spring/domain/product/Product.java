package dompoo.cafekiosk.spring.domain.product;

import dompoo.cafekiosk.spring.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String productNumber;
    
    private String name;
    
    private int price;
    
    @Enumerated(EnumType.STRING)
    private ProductType type;
    
    @Enumerated(EnumType.STRING)
    private ProductSellingStatus sellingStatus;
    
    @Builder
    private Product(String productNumber, String name, int price, ProductType type,
        ProductSellingStatus sellingStatus) {
        this.productNumber = productNumber;
        this.name = name;
        this.price = price;
        this.type = type;
        this.sellingStatus = sellingStatus;
    }
}
