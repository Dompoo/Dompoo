package dompoo.cafekiosk.spring.api.controller.product.dto.request;

import dompoo.cafekiosk.spring.domain.product.Product;
import dompoo.cafekiosk.spring.domain.product.ProductSellingStatus;
import dompoo.cafekiosk.spring.domain.product.ProductType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductCreateRequest {
    private String name;
    private ProductType type;
    private ProductSellingStatus sellingStatus;
    private int price;
    
    @Builder
    private ProductCreateRequest(String name, ProductType type, ProductSellingStatus sellingStatus, int price) {
        this.name = name;
        this.type = type;
        this.sellingStatus = sellingStatus;
        this.price = price;
    }
    
    public Product toProduct(String nextProductNumber) {
        return Product.builder()
                .productNumber(nextProductNumber)
                .name(name)
                .type(type)
                .sellingStatus(sellingStatus)
                .price(price)
                .build();
    }
}
