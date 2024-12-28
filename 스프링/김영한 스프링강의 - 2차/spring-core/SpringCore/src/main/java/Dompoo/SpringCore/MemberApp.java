package Dompoo.SpringCore;

import Dompoo.SpringCore.member.Grade;
import Dompoo.SpringCore.member.Member;
import Dompoo.SpringCore.member.MemberService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {

    public static void main(String[] args) {

        //이제 스프링 컨테이너를 사용한다.
        //스프링 컨테이너는 @Configuration이 붙은 클래스를 구성정보로 사용한다.
        //@Bean이 붙은 메서드를 모두 호출하여 메서드명-반환값으로 스프링 컨테이너에 등록한다.
        //이때 등록된 빈을 스프링 빈이라고 부른다.
        //그 후에는 스프링 컨테이너는 필요한 의존관계를 주입한다.
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        //등록된 빈 중에 이름이 memberService인 빈을 조회해온다.
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);//기본적으로 bean은 메서드명으로 등록된다.

        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("member = " + member);
        System.out.println("findMember = " + findMember);
    }
}
