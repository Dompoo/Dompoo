package dompoo.securitydemo.cors;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;
import java.util.List;

public class DemoCorsConfig implements CorsConfigurationSource {
    
    @Override
    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")); // 허용하는 메서드
        config.setAllowCredentials(true); // 자격 증명을 포함할 수 있는가? -> 포함된다면 Origin을 명시해주어야 한다.
        config.setAllowedHeaders(Collections.singletonList("*")); // 허용하는 헤더
        config.setMaxAge(3600L); // 브라우저가 Preflight 응답값을 캐싱해둘 시간
        return config;
    }
}
