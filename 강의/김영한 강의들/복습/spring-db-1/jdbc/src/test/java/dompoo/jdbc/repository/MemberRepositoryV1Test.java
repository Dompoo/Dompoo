package dompoo.jdbc.repository;

import com.zaxxer.hikari.HikariDataSource;
import dompoo.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static dompoo.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
class MemberRepositoryV1Test {
	
	MemberRepositoryV1 repository;
	
	@BeforeEach
	void setUp() {
		// 기본 DriverManager를 통한 항상 새로운 커넥션 획득
//		DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
		
		// HikariCP 커넥션 풀을 통한 커넥션 풀링
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setJdbcUrl(URL);
		dataSource.setUsername(USERNAME);
		dataSource.setPassword(PASSWORD);
		
		// 이제 해당 MemberRepository는 원하는 DataSource를 받아서 작동할 수 있다.
		repository = new MemberRepositoryV1(dataSource);
	}
	
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