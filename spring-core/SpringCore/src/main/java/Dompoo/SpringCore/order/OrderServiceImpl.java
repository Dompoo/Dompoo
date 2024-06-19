package Dompoo.SpringCore.order;

import Dompoo.SpringCore.discount.DiscountPolicy;
import Dompoo.SpringCore.discount.FixDiscountPolicy;
import Dompoo.SpringCore.member.Member;
import Dompoo.SpringCore.member.MemberRepository;
import Dompoo.SpringCore.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {

        Member findMember = memberRepository.findById(memberId);
        int discountAmount = discountPolicy.discount(findMember, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountAmount);
    }
}
