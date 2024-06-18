package lambda.ch1;

public class Main {
    public static void main(String[] args) {

        //MyClass 클래스의 인스턴스를 참조하고 있다.
        MyClass ref1 = new MyClass();
        System.out.println(ref1.max(10, 20));

        //MyInterface 인터페이스를 구현한 익명 객체를 참조하고 있다.
        MyInterface ref2 = new MyInterface() {
            public int max(int a, int b) {
                return a > b ? a : b;
            }
        };
        System.out.println(ref2.max(10, 20));

        //'MyInterface 인터페이스를 구현한 익명 객체'를 람다식으로 나타낼 수 있다.
        //MyInterface는 함수형 인터페이스이다.
        //함수형 인터페이스는 오직 하나의 추상메서드만을 갖는 인터페이스이다.
        //람다식을 통해 해당 추상메서드를 구현하여 익명 객체를 전달한다.
        MyInterface ref3 = (a, b) -> a > b ? a : b;
        System.out.println(ref3.max(10, 20));
    }
}
