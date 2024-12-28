package Dompoo.SpringCore.web;

import Dompoo.SpringCore.common.MyLogger;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;

    //프록시 모드로 동작 -> CGLIB 사용하여 미리 프록시만 넣어놓는다.
    //프록시 객체는 요청시에 진짜 빈(MyLogger)를 요청한다.
    //스프링 컨테이너는 그제서야 MyLogger를 생성하여 넘겨준다.
    private final MyLogger myLogger;

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();

        myLogger.setRequestURL(requestURL);
        myLogger.log("controller");

        logDemoService.logic("testId");

        return "OK";
    }
}
