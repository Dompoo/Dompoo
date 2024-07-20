package Dompoo.SpringCore.discount;

import Dompoo.SpringCore.member.Member;

public interface DiscountPolicy {

    int discount(Member member, int price);
}
