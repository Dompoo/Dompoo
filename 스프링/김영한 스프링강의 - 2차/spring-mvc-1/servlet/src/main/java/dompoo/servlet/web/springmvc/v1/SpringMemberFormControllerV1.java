package dompoo.servlet.web.springmvc.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
// Controller의 기능
// * 컴포넌트 스캔의 대상
// * 스프링MVC에서 애노테이션 기반 컨트롤러로 인식

// 클래스레벨에 @Controller 또는 @RequestMapping이 있어야
// RequestMappingHandlerMapping의 대상이 된다.
public class SpringMemberFormControllerV1 {

    @RequestMapping("/springmvc/v1/members/new-form")
    public ModelAndView process() {
        return new ModelAndView("new-form");
    }
}
