package dompoo.spring_security.member;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MemberRepository {

    private final Map<Long, Member> repository = new HashMap<>();
    private static long sequence = 0L;

    public Member save(Member member) {
        repository.put(sequence++, Member.builder()
                .id(sequence)
                .username(member.getUsername())
                .password(member.getPassword())
                .build());
        return member;
    }

    public Member findById(Long id) {
        return repository.get(id);
    }

    public List<Member> findAll() {
        return new ArrayList<>(repository.values());
    }
}
