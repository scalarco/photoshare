<%@ page import="photoshare.AlbumsDao" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.Picture" %>
<%@ page import="photoshare.PictureDao" %>
<%@ page import="org.apache.commons.fileupload.FileUploadException" %>
<jsp:useBean id="imageUploadBean"
             class="photoshare.ImageUploadBean">
    <jsp:setProperty name="imageUploadBean" property="*"/>
</jsp:useBean>
<html>
 <%
		AlbumsDao albumsDao= new AlbumsDao();
		int albid=Integer.parseInt(request.getParameter("albumid"));
		String aname=albumsDao.getAlbumName(albid);
	if(!(request.getParameter("delete")==null)&&request.getParameter("delete").equals("yes")){
		if(!(request.getParameter("delpicid")==null)){
		int dPicId=Integer.parseInt(request.getParameter("delpicid"));
		if(albumsDao.getUSID(request.getUserPrincipal().getName())==albumsDao.getOwnerId(albid)){
			albumsDao.deletePicture(dPicId);
		}
	}
}
            	
%>
      


<head><title><%= aname %></title></head>

<body>
<p align="right"><a href="/photoshare/index.jsp">Home</a> <a href="/photoshare/albums.jsp">Albums</a> </p>
<h1>Sean Calarco's Photo Sharing Site</h1>

<h2><%= aname %></h2>
<%
if(request.getUserPrincipal()==null){
}
else if(albumsDao.getUSID(request.getUserPrincipal().getName())==albumsDao.getOwnerId(albid)){

%>
<h3>Add a new picture</h3>

<form action="album.jsp?albumid=<%=albid%>" enctype="multipart/form-data" method="post">
    Filename: <input type="file" name="filename"/><br>
    Caption: <input type="text" name="caption"/><br>
    Tags (Separate with comma): <input type="text" name="tags"/><br>
    <input type="submit" value="Upload"/><br>
</form>

<%
    PictureDao pictureDao = new PictureDao();
    try {
        Picture picture = imageUploadBean.upload(request);
        if (picture != null) {
            pictureDao.save(picture,albid);
		
        }
    } catch (FileUploadException e) {
        e.printStackTrace();
    }
%>

<form action="albums.jsp?delalbumid=<%=albid%>&delete=yes" method="post">
<input type="hidden" name=deletePressed"/>
<input type="submit" value="Delete Album"/><br>
<%
}
else{
}
%>
<h3>Album Pictures</h3>
<table>
    <tr>
        <%
		PictureDao pics = new PictureDao();
            List<Integer> aPictureIds = pics.albumPicturesIds(albid);
            for (Integer aPictureId : aPictureIds) {
        %>
        <td><a href="/photoshare/picture.jsp?picture_id=<%= aPictureId %>">
            <img src="/photoshare/img?t=1&picture_id=<%= aPictureId %>"/>
        </a>
        </td>
        <%
            }
        %>
    </tr>
</table>


</body>
</html>
