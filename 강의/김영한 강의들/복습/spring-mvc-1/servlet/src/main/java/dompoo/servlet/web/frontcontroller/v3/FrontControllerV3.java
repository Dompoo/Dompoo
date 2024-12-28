package dompoo.servlet.web.frontcontroller.v3;

import dompoo.servlet.web.frontcontroller.ModelView;
import dompoo.servlet.web.frontcontroller.MyView;
import dompoo.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import dompoo.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import dompoo.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// *은 그 하위의 모든 요청을 이 서블릿을 호출한다는 뜻이다.
@WebServlet(name = "frontControllerV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerV3 extends HttpServlet {

    private final Map<String, ControllerV3> controllerMap = new HashMap<>();

    public FrontControllerV3() {
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ControllerV3 requestController = controllerMap.get(request.getRequestURI());

        if (requestController == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Map<String, String> paramMap = createParamMap(request);
        ModelView modelView = requestController.process(paramMap);
        String viewName = modelView.getViewName();

        //이 부분이 viewResolver, String논리이름 -> View객체로 바꿔준다.
        MyView myView = new MyView("/WEB-INF/views/" + viewName + ".jsp");

        myView.render(modelView.getModel(), request, response);
    }

    private static Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator().forEachRemaining(
                (paramName) -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
