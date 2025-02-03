package hello.config;

import hello.datasource.MyDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.unit.DataUnit;

import java.time.Duration;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class MyDataSourceEnvConfig {
	
	private final Environment environment;
	
	@Bean
	public MyDataSource myDataSource() {
		String url = environment.getProperty("my.datasource.url");
		String username = environment.getProperty("my.datasource.username");
		String password = environment.getProperty("my.datasource.password");
		Integer maxConnection = environment.getProperty("my.datasource.etc.max-connection", Integer.class);
		Duration timeout = environment.getProperty("my.datasource.etc.timeout", Duration.class);
		List<String> options = environment.getProperty("my.datasource.etc.options", List.class);
		
		return new MyDataSource(url, username, password, maxConnection, timeout, options);
	}
}
