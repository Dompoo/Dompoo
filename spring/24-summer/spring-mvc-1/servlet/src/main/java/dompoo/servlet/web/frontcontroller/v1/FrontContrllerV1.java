package dompoo.servlet.web.frontcontroller.v1;

import dompoo.servlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import dompoo.servlet.web.frontcontroller.v1.controller.MemberListControllerV1;
import dompoo.servlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// *은 그 하위의 모든 요청을 이 서블릿을 호출한다는 뜻이다.
@WebServlet(name = "frontControllerV1", urlPatterns = "/front-controller/v1/*")
public class FrontContrllerV1 extends HttpServlet {

    private final Map<String, ControllerV1> controllerMap = new HashMap<>();

    public FrontContrllerV1() {
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ControllerV1 requestController = controllerMap.get(request.getRequestURI());

        if (requestController == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        requestController.process(request, response);
    }
}
