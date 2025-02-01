package hello.member;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberRepositoryTest {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Transactional
	@Test
	void memberTest() {
		//given
		Member member = new Member("id1", "member1");
		memberRepository.initTable();
		
		//when
		memberRepository.save(member);
		Member findMember = memberRepository.find(member.getMemberId());
		
		//then
		assertThat(findMember.getMemberId()).isEqualTo(member.getMemberId());
		assertThat(findMember.getName()).isEqualTo(member.getName());
	}
}
