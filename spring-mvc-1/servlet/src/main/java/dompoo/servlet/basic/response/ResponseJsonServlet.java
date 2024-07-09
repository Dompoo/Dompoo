package dompoo.servlet.basic.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import dompoo.servlet.basic.HelloData;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "responseJsonServlet", urlPatterns = "/response-json")
public class ResponseJsonServlet extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        // {"username":"lee", "age":20}
        HelloData helloData = new HelloData();
        helloData.setUsername("lee");
        helloData.setAge(20);

        String json = objectMapper.writeValueAsString(helloData);
        response.getWriter().write(json);
    }
}
