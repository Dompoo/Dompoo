package dompoo.study.decoratorPattern.decorator;

import dompoo.study.decoratorPattern.berverage.Beverage;

public abstract class BeverageDecorator extends Beverage {
    Beverage beverage;

    public abstract String getDescription();
}
