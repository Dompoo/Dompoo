package dompoo.cafekiosk.spring.api.service.product.response;

import dompoo.cafekiosk.spring.domain.product.Product;
import dompoo.cafekiosk.spring.domain.product.ProductSellingStatus;
import dompoo.cafekiosk.spring.domain.product.ProductType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductResponse {
    
    private Long id;
    private String productNumber;
    private String name;
    private int price;
    private ProductType type;
    private ProductSellingStatus sellingType;
    
    @Builder
    private ProductResponse(Long id, String productNumber, String name, int price, ProductType type,
        ProductSellingStatus sellingType) {
        this.id = id;
        this.productNumber = productNumber;
        this.name = name;
        this.price = price;
        this.type = type;
        this.sellingType = sellingType;
    }
    
    public static ProductResponse of(Product product) {
        return ProductResponse.builder()
            .id(product.getId())
            .productNumber(product.getProductNumber())
            .name(product.getName())
            .price(product.getPrice())
            .type(product.getType())
            .sellingType(product.getSellingStatus())
            .build();
    }
}
