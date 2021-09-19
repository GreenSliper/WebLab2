<%@ page import="com.example.weblab2.localization.Localizator" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Localizator localizator = Localizator.getInstance(null);
    Object dataInc = request.getAttribute("incorrectData");
    boolean dataIncorrect = dataInc != null && (boolean) dataInc;
    String username = (String) request.getAttribute("username");
%>
<html>
<head>
    <title><%=localizator.getResource("login")%></title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/login" method="post">
        <h1><%=localizator.getResource("login")%></h1>
        <%if(dataIncorrect) {%>
        <h3 style="color: red"><%=localizator.getResource("incorrectLogin")%></h3>
        <%}%>
        <label><%=localizator.getResource("username")%></label>
        <input type="text" name="username" value="<%=username==null?"":username%>"/>
        <br />
        <label><%=localizator.getResource("password")%></label>
        <input type="text" name="password"/>
        <br />
        <input type="submit" name="submit" value="<%=localizator.getResource("submitLogin")%>"/>
    </form>
    <br />
    <p><%=localizator.getResource("switchLang")%>
        <a href='?lang=ru'>Русский</a>
        <a href='?lang=en'>English</a>
    </p>
</body>
</html>
