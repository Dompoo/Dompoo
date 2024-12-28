package dompoo.servlet.web.frontcontroller.v2.controller;

import dompoo.servlet.domain.Member;
import dompoo.servlet.domain.MemberRepository;
import dompoo.servlet.web.frontcontroller.MyView;
import dompoo.servlet.web.frontcontroller.v2.ControllerV2;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class MemberSaveControllerV2 implements ControllerV2 {

    private final MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String age = request.getParameter("age");
        Member member = new Member(username, Integer.parseInt(age));
        memberRepository.save(member);

        //Model에 데이터 보관
        request.setAttribute("member", member);

        return new MyView("/WEB-INF/views/save-result.jsp");
    }
}
