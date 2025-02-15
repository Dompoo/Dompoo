package dompoo.securitydemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {
    
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/img/**");
    }
    
    @Bean
    @Order(1) // 등록되는 순서 지정
    public SecurityFilterChain filterChain1(HttpSecurity http) throws Exception {
        log.info("필터체인 1 호출!");
        return http
                .securityMatchers((auth) -> auth.requestMatchers("/user"))
                .authorizeHttpRequests((auth) -> auth.requestMatchers("/user").permitAll())
                .cors((cors) -> cors.disable())
                .csrf((csrf) -> csrf.disable())
                .formLogin((login) -> login.disable())
                .build();
    }
    
    @Bean
    @Order(2)
    public SecurityFilterChain filterChain2(HttpSecurity http) throws Exception {
        log.info("필터체인 2 호출!");
        return http
                .securityMatchers((auth) -> auth.requestMatchers("/admin"))
                .authorizeHttpRequests((auth) -> auth.requestMatchers("/admin").permitAll())
                .build();
    }
}
