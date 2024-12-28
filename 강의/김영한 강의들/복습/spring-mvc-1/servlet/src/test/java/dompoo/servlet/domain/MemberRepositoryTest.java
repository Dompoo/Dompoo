package dompoo.servlet.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemberRepositoryTest {

    MemberRepository memberRepository = MemberRepository.getInstance();

    @AfterEach
    void setUp() {
        memberRepository.clearStore();
    }

    @Test
    void save() {
        //given
        Member member = new Member("lee", 20);

        //when
        Member savedMember = memberRepository.save(member);

        //then
        Member findMember = memberRepository.findById(savedMember.getId());
        assertThat(findMember).isEqualTo(savedMember);
    }

    @Test
    void findAll() {
        //given
        Member member1 = new Member("lee", 20);
        Member member2 = new Member("kim", 30);
        memberRepository.save(member1);
        memberRepository.save(member2);

        //when
        List<Member> findAll = memberRepository.findAll();

        //then
        assertThat(findAll.size()).isEqualTo(2);
        assertThat(findAll).contains(member1, member2);
    }
}