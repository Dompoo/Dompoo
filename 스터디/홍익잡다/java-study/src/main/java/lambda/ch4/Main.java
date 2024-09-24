package lambda.ch4;

import java.util.function.Function;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        Function<String, Integer> f1 = (str) -> str.length();
        Function<Integer, Integer> f2 = (i) -> i + 5;

        //andThen으로 합성
        Function<String, Integer> andThen = f1.andThen(f2);
        System.out.println(andThen.apply("Hello"));

        //compose로 합성
        Function<String, Integer> compose = f2.compose(f1);
        System.out.println(compose.apply("World!!"));

        Predicate<Integer> p1 = (i) -> i < 30;
        Predicate<Integer> p2 = (i) -> 10 < i;

        //and로 합성
        Predicate<Integer> and = p1.and(p2);
        System.out.println(and.test(9));
        System.out.println(and.test(15));
        System.out.println(and.test(31));

        //or로 합성
        Predicate<Integer> or = p1.or(p2);
        System.out.println(or.test(9));
        System.out.println(or.test(15));
        System.out.println(or.test(31));

        //negate
        Predicate<Integer> negate = p1.negate();
        System.out.println(negate.test(9));
        System.out.println(negate.test(15));
        System.out.println(negate.test(31));
    }
}
