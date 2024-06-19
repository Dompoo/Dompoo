package Dompoo.SpringCore.order;

import Dompoo.SpringCore.member.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderServiceTest {

    private final MemberService memberService = new MemberServiceImpl();
    private final OrderService orderService = new OrderServiceImpl();

    @Test
    void order() {
        //given
        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        //when
        Order order = orderService.createOrder(member.getId(), "itemA", 10000);

        //then
        Assertions.assertThat(order.getItemName()).isEqualTo("itemA");
        Assertions.assertThat(order.getDiscountAmount()).isEqualTo(1000);
    }

}