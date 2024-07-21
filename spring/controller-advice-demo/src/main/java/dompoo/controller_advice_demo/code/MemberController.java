package dompoo.controller_advice_demo.code;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;
	
	@GetMapping("/demo")
	public ResponseEntity<String> demo() {
		String value = memberService.doLogic();
		return ResponseEntity.ok().body(value);
	}
}
