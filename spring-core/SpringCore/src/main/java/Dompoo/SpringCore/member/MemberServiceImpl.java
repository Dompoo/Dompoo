package Dompoo.SpringCore.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService {

//    private final MemberRepository memberRepository = new MemoryMemberRepository();
    //다형성은 고려되었지만, 구현체를 변경할 때 코드의 수정이 생긴다. OCP, DIP 위반!!!

    private final MemberRepository memberRepository;

    //구현체는 외부에서 주입해준다.
    @Autowired //@Component와 짝꿍, @Component로 빈 등록이 될 때 의존관계를 자동 주입해준다.
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
