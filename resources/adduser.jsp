<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.NewUserDao" %>
<%@ page import="photoshare.NewLocationDao" %>

<html>
<head><title>Adding New User</title></head>

<body>

<% 
   String err = null;
   String email  = request.getParameter("email");
   String password1 = request.getParameter("password1");
   String password2 = request.getParameter("password2");
   String firstname = request.getParameter("firstname");
   String lastname  = request.getParameter("lastname");
   String dob = request.getParameter("dob");
   String gender =request.getParameter("gender");
   String education =request.getParameter("education");
   String bcity = request.getParameter("birthcity");
   String bstate = request.getParameter("birthstate");
   String bcountry = request.getParameter("birthcountry");
   String ccity = request.getParameter("currcity");
   String cstate = request.getParameter("currstate");
   String ccountry = request.getParameter("currcountry");



   if (!email.equals("")) {
     if (!password1.equals(password2)) {
       err = "Both password strings must match";

     }
     else if (password1.length() < 4) {
       err = "Your password must be at least four characters long";
     }
     else {
      if(firstname.equals("")||lastname.equals("")) {
	err="Must provide a full name";
		}
	else{
		if(dob.equals("")){
			err="Must provide a date of birth";
			}
		else{
       NewUserDao newUserDao = new NewUserDao();
	NewLocationDao newLocationDao = new NewLocationDao();
       boolean success = newUserDao.create(email, password1, firstname, lastname, dob, gender, education);
	boolean success2 =newLocationDao.create(email, bcity, bstate, bcountry, ccity, cstate, ccountry);

       if (!success) {
         err = "Couldn't create user (that email may already be in use)";
       }
	if(!success2){
	err="Couldn't create user location";
	}
	}
     }
   } 
}else {
	 err = "You have to provide an email";

   }
   
%>

<% if (err != null) { %>
<font color=red><b>Error: <%= err %></b></font>
<p> <a href="newuser2.jsp">Go Back</a>
<% }
   else { %>

<h2>Success!</h2>

<p>A new user has been created with email <%= email %>.
You can now return to the <a href="index.jsp">login page</a>.

<% } %>

</body>
</html>
