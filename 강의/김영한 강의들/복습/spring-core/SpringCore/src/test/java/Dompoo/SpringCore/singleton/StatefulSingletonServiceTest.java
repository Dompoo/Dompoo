package Dompoo.SpringCore.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

class StatefulSingletonServiceTest {

    @Test
    void statefulService() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

        StatefulSingletonService service1 = ac.getBean("statefulSingletonService", StatefulSingletonService.class);
        StatefulSingletonService service2 = ac.getBean("statefulSingletonService", StatefulSingletonService.class);

        service1.order("탕수육", 12000);
        service2.order("짜장면", 8000);

        int price = service1.getPrice();
        System.out.println("service1의 price = " + price);
        Assertions.assertThat(price).isNotEqualTo(12000);
    }

    static class TestConfig {

        @Bean
        public StatefulSingletonService statefulSingletonService() {
            return new StatefulSingletonService();
        }

    }
}