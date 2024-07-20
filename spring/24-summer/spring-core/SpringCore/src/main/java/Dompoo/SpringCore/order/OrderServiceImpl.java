package Dompoo.SpringCore.order;

import Dompoo.SpringCore.discount.DiscountPolicy;
import Dompoo.SpringCore.member.Member;
import Dompoo.SpringCore.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {


//    private final MemberRepository memberRepository = new MemoryMemberRepository();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    //여기서 다른 DiscountPolicy 구현체로 갈아끼울려면 OCP, DIP가 위반된다.
    //OrderServiceImpl이 역할과 구현에 모두 의존하고 있기 때문이다.
    //이를 해결하기 위해서는 구현체를 '생성하고 주입'하는 역할을 수행하는 다른 무언가가 필요하다! -> AppConfig

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    //구현체는 외부에서 주입해준다.
    @Autowired //@Component와 짝꿍, @Component로 빈 등록이 될 때 의존관계를 자동 주입해준다.
    public OrderServiceImpl(MemberRepository memberRepository, @Qualifier("rateDiscountPolicy") DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {

        Member findMember = memberRepository.findById(memberId);
        int discountAmount = discountPolicy.discount(findMember, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountAmount);
    }
}
