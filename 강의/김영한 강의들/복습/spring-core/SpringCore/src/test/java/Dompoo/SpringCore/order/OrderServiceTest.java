package Dompoo.SpringCore.order;

import Dompoo.SpringCore.AppConfig;
import Dompoo.SpringCore.member.Grade;
import Dompoo.SpringCore.member.Member;
import Dompoo.SpringCore.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderServiceTest {

    @Test
    void order() {
        //given
        AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();
        OrderService orderService = appConfig.orderService();

        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        //when
        Order order = orderService.createOrder(member.getId(), "itemA", 10000);

        //then
        Assertions.assertThat(order.getItemName()).isEqualTo("itemA");
        Assertions.assertThat(order.getDiscountAmount()).isEqualTo(1000);
    }

}