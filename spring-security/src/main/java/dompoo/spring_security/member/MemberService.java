package dompoo.spring_security.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Transient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member save(Member member) {
        return memberRepository.save(member);
    }

    public Member findById(Long id) {
        return memberRepository.findById(id).get();
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }
}
