package Dompoo.SpringCore.common;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class MyLogger {

    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message) {
        System.out.println("[" + uuid + "][" + requestURL + "][" + message + "]");
    }

    @PostConstruct
    public void init() {
        this.uuid = java.util.UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] request scope bean INIT : " + this);
    }

    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "] request scope bean CLOSE : " + this);
    }
}
