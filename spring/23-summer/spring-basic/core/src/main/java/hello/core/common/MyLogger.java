package hello.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
//Proxy를 통해 CGLIB가 조작된 MyLogger 생성 -> 실제 객체 조회를 실제 사용시 까지 미룬다.
//클라이언트 코드를 고치지 않고 마치 싱글톤 빈을 사용하듯이 편하게 Request스코프를 사용하는 것이다.
public class MyLogger {

    private String uuid;
    private String requestURL;


    public void setRequestURL(String id) {
        this.requestURL = id;
    }

    public void log(String message) {
        System.out.println("[" + uuid + "][" + requestURL + "] " + message);
    }

    @PostConstruct
    public void init() {
        uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] request scope bean create:" + this);
    }

    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "] request scope bean close:" + this);
    }

}
