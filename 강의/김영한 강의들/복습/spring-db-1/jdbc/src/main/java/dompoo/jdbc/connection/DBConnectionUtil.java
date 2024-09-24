package dompoo.jdbc.connection;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static dompoo.jdbc.connection.ConnectionConst.*;

@Slf4j
public class DBConnectionUtil {
	
	public static Connection getConnection() {
		try {
			Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			// DriverManager는 라이브러리의 드라이버들을 인식한다.
			// 드라이버들에게 해당 URL 정보를 던져서 handle할 수 있는지 체크한다.
			// (정확히는 isDriverAllowed 메서를 통해 체크된다.)
			// 가능하다면 DB에 연결한 후 커넥션을 반환한다.
			// 불가능하다면 다음 드라이버에게 기회를 넘긴다.
			log.info("connection : {} connection.getClass : {}", connection, connection.getClass());
			return connection;
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}
	
	private DBConnectionUtil() {}
}
