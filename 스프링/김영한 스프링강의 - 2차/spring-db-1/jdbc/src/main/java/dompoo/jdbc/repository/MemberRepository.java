package dompoo.jdbc.repository;

import dompoo.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.NoSuchElementException;

public interface MemberRepository {
	
	
	public Member save(Member member);
	
	public Member findById(String memberId);
	
	public int update(String memberId, int money);
	
	public int delete(String memberId);
	
	public int deleteAll();
}
