package dompoo.cafekiosk.unit;

import dompoo.cafekiosk.unit.beverages.Beverage;
import dompoo.cafekiosk.unit.order.Order;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class CafeKiosk {
    
    private final List<Beverage> beverages = new ArrayList<>();
    
    public void add(Beverage beverage) {
        beverages.add(beverage);
    }
    
    public void delete(Beverage beverage) {
        beverages.remove(beverage);
    }
    
    public void deleteAll() {
        beverages.clear();
    }
    
    public int calculateTotalPrice() {
        int totalPrice = 0;
        for (Beverage beverage : beverages) {
            totalPrice += beverage.getPrice();
        }
        return totalPrice;
    }
    
    public Order createOrder() {
        return new Order(LocalDateTime.now(), beverages);
    }
}
