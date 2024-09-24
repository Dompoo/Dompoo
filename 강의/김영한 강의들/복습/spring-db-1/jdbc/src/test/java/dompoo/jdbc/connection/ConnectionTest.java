package dompoo.jdbc.connection;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static dompoo.jdbc.connection.ConnectionConst.*;

@Slf4j
public class ConnectionTest {
	
	@Test
	void driverManager() throws SQLException {
		Connection connection1 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		Connection connection2 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		
		log.info("connection1 : {} connection.getClass : {}", connection1, connection1.getClass());
		log.info("connection2 : {} connection.getClass : {}", connection2, connection2.getClass());
	}
	
	@Test
	void dataSourceDriverManager() throws SQLException {
	    // DriverManagerDataSource - 항상 새로운 커넥션을 획득
		DataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
		
		// DataSource는 처음만 파라미터를 넘겨주면, 그 다음부터는 getConnection을 그냥 호출할 수 있다.
		// 즉, 설정과 사용을 분리하는 것이다.
		useDataSource(dataSource);
	}
	
	@Test
	void dataSourceConnectionPool() throws SQLException, InterruptedException {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setJdbcUrl(URL);
		dataSource.setUsername(USERNAME);
		dataSource.setPassword(PASSWORD);
		dataSource.setMaximumPoolSize(10);
		dataSource.setPoolName("My Pool");
		// 커넥션 풀에서 커넥션을 생성하는 작업은 별도의 쓰레드에서 작업된다.
		// 이는 풀을 채우는 시간이 오래 걸리는 작업이기 때문에, 애플리케이션 로딩 시간을 줄이기 위한 선택이다.
		
		useDataSource(dataSource);
		Thread.sleep(1000);
	}
	
	private void useDataSource(DataSource dataSource) throws SQLException {
		Connection con1 = dataSource.getConnection();
		Connection con2 = dataSource.getConnection();
		
		log.info("connection1 : {} connection.getClass : {}", con1, con1.getClass());
		log.info("connection2 : {} connection.getClass : {}", con2, con2.getClass());
	}
}
