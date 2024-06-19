package Dompoo.SpringCore;

public interface MemberService {

    void join(Member member);

    Member findMember(Long memberId);
}
