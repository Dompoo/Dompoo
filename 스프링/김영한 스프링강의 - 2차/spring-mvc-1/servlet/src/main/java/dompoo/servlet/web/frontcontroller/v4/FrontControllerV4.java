package dompoo.servlet.web.frontcontroller.v4;

import dompoo.servlet.web.frontcontroller.MyView;
import dompoo.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import dompoo.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import dompoo.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// *은 그 하위의 모든 요청을 이 서블릿을 호출한다는 뜻이다.
@WebServlet(name = "frontControllerV4", urlPatterns = "/front-controller/v4/*")
public class FrontControllerV4 extends HttpServlet {

    private final Map<String, ControllerV4> controllerMap = new HashMap<>();

    public FrontControllerV4() {
        controllerMap.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
        controllerMap.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
        controllerMap.put("/front-controller/v4/members", new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ControllerV4 requestController = controllerMap.get(request.getRequestURI());

        if (requestController == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Map<String, String> paramMap = createParamMap(request);
        Map<String, Object> model = new HashMap<>();
        String viewName = requestController.process(paramMap, model);

        MyView myView = new MyView("/WEB-INF/views/" + viewName + ".jsp");

        myView.render(model, request, response);
    }

    private static Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator().forEachRemaining(
                (paramName) -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
