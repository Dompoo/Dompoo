package dompoo.servlet.basic.request;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "requestParamServlet", urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //http://localhost:8080/request-param?username=hello&age=20

        System.out.println("[전체 파라미터 조회]");
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> System.out.println(paramName + " = " + request.getParameter(paramName)));
        System.out.println();

        System.out.println("[단일 파라미터 조회]");
        String username = request.getParameter("username"); //기본적으로 중복일 때는 맨 첫 파라미터를 가져온다.
        System.out.println("username = " + username);
        String age = request.getParameter("age");
        System.out.println("age = " + age);
        System.out.println();

        System.out.println("[이름이 같은 여러 파리미터 조회]");
        String[] usernames = request.getParameterValues("username");
        for (String s : usernames) {
            System.out.println("username = " + s);
        }
        String[] ages = request.getParameterValues("age");
        for (String s : ages) {
            System.out.println("age = " + s);
        }
        System.out.println();

        response.getWriter().write("ok");
    }
}
