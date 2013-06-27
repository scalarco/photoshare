<%@ page import="photoshare.Picture" %>
<%@ page import="photoshare.PictureDao" %>
<%@ page import="org.apache.commons.fileupload.FileUploadException" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head><title>Photo Sharing</title></head>

<body>
<p align="right"><a href="/photoshare/index.jsp">Home</a></p>
<h1>Sean Calarco's Photo Sharing Site</h1>
<h2>Search Photos</h2>
<form action="tagphotos.jsp" method="post">
    Tags (separate by commas): <input type="text" name="taglist"/><br>
    <input type="submit" value="Search Tags"/>
</form>

<h2>All Pictures</h2>
<table>
    <tr>
        <%
		PictureDao pictureDao = new PictureDao();
            List<Integer> pictureIds = pictureDao.allPicturesIds();
		int i=1;
            for (Integer pictureId : pictureIds) {
        %>
        <td><a href="/photoshare/picture.jsp?picture_id=<%= pictureId %>">
            <img src="/photoshare/img?t=1&picture_id=<%= pictureId %>"/>
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
<h3>Popular Tags: 
<%
	PictureDao popTags= new PictureDao();
	List<String> tagList = popTags.getPopularWords();
	int j=1;
	for (String tag : tagList){
		if(j<6){
		%>
		<a href="tag.jsp?word=<%=tag %>"><%= tag %></a>
		<%
		j++;
		}
	}
%>
</h3>
</body>
</html>
