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
if(!(request.getParameter("friend_email")==null))
{
	friendD.addFriend(request.getUserPrincipal().getName(),request.getParameter("friend_email"));
}

%>


<head><title>My Friends</title></head>

<body>
<p align="right"><a href="/photoshare/index.jsp">Home</a></p>
<h1>Sean Calarco's Photo Sharing Site</h1>



<h2>Friends List</h2>

<table>
    
        <%
		FriendsDao friendsDao= new FriendsDao();
            List<Integer> friendIds = friendsDao.allFriendIds(request.getUserPrincipal().getName());
		List<String> friendNames=new ArrayList<String>();
		for (Integer friendId : friendIds) {
			friendNames.add(friendsDao.getFullName(friendId));
		}
	for(String friendname : friendNames){
		%>
		<tr><%=friendname %><br></tr>
        	
        <%
            }
        %>
    
</table>
<h2>Add Friend by Email</h2>
<form action="friends.jsp?user_id=<%= currUser %>" method="post">
    Friend Email: <input type="text" name="femail"/><br>
    <input type="submit" value="Add Friend"/>
</form>

<a href="/photoshare/searchfriends.jsp?<%=currUser %>">Search Users</a>


</body>
</html>
