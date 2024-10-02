<%
    if (session == null || session.getAttribute("user") == null) {
        response.sendRedirect("login");
        return;
    }
%>
<html>
<body>
<h2>Hello World!</h2>
</body>
</html>