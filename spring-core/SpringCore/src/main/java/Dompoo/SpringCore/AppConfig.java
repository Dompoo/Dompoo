package Dompoo.SpringCore;

import Dompoo.SpringCore.discount.RateDiscountPolicy;
import Dompoo.SpringCore.member.MemberService;
import Dompoo.SpringCore.member.MemberServiceImpl;
import Dompoo.SpringCore.member.MemoryMemberRepository;
import Dompoo.SpringCore.order.OrderService;
import Dompoo.SpringCore.order.OrderServiceImpl;

public class AppConfig {

    public MemberService memberService() {
        //생성자를 통해 '의존관계를 주입'한다.
        return new MemberServiceImpl(new MemoryMemberRepository());
    }

    public OrderService orderService() {
        //생성자를 통해 '의존관계를 주입'한다.
        return new OrderServiceImpl(new MemoryMemberRepository(), new RateDiscountPolicy());
    }
}
