package dompoo.spring_security.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private Long memberId;
    private String username;
    private final String message = "인증성공!";

    @Builder
    public LoginResponse(Long memberId, String username) {
        this.memberId = memberId;
        this.username = username;
    }
}
