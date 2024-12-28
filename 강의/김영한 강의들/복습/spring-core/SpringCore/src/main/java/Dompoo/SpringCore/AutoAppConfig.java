package Dompoo.SpringCore;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

//@Bean으로 하나하나 등록하는 것은 프로젝트가 너무 커지면 귀찮아질 수 있다.
//이를 위해 자동으로 빈 등록해주는 @ComponentScan과 @Component 어노테이션이 존재한다.
@Configuration
@ComponentScan(
        //@Configuration에도 @Component가 붙어있기 때문에 자동으로 빈 등록된다.
        //AppConfig를 제외하기 위한 옵션, 예제코드를 없앤다면 필요없다.
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {
}
