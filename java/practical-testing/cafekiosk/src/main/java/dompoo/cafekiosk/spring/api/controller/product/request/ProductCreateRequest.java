package dompoo.cafekiosk.spring.api.controller.product.request;

import dompoo.cafekiosk.spring.domain.product.Product;
import dompoo.cafekiosk.spring.domain.product.ProductSellingStatus;
import dompoo.cafekiosk.spring.domain.product.ProductType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductCreateRequest {
    
    @NotBlank(message = "상품 이름은 필수입니다.")
    private String name;
    
    @NotNull(message = "상품 타입은 필수입니다.")
    private ProductType type;
    
    @NotNull(message = "상품 판매 상태는 필수입니다.")
    private ProductSellingStatus sellingStatus;
    
    @Positive(message = "상품 가격은 0 이상이어야 합니다.")
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
