<%@ page import="dompoo.servlet.domain.MemberRepository" %>
<%@ page import="dompoo.servlet.domain.Member" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    //request, response는 그냥 사용 가능하다.
    //결국 JSP도 서블릿으로 변환되기 때문이다!
    MemberRepository memberRepository = MemberRepository.getInstance();
    String username = request.getParameter("username");
    String age = request.getParameter("age");
    Member member = new Member(username, Integer.parseInt(age));
    memberRepository.save(member);
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
성공
<ul>
    <li>id=<%=member.getId()%></li>
    <li>username=<%=member.getUsername()%></li>
    <li>age=<%=member.getAge()%></li>
</ul>
<a href="/index.html">메인</a>
</body>
</html>
