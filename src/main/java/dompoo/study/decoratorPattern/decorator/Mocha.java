package dompoo.study.decoratorPattern.decorator;

import dompoo.study.decoratorPattern.berverage.Beverage;

public class Mocha extends BeverageDecorator {

    public Mocha(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", 모카";
    }

    @Override
    public int cost() {
        return beverage.cost() + 500;
    }
}
