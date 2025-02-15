package dompoo.securitydemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class DemoController {
    
    @GetMapping("/user")
    public String get() {
        System.out.println("SecurityContextHolder.getContextHolderStrategy() = " + SecurityContextHolder.getContextHolderStrategy());
        System.out.println("SecurityContextHolder.getContext() = " + SecurityContextHolder.getContext());
        System.out.println("SecurityContextHolder.getContext().getAuthentication() = " + SecurityContextHolder.getContext().getAuthentication());
        System.out.println("SecurityContextHolder.getContext().getAuthentication() = " + SecurityContextHolder.getContext().getAuthentication());
        return "OK!!";
    }
}
