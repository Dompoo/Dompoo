package Dompoo.SpringCore.singleton;

public class SingletonService {

    //스태틱 영역에 미리 단 하나의 인스턴스를 만든다.
    private static final SingletonService instance = new SingletonService();

    //외부에서 인스턴스를 접근할 수 있도록 한다.
    public static SingletonService getInstance() {
        return instance;
    }

    //생성자를 private으로 막아서 외부 생성을 막는다.
    private SingletonService() {
    }

    public void logic() {
        System.out.println("singletone logic");
    }
}
