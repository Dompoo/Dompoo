package hello.spring;

import org.springframework.context.annotation.Bean;

//@Configuration
// 이제 Configuration이 아니라, MySpringBootMain을 통하여 ComponentScan으로 빈 등록된다.
public class HelloConfig {
	
	@Bean
	public HelloController helloController() {
		return new HelloController();
	}
}
