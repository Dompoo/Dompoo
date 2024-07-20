<%@ page import="dompoo.servlet.domain.MemberRepository" %>
<%@ page import="dompoo.servlet.domain.Member" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    //request, response는 그냥 사용 가능하다.
    //결국 JSP도 서블릿으로 변환되기 때문이다!
    MemberRepository memberRepository = MemberRepository.getInstance();
    List<Member> members = memberRepository.findAll();
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<a href="/index.html">메인</a>
<table>
    <thead>
    <th>id</th>
    <th>username</th>
    <th>age</th>
    </thead>
    <tbody>
    <%
        for (Member member : members) {
            out.write("    <tr>\n");
            out.write("        <td>" + member.getId() + "</td>\n");
            out.write("        <td>" + member.getUsername() + "</td>\n");
            out.write("        <td>" + member.getAge() + "</td>\n");
            out.write("    </tr>\n");
        }
    %>
    </tbody>
</table>
</body>
</html>
