<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.Picture" %>
<%@ page import="photoshare.PictureDao" %>
<%@ page import="photoshare.AlbumsDao" %>

<html>
<head><title>Custon Photos</title></head>

<body>
<p align="right"><a href="/photoshare/index.jsp">Home</a></p>
<h1>Sean Calarco's Photo Sharing Site</h1>

<h2>Custom Search Pictures</h2>

<%
    PictureDao pictureDao = new PictureDao();
%>

<table>
    <tr>
        <%
            List<Integer> tagsPictureIds = pictureDao.searchTags(request.getParameter("taglist"));
		int i=1;
            for (Integer tagsPictureId : tagsPictureIds) {
        %>
        <td><a href="/photoshare/picture.jsp?picture_id=<%= tagsPictureId %>">
            <img src="/photoshare/img?t=1&picture_id=<%= tagsPictureId %>"/>
        </a>
        </td>
        <%
		if(i%10==0){
		%>
		<br></tr><tr>
	<%
		}
		i++;
            }
        %>
    </tr>
</table>

</body>
</html>
