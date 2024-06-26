package Dompoo.SpringCore.discount;

import Dompoo.SpringCore.member.Grade;
import Dompoo.SpringCore.member.Member;
import org.springframework.stereotype.Component;

@Component
public class FixDiscountPolicy implements DiscountPolicy {

    private final int fixDiscountAmount = 1000;

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return  fixDiscountAmount;
        } else {
            return 0;
        }
    }
}
