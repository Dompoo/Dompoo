package dompoo.securitydemo.filter;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Slf4j
public class DemoGenericFilterBean extends GenericFilterBean {
    
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("요청에서 DemoGenericFilterBean 호출!!");
        filterChain.doFilter(servletRequest, servletResponse);
        log.info("응답에서 DemoGenericFilterBean 호출!!");
    }
}
