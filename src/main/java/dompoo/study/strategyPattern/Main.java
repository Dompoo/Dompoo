package dompoo.study.strategyPattern;

import dompoo.study.strategyPattern.duck.Duck;
import dompoo.study.strategyPattern.duck.HealthyDuck;
import dompoo.study.strategyPattern.duck.OldDuck;
import dompoo.study.strategyPattern.duck.ToyDuck;
import dompoo.study.strategyPattern.fly.FlyNoWay;
import dompoo.study.strategyPattern.quack.Squeak;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

	public static void main(String[] args) {
		Duck healtyDuck = new HealthyDuck();
		Duck oldDuck = new OldDuck();
		Duck toyDuck = new ToyDuck();

		healtyDuck.introduce();
		healtyDuck.fly();
		healtyDuck.quack();

		oldDuck.introduce();
		oldDuck.fly();
		oldDuck.quack();

		toyDuck.introduce();
		toyDuck.fly();
		toyDuck.quack();

		log.info("건강한 오리가 총에 맞았습니다! 건강한 오리가 행동합니다.");
		healtyDuck.setFlyBehavior(new FlyNoWay());
		healtyDuck.setQuackBehavior(new Squeak());
		healtyDuck.fly();
		healtyDuck.quack();
	}
}
