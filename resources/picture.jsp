<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.Picture" %>
<%@ page import="photoshare.PictureDao" %>
<%@ page import="photoshare.AlbumsDao" %>
<%@ page import="java.util.List" %>
<%@ page import= "java.util.ArrayList" %>
<html>


<head><title>Sean Calarco's Photo Sharing Site</title></head>
<%  
AlbumsDao idGetter= new AlbumsDao();
int pictureId = Integer.valueOf(request.getParameter("picture_id"));
String caption=PictureDao.grabCap(pictureId);
int picAlbid=idGetter.getAlbId(pictureId);
%>
<body>
<p align="right"><a href="/photoshare/index.jsp">Home</a> <a href="/photoshare/album.jsp?albumid=<%=picAlbid %>">Album</a> </p>

<% 
        if(request.getParameter("like") != null) {
               AlbumsDao alb= new AlbumsDao();
		if(!(request.getUserPrincipal()==null)){
		int usid=alb.getUSID(request.getUserPrincipal().getName());
		PictureDao pic= new PictureDao();
		pic.addLike(usid, pictureId);
		}
        }
    %>
<%
if(!(request.getParameter("cname")==null))
{
	AlbumsDao alb= new AlbumsDao();
	int usid=-1;
	if(!(request.getUserPrincipal()==null)){
		usid=alb.getUSID(request.getUserPrincipal().getName());
	}
	PictureDao pic= new PictureDao();
	pic.leaveComment(usid, pictureId, request.getParameter("cname"));
	
}
%>

<h2><center><%= caption %></center></h2>


<center>
<br>
<img src="/photoshare/img?picture_id=<%= pictureId %>"/>
<br>

<h3>Tags: 
<%
	PictureDao picTags= new PictureDao();
	List<String> tagList = picTags.listTags(pictureId);
	for (String tag : tagList){
	%>
	<a href="tag.jsp?word=<%=tag %>"><%= tag %></a>
	<%
	}
%>
</h3>
<FORM NAME="likeform" METHOD="POST">
        <INPUT TYPE="HIDDEN" NAME="like">
        <INPUT TYPE="BUTTON" VALUE="Like" ONCLICK="button1()">
    </FORM>

    <SCRIPT LANGUAGE="JavaScript">
        <!--
        function button1()
        {
            document.likeform.like.value = "yes";
            likeform.submit();
        } 
        // --> 
    </SCRIPT>

<h4>Likes:
	<%
		PictureDao likesPic= new PictureDao();
            List<Integer> likeList = likesPic.listLikes(pictureId);
		List<String> likesNames=new ArrayList<String>();
		for(int like :likeList){
		likesNames.add(likesPic.getFullName(like));
		}
		int j=0;
		for(String likename: likesNames){
	%>
		<%=likename %>, 
		<%
		j++;
		}
		%>
	Total: <%= j %></h4>
<%
AlbumsDao albDelete= new AlbumsDao();
if(!(request.getUserPrincipal()==null)){
	int delaid=albDelete.getAlbId(pictureId);
	int deluid=albDelete.getUSID(request.getUserPrincipal().getName());
	if(deluid==albDelete.getOwnerId(delaid)){
	%>
	<form action="album.jsp?albumid=<%=delaid%>&delpicid=<%=pictureId%>&delete=yes" method="post">
	<input type="submit" value="Delete Picture"/><br>
	<%
	}
}
%>
</center>
<h2>Comments</h2>
<table>
    
        <%
		PictureDao pictureDao= new PictureDao();
            List<String> commentList = pictureDao.listComments(pictureId);
		int i=1;
		for (String commentN : commentList) {
		
        %>
        <tr><h4><%= i+": " %> <%= commentN %></h4>
        </tr>
        <%
		i++;
            }
        %>
    
</table>
<%
	AlbumsDao albNoComm= new AlbumsDao();
	if(!(request.getUserPrincipal()==null)){
		int commaid=albNoComm.getAlbId(pictureId);
		int commuid=albNoComm.getUSID(request.getUserPrincipal().getName());
		if(commuid==albNoComm.getOwnerId(commaid)){
		}
		else{
	%>

<h3>Leave Comment</h3>
<form action="picture.jsp?picture_id=<%= pictureId %>" method="post">
    Comment: <input type="text" name="cname"/><br>
    <input type="submit" value="Comment"/>
</form>
	<%
		}
	}
	else{
	%>
	<h3>Leave Comment</h3>
	<form action="picture.jsp?picture_id=<%= pictureId %>" method="post">
    	Comment: <input type="text" name="cname"/><br>
    	<input type="submit" value="Comment"/>
	</form>
	<%
	}
%>

</body>
</html>

