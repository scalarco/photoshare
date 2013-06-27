<%--
  Author: Giorgos Zervas <cs460tf@cs.bu.edu>
  modified by Sean Calarco
--%>
<%@ page import="photoshare.FriendsDao" %>
<%@ page import="photoshare.AlbumsDao" %>
<%@ page import="photoshare.Picture" %>
<%@ page import="photoshare.PictureDao" %>
<%@ page import="org.apache.commons.fileupload.FileUploadException" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head><title>Photo Sharing</title></head>

<body>
<h1>Sean Calarco's Photo Sharing Site</h1>

Hello <b><code><%= request.getUserPrincipal().getName()  %></code></b>, click here to
<a href="/photoshare/logout.jsp">log out</a>

<%
AlbumsDao alb =new AlbumsDao();
PictureDao pics = new PictureDao();
int user=alb.getUSID(request.getUserPrincipal().getName());
int userscore=(pics.getNumUserComments(user))+(pics.getNumUserPics(user));


%>

<h3><a href="/photoshare/albums.jsp">Albums</a></h3>
<h3><a href="/photoshare/friends.jsp">Friends</a></h3>
<h3><a href="/photoshare/photos.jsp">Browse Pictures</a><h3>

<h3>My User Score: <%=userscore %></h3>


<h3>Top Users:</h3>
<%
	FriendsDao usersIds =new FriendsDao();
	List<Integer> allUsers= usersIds.allUserIds();
	int numUsers=allUsers.size();
	Integer[][] usersAndScores=pics.inOrderScoreList(allUsers, numUsers);
	int p=0;
	for (Integer[] i: usersAndScores){
		if(p<10){
		%>
		<%=usersIds.getFullName(i[0]) + " - User Score: " + i[1] %><br>
		<%
		p++;
		}
	}
%>

<h3>Recommended Pictures</h3>
<table>
    <tr>
        <%
            List<Integer> recPictureIds = alb.recommendPicturesIds(request.getUserPrincipal().getName());
		int i=1;
            for (Integer recPictureId : recPictureIds) {
        %>
        <td><a href="/photoshare/picture.jsp?picture_id=<%= recPictureId %>">
            <img src="/photoshare/img?t=1&picture_id=<%= recPictureId %>"/>
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
