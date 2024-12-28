<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

    <form action="save" method="post"> <!--상대경로 사용, 현재 계층 경로 + /save-->
        username: <input type="text" name="username" />
        age:      <input type="text" name="age" />
        <button type="submit">전송</button>
    </form>
</body>
</html>
<!--WEB-INF에 넣으면 외부에서 호출해도 호출되지 않고, 서블릿을 통해서만 접근할 수 있다.-->


<!--
MVC 패턴 덕분에 뷰와 컨트롤러를 명확하게 구분하고, 나름 SOP를 지키는 것을 볼 수 있다.
하지만, 몇가지 문제가 발생했다.
* 포워딩 코드 중복
* viewPath 중복
* request response를 항상 사용하는 것은 아니다.
* 컨트롤러에서 반복되는 코드의 공통 처리가 어렵다.
-> 프론트 컨트롤러 사용
-->