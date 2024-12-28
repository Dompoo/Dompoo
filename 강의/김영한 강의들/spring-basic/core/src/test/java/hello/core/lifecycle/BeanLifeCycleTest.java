package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest() {
        //.close()를 제공하는 ApplicationContext
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();
    }

    @Configuration
    static class LifeCycleConfig {

        //@Bean(initMethod = "init", destroyMethod = "close")
        @Bean
        public NetworkClient networkClient() {
            //아래와 같이 객체를 생성한 후 초기화가 되는 경우(객체 생성과 초기화는 유지보수 측면에서 분리하는 것이 좋다.)
            NetworkClient networkClient = new NetworkClient(); //객체 생성
            networkClient.setUrl("https://hello-spring.dev"); //초기화
            return networkClient;
        }
    }
}
