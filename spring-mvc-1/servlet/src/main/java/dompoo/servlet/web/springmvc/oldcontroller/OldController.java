package dompoo.servlet.web.springmvc.oldcontroller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

@Component("/springmvc/old-controller") //스프링 빈의 이름을 urlPattern으로 사용한다.
public class OldController implements Controller {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("OldController.handleRequest");
        return new ModelAndView("new-form");
    }
}

/*
이 핸들러가 잘 작동하기 위해서는
* 핸들러 매핑에 잘 등록되어야 하고(스프링 빈의 이름으로 매핑되어야 한다.)
* 핸들러 어댑터가 필요하다.
-> 스프링에는 아래와 같은 매핑과 어댑터가 이미 등록되어 있다.

### 핸들러 매핑 (순위대로 찾는다.)
    1. RequestMappingHandlerMapping : 애노테이션 기반의 컨트롤러인 @RequestMapping을 처리하는 핸들러 매핑
    2. BeanNameUrlHandlerMapping : 스프링 빈의 이름으로 핸들러를 찾아주는 핸들러 매핑
### 핸들러 어댑터 (순위대로 찾는다.)
    1. RequestMappingHandlerAdapter : 애노테이션 기반의 컨트롤러인 @RequestMapping을 처리하는 어댑터
    2. HttpRequestHandlerAdapter : HttpRequestHandler 처리
    3. SimpleControllerHandlerAdapter : Controller 인터페이스를 처리

스프링은 이것들을 순위대로 찾고,
찾은 매핑과 어댑터를 사용하여 핸들러를 실행한다.

1. BeanNameUrlHandlerMapping에서 나의 OldController를 찾는다.
2. 내 OldController를 실행할 수 있는 핸들러어댑터를 찾는다. (SimpleControllerHandlerAdapter)
3. SimpleControllerHandlerAdapter를 사용하여 OldController를 실행한다.
 */
