package dompoo.spring_security.member;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/member")
    public Member saveMember(@RequestBody Member member) {
        return memberService.save(member);
    }

    @GetMapping("/member/{id}")
    public Member getMember(@PathVariable Long id) {
        return memberService.findById(id);
    }

    @GetMapping("/members")
    public List<Member> getMemberList() {
        return memberService.findAll();
    }
}
