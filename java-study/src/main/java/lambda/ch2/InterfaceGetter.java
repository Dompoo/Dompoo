package lambda.ch2;

public class InterfaceGetter {

    MyFunction getMyFunction() {
        return () -> System.out.println("MyFunction from Getter");
    }
}
