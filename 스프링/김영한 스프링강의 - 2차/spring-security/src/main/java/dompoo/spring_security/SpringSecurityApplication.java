package dompoo.spring_security;

import dompoo.spring_security.member.Member;
import dompoo.spring_security.member.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class SpringSecurityApplication {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityApplication.class, args);
	}

	@PostConstruct
	public void init() {
		log.info("SpringSecurityApplication.init 호출!");
		memberRepository.save(Member.builder()
				.username("dompoo")
				.password(passwordEncoder.encode("1234"))
				.build());
	}

}
