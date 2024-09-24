package hello.core.singleton;

public class SingletonService {

    private static final SingletonService instance = new SingletonService();

    //위 클래스의 객체를 사용하려면 getInstance로 반환 -> 항상 같은 객체 반환
    public static SingletonService getInstance() {
        return instance;
    }

    //생성자를 private으로 설정 -> 외부에서 객체 생성을 막는다.
    private SingletonService() {

    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }
}
