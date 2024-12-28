package dompoo.servlet.web.frontcontroller.v5.adapter;

import dompoo.servlet.web.frontcontroller.ModelView;
import dompoo.servlet.web.frontcontroller.v3.ControllerV3;
import dompoo.servlet.web.frontcontroller.v5.MyHandlerAdapter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

public class ControllerV3HandlerAdapter implements MyHandlerAdapter {

    @Override
    public boolean support(Object handler) {
        return handler instanceof ControllerV3;
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        ControllerV3 controller = (ControllerV3) handler;

        Map<String, String> paramMap = createParamMap(request);
        return controller.process(paramMap);
    }

    private static Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator().forEachRemaining(
                (paramName) -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
