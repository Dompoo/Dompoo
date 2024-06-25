package Dompoo.SpringCore.singleton;

import Dompoo.SpringCore.AppConfig;
import Dompoo.SpringCore.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SingletonTest {
    
    @Test
    @DisplayName("스프링 없는 순수 DI 컨테이너")
    void pureContainer() {
        AppConfig appConfig = new AppConfig();

        MemberService memberService1 = appConfig.memberService();
        MemberService memberService2 = appConfig.memberService();

        //계속해서 새로운 객체를 생성한다.
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        Assertions.assertThat(memberService1).isNotSameAs(memberService2);
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체")
    void useSingleton() {

        // 이제 아래와 같이 사용하지 못한다.
//        SingletonService singletonService = new SingletonService();

        SingletonService instance1 = SingletonService.getInstance();
        SingletonService instance2 = SingletonService.getInstance();

        //같은 객체를 계속해서 가져온다.
        System.out.println("instance1 = " + instance1);
        System.out.println("instance2 = " + instance2);

        Assertions.assertThat(instance1).isSameAs(instance2);
    }
}
