package dompoo.spring_security.config;

import dompoo.spring_security.member.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class MyUserDetails extends User {

    private final Long memberId;
    private final String username;

    public MyUserDetails(Member member) {
        super(member.getUsername(), member.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));

        this.memberId = member.getId();
        this.username = member.getUsername();
    }
}
