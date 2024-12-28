package dompoo.servlet.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {

    private Long id;
    private String username;
    private Integer age;

    public Member() {
    }

    public Member(String username, Integer age) {
        this.username = username;
        this.age = age;
    }
}
