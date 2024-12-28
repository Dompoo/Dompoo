package Dompoo.SpringCore.beanfind;

import Dompoo.SpringCore.AppConfig;
import Dompoo.SpringCore.member.MemberService;
import Dompoo.SpringCore.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextBasicFind {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("빈 이름으로 조회")
    void findBeanByName() {
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        Assertions.assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    //주의!! 타입으로 조회시 빈이 여러개 조회될 수 있다. (보통 상속관계에 놓인 빈의 경우)
    //이 경우 빈 이름으로 조회하거나,
    //여러개를 조회하는 getBeansOfType() 메서드로 수정해야 한다.
    @Test
    @DisplayName("이름없이 인터페이스 타입으로 조회")
    void findBeanByType() {
        MemberService memberService = ac.getBean(MemberService.class);
        Assertions.assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("이름없이 구체 타입으로 타입으로 조회")
    void findBeanByName2() {
        MemberService memberService = ac.getBean("memberService", MemberServiceImpl.class);
        Assertions.assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }
    
    @Test
    @DisplayName("빈 이름으로 조회 실패")
    void findBeanByNameFail() {
        Assertions.assertThatThrownBy(() ->
                        ac.getBean("이상한 이름", MemberService.class))
                .isInstanceOf(NoSuchBeanDefinitionException.class);
    }

}
