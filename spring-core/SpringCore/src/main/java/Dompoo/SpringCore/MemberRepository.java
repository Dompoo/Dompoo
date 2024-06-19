package Dompoo.SpringCore;

public interface MemberRepository {

    void save(Member member);

    Member findById(Long memberId);
}
