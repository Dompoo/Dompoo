package dompoo.cafekiosk.unit.order;

import dompoo.cafekiosk.unit.beverages.Beverage;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Order {
    
    private final LocalDateTime orderDateTime;
    private final List<Beverage> beverages;

    
}
