package dompoo.servlet.web.frontcontroller.v3.controller;

import dompoo.servlet.domain.Member;
import dompoo.servlet.domain.MemberRepository;
import dompoo.servlet.web.frontcontroller.ModelView;
import dompoo.servlet.web.frontcontroller.v3.ControllerV3;

import java.util.Map;

public class MemberSaveControllerV3 implements ControllerV3 {

    private final MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> paramMap) {
        String username = paramMap.get("username");
        String age = paramMap.get("age");
        Member member = new Member(username, Integer.parseInt(age));
        memberRepository.save(member);

        ModelView modelView = new ModelView("save-result");
        modelView.getModel().put("member", member);
        return modelView;
    }
}
