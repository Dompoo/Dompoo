package dompoo.study.decoratorPattern.berverage;

public abstract class Beverage {
    public String description;

    public String getDescription() {
        return description;
    }

    public abstract int cost();
}
