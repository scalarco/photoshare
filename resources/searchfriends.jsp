<%@ page import="photoshare.FriendsDao" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
FriendsDao friendD= new FriendsDao();
int currUser=friendD.getUSID(request.getUserPrincipal().getName());
if(!(request.getParameter("femail")==null))
{
	friendD.addFriend(request.getUserPrincipal().getName(),request.getParameter("femail"));
}

%>


<head><title>My Friends</title></head>

<body>
<p align="right"><a href="/photoshare/index.jsp">Home</a></p>
<h1>Sean Calarco's Photo Sharing Site</h1>



<h2>Users</h2>
<h3>Click a name to add user as friend</h3>

<table>
    
        <%
		FriendsDao friendsDao= new FriendsDao();
            List<Integer> friendIds = friendsDao.allUserIds();
		for (Integer friendId : friendIds) {
		String user=friendsDao.getFullName(friendId);
		String userEmail=friendsDao.getUserEmail(friendId);
		%>
		<tr><a href="/photoshare/friends.jsp?friend_email=<%= userEmail%>"><%= user%>, <%=userEmail %></a><br></tr>
        	
        <%
            }
        %>
    
</table>

</body>
</html>
