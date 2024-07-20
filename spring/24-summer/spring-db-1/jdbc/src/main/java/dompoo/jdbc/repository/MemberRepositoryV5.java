package dompoo.jdbc.repository;

import dompoo.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;

import javax.sql.DataSource;
import java.sql.*;
import java.util.NoSuchElementException;

/**
 * JdbcTemplate
 */
@Slf4j
public class MemberRepositoryV5 implements MemberRepository {
	
	private final JdbcTemplate jdbcTemplate;
	
	public MemberRepositoryV5(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public Member save(Member member) {
		String sql = "insert into member(member_id, money) values (?,?)";
		jdbcTemplate.update(sql, member.getMemberId(), member.getMoney());
		return member;
	}
	
	@Override
	public Member findById(String memberId) {
		String sql = "select * from member where member_id = ?";
		return jdbcTemplate.queryForObject(sql, memberRowMapper(), memberId);
	}
	
	@Override
	public int update(String memberId, int money) {
		String sql = "update member set money = ? where member_id = ?";
		return jdbcTemplate.update(sql, money, memberId);
	}
	
	@Override
	public int delete(String memberId) {
		String sql = "delete from member where member_id = ?";
		return jdbcTemplate.update(sql, memberId);
	}
	
	@Override
	public int deleteAll() {
		String sql = "delete from member";
		return jdbcTemplate.update(sql);
	}
	
	private RowMapper<Member> memberRowMapper() {
		return (rs, rowNum) -> {
			Member member = new Member();
			member.setMemberId(rs.getString("member_id"));
			member.setMoney(rs.getInt("money"));
			return member;
		};
	}
}
