<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="/jsp/members/save.jsp" method="post">
        username: <input type="text" name="username" />
        age:      <input type="text" name="age" />
        <button type="submit">전송</button>
    </form>
</body>
</html>

<!--
JSP로 개발하니 뷰를 생성하는 HTML은 깔끔해졌지만,
JSP에 비즈니스 로직, 리포지토리, HTML등 너무 많은 역할을 하고 있다.
-> MVC 패턴
-->