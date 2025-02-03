package hello;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EnvironmentCheck {
	
	private final Environment env;
	
	@PostConstruct
	public void init() {
		String url = env.getProperty("url");
		String username = env.getProperty("username");
		String password = env.getProperty("password");
	}
}
