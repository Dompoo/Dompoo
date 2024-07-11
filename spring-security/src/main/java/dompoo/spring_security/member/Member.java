package dompoo.spring_security.member;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;

    @Builder
    public Member(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
