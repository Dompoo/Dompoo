package dompoo.servlet.web.frontcontroller.v5;

import dompoo.servlet.web.frontcontroller.ModelView;
import dompoo.servlet.web.frontcontroller.MyView;
import dompoo.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import dompoo.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import dompoo.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import dompoo.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import dompoo.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import dompoo.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import dompoo.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import dompoo.servlet.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontControllerServletV5() {
        initHandlerMappingMap();
        initHandlerAdapters();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object handler = getHandler(request);

        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        MyHandlerAdapter adapter = getHandlerAdapter(handler);

        ModelView modelView = adapter.handle(request, response, handler);

        MyView myView = viewResolver(modelView);

        myView.render(modelView.getModel(), request, response);

        /*
        1. 프론트컨트롤러에서 URL 매핑정보를 가지고 핸들러를 찾는다.
        2. 핸들러를 가지고 핸들러어댑터를 찾는다.
        3. 핸들러어댑터의 handle을 통해 핸들러를 실행한다.
            4. 핸들러는 비즈니스 로직을 실행하고 ModelView을 반환한다.
            (ModelView에는 논리이름과 모델이 들어있다.)
        5. 프론트컨트롤러에서 해당 논리이름을 가지고 뷰리졸버를 호출한다.
            6. 뷰 리졸버는 뷰를 반환한다.
        7. 뷰를 render하여 model을 request에 담고 포워딩한다.
         */
    }

    private static MyView viewResolver(ModelView modelView) {
        return new MyView("/WEB-INF/views/" + modelView.getViewName() + ".jsp");
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        for (MyHandlerAdapter adapter : handlerAdapters) {
            if (adapter.support(handler)) {
                return adapter;
            }
        }
        throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다. handler=" + handler);
    }

    private Object getHandler(HttpServletRequest request) {
        return handlerMappingMap.get(request.getRequestURI());
    }

    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }

    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());

        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
    }
}
