package Dompoo.SpringCore;

import Dompoo.SpringCore.discount.DiscountPolicy;
import Dompoo.SpringCore.discount.RateDiscountPolicy;
import Dompoo.SpringCore.member.MemberRepository;
import Dompoo.SpringCore.member.MemberService;
import Dompoo.SpringCore.member.MemberServiceImpl;
import Dompoo.SpringCore.member.MemoryMemberRepository;
import Dompoo.SpringCore.order.OrderService;
import Dompoo.SpringCore.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public MemberService memberService() {
        //생성자를 통해 '의존관계를 주입'한다.
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public OrderService orderService() {
        //생성자를 통해 '의존관계를 주입'한다.
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public MemberRepository memberRepository() {
        //구현체를 갈아끼우기 위해서는 이 부분만 수행하면 된다.
        return new MemoryMemberRepository();
    }

    @Bean
    private static DiscountPolicy discountPolicy() {
        //구현체를 갈아끼우기 위해서는 이 부분만 수행하면 된다.
        return new RateDiscountPolicy();
    }
}
