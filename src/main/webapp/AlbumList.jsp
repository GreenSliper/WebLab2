<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<%@ page import="com.example.weblab2.localization.*"%>
<%@ page import="com.example.weblab2.IAlbumLine" %>
<%@ page import="java.util.List" %>
<%
    Localizator localizator = Localizator.getInstance(null);
    List<IAlbumLine> lines = (List<IAlbumLine>) request.getAttribute("lines");
    List<Boolean> canVoteList = (List<Boolean>) request.getAttribute("canVoteList");
    String loggedInUsername = (String) request.getAttribute("loggedInUsername");
%>
<style>
    td{
      padding: 3px;
    }
</style>
<html>
<head>
    <title><%= localizator.getResource("title")%></title>
</head>
<body>
<h1><%=localizator.getResource("header")%></h1>
<% if(loggedInUsername==null) { %>
    <a href='/login'><h4><%=localizator.getResource("login")%></h4></a>
<%} else {%>
<h3><%=localizator.getResource("helloText")%> <%=loggedInUsername%>!</h3>
<a href='/login?logout=true'><h4><%=localizator.getResource("logout")%></h4></a>
<%}%>
<table border='0' style="border-collapse:collapse; padding: 3px">
    <tr><td><h4> <%=localizator.getResource("performer")%>
        </h4></td><td><h4> <%=localizator.getResource("album")%>
        </h4></td><td><h4> <%=localizator.getResource("rating")%>
        </h4></td></tr>
    <%for(int i =0; i < lines.size(); i++) { %>
    <tr>
        <td><%= lines.get(i).getPerformer()%></td>
        <td><%= lines.get(i).getAlbum()%></td>
        <td><%= lines.get(i).getRating()%></td>
        <%if(canVoteList.get(i)) {%>
            <td>
                <form action="" method="post">
                    <input type="submit" name="vote" value="<%=localizator.getResource("vote")%>" />
                    <input type="range" id="rating" name="rating" min="0" max="10" value="0"
                           oninput="this.nextElementSibling.value = this.value">
                    <output>0</output>
                    <input type="text" name="albumid" style="display: none" value="<%=i%>">
                </form>
            </td>
        <% } %>
    </tr>
    <% } %>
</table>
<br>
<br>
<p><%=localizator.getResource("switchLang")%>
    <a href='?lang=ru'>Русский</a>
    <a href='?lang=en'>English</a>
</p>
</body>
</html>
