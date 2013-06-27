

<%@ page import="photoshare.AlbumsDao" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<%
AlbumsDao alb= new AlbumsDao();
if(!(request.getParameter("aname")==null))
{	
	alb.create(alb.getUSID(request.getUserPrincipal().getName()),request.getParameter("aname"));
}
if(!(request.getParameter("delete")==null)&&request.getParameter("delete").equals("yes")){
	if(!(request.getParameter("delalbumid")==null)){
	int abid=Integer.parseInt(request.getParameter("delalbumid"));
	if(alb.getUSID(request.getUserPrincipal().getName())==alb.getOwnerId(abid)){
	alb.deleteAlbum(abid);
	}
	}
}
%>
<head><title>My Albums</title></head>

<body>
<p align="right"><a href="/photoshare/index.jsp">Home</a></p>
<h1>Sean Calarco's Photo Sharing Site</h1>



<h2>Existing Albums</h2>


<table>
    
        <%
		AlbumsDao albumsDao= new AlbumsDao();
            List<Integer> albumIds = albumsDao.allAlbumsIds(request.getUserPrincipal().getName());
		for (Integer albumId : albumIds) {
        %>
        <tr><a href="/photoshare/album.jsp?albumid=<%= albumId %>">
            <%= albumsDao.getAlbumName(albumId) %>        
	</a><br>
        </tr>
        <%
            }
        %>
    
</table>

<h2>Create New Album</h2>
<form action="albums.jsp" method="post">
    Album Name: <input type="text" name="aname"/><br>
    <input type="submit" value="Create Album"/>
</form>

</body>
</html>
