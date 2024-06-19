package Dompoo.SpringCore.discount;

import Dompoo.SpringCore.member.Grade;
import Dompoo.SpringCore.member.Member;

public class RateDiscountPolicy implements DiscountPolicy {

    private final float discountRate = 0.1F;

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return (int) (price * discountRate);
        } else {
            return 0;
        }
    }
}
