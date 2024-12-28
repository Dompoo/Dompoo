package lambda.ch2;

public class Main {
    public static void main(String[] args) {
        InterfaceLogger logger = new InterfaceLogger();
        InterfaceGetter getter = new InterfaceGetter();

        MyFunction f1 = new MyFunction() {
            @Override
            public void myMethod() {
                System.out.println("anonymous");
            }
        };
        MyFunction f2 = () -> System.out.println("lambda");

        logger.interfaceParam(f1);
        logger.interfaceParam(f2);
        //함수형 인터페이스가 매개변수라는 것은
        //함수형 인터페이스를 구현한 익명 객체의 참조 변수를 넣어주어야 한다는 것이다.

        //또는 아래와 같이 바로 넣어줄 수 있다. (이제부터는 람다식만 사용)
        logger.interfaceParam(() -> System.out.println("lambda"));

        //반환된 MyFunction을 사용할 수도 있다.
        MyFunction myFunction = getter.getMyFunction();
        myFunction.myMethod();
    }
}
