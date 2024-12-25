- `try` 문에서 자원을 명시하고, `try`가 끝나면 자동으로 자원을 반환하는 기능이다.
	- 자바 수준에서 자원의 반환을 보장해준다.
	- 해당 자원의 스코프가 지역변수처럼 사용되어서 스코프가 좁아서 가독성이 좋다.
	- `try` 문이 끝나면 곧바로 실행되므로 자원 반환이 조금 더 빠르다.
- 기존에 `finally`에서 수행하던 자원 반환 작업을 이곳에 적어주면 된다.
- 이를 사용하기 위해서는 `AutoCloseable` 인터페이스를 구현해야 한다.
	- `close` 메서드만 구현해주면 된다.
- 예외가 터지더라도 `catch`문 직전에 해당 `close` 메서드가 호출된다.
```java
public class DatabaseConnection implements AutoCloseable {  
      
    private static Connection connection;  
      
    public void connect(String url) {  
        connection = new DatabaseConnection(url);  
    }  
      
    @Override  
    public void close() {  
        connection = null;  
    }  
}

public void run() {  
    try (DatabaseConnection dc = new DatabaseConnection()) {  
        dc.connect("mysql@connection");  
        dc.send("hello world");  
        dc.send("hello java");  
    }  
}
```