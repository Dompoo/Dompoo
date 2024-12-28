package dompoo.jdbc.repository;

import dompoo.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

@Slf4j
class MemberRepositoryV0Test {
	
	MemberRepositoryV0 repository = new MemberRepositoryV0();
	
	@Test
	void crud() throws SQLException {
		repository.deleteAll();
		
		String id = "hello1";
		Member member = new Member(id, 10000);
		repository.save(member);
		
		Member findMember = repository.findById(id);
		assertThat(findMember).isEqualTo(member);
		
		int effected1 = repository.update(id, 10000000);
		Member updatedMember = repository.findById(id);
		assertThat(effected1).isEqualTo(1);
		assertThat(updatedMember.getMoney()).isEqualTo(10000000);
		
		int effected2 = repository.delete(id);
		assertThat(effected2).isEqualTo(1);
		assertThatThrownBy(() ->
				repository.findById(id))
				.isInstanceOf(NoSuchElementException.class);
	}
}