package hello.proxy.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

public class BasicTest {

    @Test
    void basicConfig() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(BasicConfig.class);

        //A는 빈으로 등록되었을 것이다.
        A beanA = ac.getBean("beanA", A.class);
        beanA.helloA();

        //B는 빈으로 등록되지 않았을 것이다.
        Assertions.assertThatThrownBy(() -> ac.getBean("beanB"))
                .isExactlyInstanceOf(NoSuchBeanDefinitionException.class);
    }

    @Slf4j
    static class BasicConfig {
        @Bean(name = "beanA")
        public A a() {
            return new A();
        }
    }

    @Slf4j
    static class A {
        public void helloA() {
            log.info("helloA");
        }
    }

    @Slf4j
    static class B {
        public void helloB() {
            log.info("helloB");
        }
    }

    @Test
    void postProcessor1() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(PostProcessorConfig.class);

        //B는 빈으로 등록되었을 것이다.
        B bean = ac.getBean("beanA", B.class);
        bean.helloB();

        //A는 빈으로 등록되지 않았을 것이다.
        Assertions.assertThatThrownBy(() -> ac.getBean(A.class))
                .isExactlyInstanceOf(NoSuchBeanDefinitionException.class);
    }

    @Slf4j
    static class PostProcessorConfig {
        @Bean(name = "beanA")
        public A a() {
            return new A();
        }

        @Bean
        private AtoBPostProcessor atoBPostProcessor() {
            return new AtoBPostProcessor();
        }
    }

    @Slf4j
    static class AtoBPostProcessor implements BeanPostProcessor {

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            log.info("beanName={}, bean={}", beanName, bean);

            if (bean instanceof A) {
                return new B();
            }
            return bean;
        }
    }
}
