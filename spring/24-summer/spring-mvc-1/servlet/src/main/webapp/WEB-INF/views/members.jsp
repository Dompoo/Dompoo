<%@ page import="dompoo.servlet.domain.Member" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Object membersObj = request.getAttribute("members");
    List<Member> members;

    if (membersObj instanceof List) {
        members = (List<Member>) membersObj;
    } else {
        members = new ArrayList<>();
    }
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
