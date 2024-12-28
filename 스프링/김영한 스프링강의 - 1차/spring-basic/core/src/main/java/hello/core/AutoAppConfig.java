package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "hello.core", //어디서부터 찾을지 지정
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
//AppConfig는 등록되면 안되기 때문! 그냥 예제를 그대로 남겨두기 위해 사용
//ComponentScan은 Component에노테이션이 붙은 것들을 스프링 빈으로 등록

public class AutoAppConfig {

}
