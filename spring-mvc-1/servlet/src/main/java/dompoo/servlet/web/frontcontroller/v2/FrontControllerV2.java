package dompoo.servlet.web.frontcontroller.v2;

import dompoo.servlet.web.frontcontroller.MyView;
import dompoo.servlet.web.frontcontroller.v2.controller.MemberFormControllerV2;
import dompoo.servlet.web.frontcontroller.v2.controller.MemberListControllerV2;
import dompoo.servlet.web.frontcontroller.v2.controller.MemberSaveControllerV2;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// *은 그 하위의 모든 요청을 이 서블릿을 호출한다는 뜻이다.
@WebServlet(name = "frontControllerV2", urlPatterns = "/front-controller/v2/*")
public class FrontControllerV2 extends HttpServlet {

    private final Map<String, ControllerV2> controllerMap = new HashMap<>();

    public FrontControllerV2() {
        controllerMap.put("/front-controller/v2/members/new-form", new MemberFormControllerV2());
        controllerMap.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
        controllerMap.put("/front-controller/v2/members", new MemberListControllerV2());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ControllerV2 requestController = controllerMap.get(request.getRequestURI());

        if (requestController == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        MyView view = requestController.process(request, response);

        view.render(request, response);
    }
}
