package Dompoo.SpringCore.member;

public class MemberServiceImpl implements MemberService {

//    private final MemberRepository memberRepository = new MemoryMemberRepository();
    //다형성은 고려되었지만, 구현체를 변경할 때 코드의 수정이 생긴다. OCP, DIP 위반!!!

    private final MemberRepository memberRepository;

    //구현체는 외부에서 주입해준다.
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
