package Dompoo.SpringCore.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);

        ac.close();
    }

    @Configuration
    static class LifeCycleConfig {

//        @Bean(initMethod = "init", destroyMethod = "close") //외부 라이브러리의 경우 이와 같은 방법 사용
        @Bean
        public NetworkClient networkClient() {

            NetworkClient networkClient = new NetworkClient(); //생성자에서 url 정보 없이 메서드들이 호출되고 있다.
            //즉, 스프링 빈은 [객체 생성] -> [의존관계 주입] 의 순서로 진행된다.
            //더 나아가서는
            //[빈 생성] -> [의존관계 주입] -> [초기화 콜백] -> [사용] -> [소멸전 콜백] -> [스프링 종료]
            //의 순서로 진행되어, 콜백을 통해 여러 작업을 수행할 수 있다.
            networkClient.setUrl("http://hello-world");
            return networkClient;
        }
    }
}
