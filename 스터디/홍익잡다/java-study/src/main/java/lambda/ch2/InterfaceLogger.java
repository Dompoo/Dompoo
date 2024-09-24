package lambda.ch2;

public class InterfaceLogger {

    void interfaceParam(MyFunction myFunction) {
        System.out.println("logger start");
        myFunction.myMethod();
        System.out.println("logger end");
        System.out.println();
    }
}
