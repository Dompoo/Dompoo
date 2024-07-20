package dompoo.study.decoratorPattern.decorator;

import dompoo.study.decoratorPattern.berverage.Beverage;

public class Whip extends BeverageDecorator {

    public Whip(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", 휘핑";
    }

    @Override
    public int cost() {
        return beverage.cost() + 300;
    }
}
