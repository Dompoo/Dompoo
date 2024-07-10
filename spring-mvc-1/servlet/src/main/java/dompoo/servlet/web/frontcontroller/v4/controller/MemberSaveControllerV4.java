package dompoo.servlet.web.frontcontroller.v4.controller;

import dompoo.servlet.domain.Member;
import dompoo.servlet.domain.MemberRepository;
import dompoo.servlet.web.frontcontroller.v4.ControllerV4;

import java.util.Map;

public class MemberSaveControllerV4 implements ControllerV4 {

    private final MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        String username = paramMap.get("username");
        String age = paramMap.get("age");
        Member member = new Member(username, Integer.parseInt(age));
        memberRepository.save(member);

        model.put("member", member);
        return "save-result";
    }
}
