package hello.datasource;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
//@ConfigurationProperties("my.datasource")
public class MyDataSourcePropertiesV1 {
	
	private final String url;
	private final String username;
	private final String password;
	private final Etc etc;
	
	@Getter
	@RequiredArgsConstructor
	public static class Etc {
		private final int maxConnection;
		private final Duration timeout;
		private final List<String> options = new ArrayList<>();
	}
}
