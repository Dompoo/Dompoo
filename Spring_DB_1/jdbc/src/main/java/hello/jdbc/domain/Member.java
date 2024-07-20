package hello.jdbc.domain;

import lombok.Data;

@Data
public class Member {

    private String memberId;
    private int Money;

    public Member() {
    }

    public Member(String memberId, int money) {
        this.memberId = memberId;
        Money = money;
    }
}
