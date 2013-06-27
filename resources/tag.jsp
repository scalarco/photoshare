<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.Picture" %>
<%@ page import="photoshare.PictureDao" %>
<%@ page import="photoshare.AlbumsDao" %>

<html>
 <%
		AlbumsDao alb= new AlbumsDao();
		String tag=request.getParameter("word");
		boolean mine=false;
		int usid=-1;
            	if(request.getParameter("mytags") != null){
			if(request.getUserPrincipal()!=null){
				usid=alb.getUSID(request.getUserPrincipal().getName());
				mine=true;
			}
		}
		else if(request.getParameter("alltags") != null){
				mine=false;
		}
		
%>
      


<head><title><%= tag %></title></head>

<body>
<p align="right"><a href="/photoshare/index.jsp">Home</a></p>
<h1>Sean Calarco's Photo Sharing Site</h1>

<h2><%= tag %> Pictures</h2>
<FORM NAME="myTags" METHOD="POST">
        <INPUT TYPE="HIDDEN" NAME="mytags">
        <INPUT TYPE="BUTTON" VALUE="My Tags" ONCLICK="myTagsButton()">
    </FORM>

    <SCRIPT LANGUAGE="JavaScript">
        <!--
        function myTagsButton()
        {
            document.myTags.mytags.value = "yes";
            myTags.submit();
        } 
        // --> 
    </SCRIPT>

<FORM NAME="allTags" METHOD="POST">
        <INPUT TYPE="HIDDEN" NAME="alltags">
        <INPUT TYPE="BUTTON" VALUE="All Tags" ONCLICK="allTagsButton()">
    </FORM>

    <SCRIPT LANGUAGE="JavaScript">
        <!--
        function allTagsButton()
        {
            document.allTags.alltags.value = "yes";
            allTags.submit();
        } 
        // --> 
    </SCRIPT>


<%
    PictureDao pictureDao = new PictureDao();
	if(mine==false){
%>

<table>
    <tr>
        <%
            List<Integer> tagPictureIds = pictureDao.tagPicturesIds(tag);
		int j=1;
            for (Integer tagPictureId : tagPictureIds) {
        %>
        <td><a href="/photoshare/picture.jsp?picture_id=<%= tagPictureId %>">
            <img src="/photoshare/img?t=1&picture_id=<%= tagPictureId %>"/>
        </a>
        </td>
        <%
if(j%10==0){
%>
<br></tr><tr>
<%
}
j++;
            }
        %>
    </tr>
</table>
<%

}
else{
%>
<table>
    <tr>
	<%
		List<Integer> myTagPictureIds = pictureDao.myTagPicturesIds(tag, usid);
		int i=1;
            for (Integer myTagPictureId : myTagPictureIds) {
	%>
	<td><a href="/photoshare/picture.jsp?picture_id=<%= myTagPictureId %>">
            <img src="/photoshare/img?t=1&picture_id=<%= myTagPictureId %>"/>
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
<%
}
%>
</body>
</html>
