package dompoo.study.decoratorPattern.berverage;

public class Americano extends Beverage {

    public Americano() {
        description = "아메리카노";
    }

    @Override
    public int cost() {
        return 3000;
    }
}
