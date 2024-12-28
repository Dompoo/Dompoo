package lambda.ch3;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) {
        //자주쓰이는 함수형 인터페이스들은
        //이미 java.util.function 패키지와 java.lang 패키지에 다 정의되어있다.

        // () -> {}
        Runnable runnable = () -> System.out.println("I'm Runnable");
        runnable.run();

        // () -> {T}
        Supplier<String> supplier = () -> "I'm Supplier";
        System.out.println(supplier.get());

        // (T) -> {}
        Consumer<String> consumer = (str) -> System.out.println(str);
        consumer.accept("I'm Consumer");

        // (T) -> {R}
        Function<String, Integer> function = (str) -> str.length();
        System.out.println(function.apply("I'm Function"));

        // (T) -> {Boolean}
        Predicate<String> predicate = (str) -> str.equals("I'm Predicate");
        System.out.println(predicate.test("I'm Predicate"));
    }
}
