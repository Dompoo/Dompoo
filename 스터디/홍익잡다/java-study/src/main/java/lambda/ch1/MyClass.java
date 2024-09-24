package lambda.ch1;

/*
자바에서 메서드를 만들기 위해서는 클래스가 필요하다.
나는 어떠한 '동작' 만을 전달하고 싶은데, 기존의 방식대로라면
해당 동작을 수행하는 '메서드'를 만들어야 하고, 해당 메서드를 가진 '클래스'를 만들어야 한다.
*/
public class MyClass {
    public int max(int a, int b) {
        return a > b ? a : b;
    }
}
