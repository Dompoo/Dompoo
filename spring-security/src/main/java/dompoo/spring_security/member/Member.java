package dompoo.spring_security.member;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {

    private Long id;
    private String username;
    private String password;

    @Builder
    public Member(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
