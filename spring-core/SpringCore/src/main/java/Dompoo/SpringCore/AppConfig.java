package Dompoo.SpringCore;

import Dompoo.SpringCore.discount.DiscountPolicy;
import Dompoo.SpringCore.discount.RateDiscountPolicy;
import Dompoo.SpringCore.member.MemberRepository;
import Dompoo.SpringCore.member.MemberService;
import Dompoo.SpringCore.member.MemberServiceImpl;
import Dompoo.SpringCore.member.MemoryMemberRepository;
import Dompoo.SpringCore.order.OrderService;
import Dompoo.SpringCore.order.OrderServiceImpl;

public class AppConfig {

    public MemberService memberService() {
        //생성자를 통해 '의존관계를 주입'한다.
        return new MemberServiceImpl(memberRepositoy());
    }

    public OrderService orderService() {
        //생성자를 통해 '의존관계를 주입'한다.
        return new OrderServiceImpl(memberRepositoy(), discountPolicy());
    }

    public MemberRepository memberRepositoy() {
        //구현체를 갈아끼우기 위해서는 이 부분만 수행하면 된다.
        return new MemoryMemberRepository();
    }

    private static DiscountPolicy discountPolicy() {
        //구현체를 갈아끼우기 위해서는 이 부분만 수행하면 된다.
        return new RateDiscountPolicy();
    }
}
