package hello.pay;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Slf4j
@Configuration
public class PayClientConfig {

    @Bean
    @Profile("default")
    public LocalPayClient localPayClient() {
        log.info("로컬 payClient 등록");
        return new LocalPayClient();
    }
    
    @Bean
    @Profile("prod")
    public ProdPayClient prodPayClient() {
        log.info("프로덕션 payClient 등록");
        return new ProdPayClient();
    }
}
