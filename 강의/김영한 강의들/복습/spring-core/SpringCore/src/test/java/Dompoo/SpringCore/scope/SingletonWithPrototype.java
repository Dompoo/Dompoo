package Dompoo.SpringCore.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


public class SingletonWithPrototype {

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean proto1 = ac.getBean(PrototypeBean.class);
        proto1.addCount();
        Assertions.assertThat(proto1.getCount()).isEqualTo(1);

        PrototypeBean proto2 = ac.getBean(PrototypeBean.class);
        proto2.addCount();
        Assertions.assertThat(proto2.getCount()).isEqualTo(1);
    }

    @Test
    void singletonUsePrototype() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);
        ClientBean cleint1 = ac.getBean(ClientBean.class);
        int count1 = cleint1.logic();
        Assertions.assertThat(count1).isEqualTo(1);

        ClientBean client2 = ac.getBean(ClientBean.class);
        int count2 = client2.logic();
        Assertions.assertThat(count2).isEqualTo(2);
        //예상치 못한 상황! 우리의 의도는 로직을 호출할 때 마다 새로 생성하고 싶다.
    }

    @Test
    void singletonUseProvider() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBeanWithProvider.class, PrototypeBean.class);
        ClientBeanWithProvider cleint1 = ac.getBean(ClientBeanWithProvider.class);
        int count1 = cleint1.logic();
        Assertions.assertThat(count1).isEqualTo(1);

        ClientBeanWithProvider client2 = ac.getBean(ClientBeanWithProvider.class);
        int count2 = client2.logic();
        Assertions.assertThat(count2).isEqualTo(1);
        //이제는 로직을 수행할 때마다 프로토타입 빈을 생성한다.
    }

    @Scope("singleton")
    static class ClientBean {
        private final PrototypeBean protoTypeBean;

        @Autowired
        public ClientBean(PrototypeBean protoTypeBean) {
            this.protoTypeBean = protoTypeBean;
        }

        public int logic() {
            protoTypeBean.addCount();
            return protoTypeBean.getCount();
        }
    }

    @Scope("singleton")
    static class ClientBeanWithProvider {

        @Autowired
        private ObjectProvider<PrototypeBean> prototypeBeanProvider;

        public int logic() {
            PrototypeBean prototypeBean = prototypeBeanProvider.getObject();
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }

    @Scope("prototype")
    @Getter
    static class PrototypeBean {
        private int count = 0;

        public void addCount() {
            count++;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init " + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }

    }
}
