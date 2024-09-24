package dompoo.servlet.web.springmvc.v1;

import dompoo.servlet.domain.Member;
import dompoo.servlet.domain.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SpringMemberSaveControllerV1 {

    private final MemberRepository memberRepository = MemberRepository.getInstance();

    @RequestMapping("/springmvc/v1/members/save")
    public ModelAndView process(HttpServletRequest request, HttpServletResponse response) {
        //원하는 대로 파라미터로 request, response를 받을 수 있다.
        String username = request.getParameter("username");
        String age = request.getParameter("age");
        Member member = new Member(username, Integer.parseInt(age));
        memberRepository.save(member);

        ModelAndView mv = new ModelAndView("save-result");
        mv.addObject("member", member);
        return mv;
    }
}
