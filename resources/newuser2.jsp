<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head><title>Create New User</title>


<link href="http://cs-people.bu.edu/scalarco/assets/css/bootstrap.css" rel="stylesheet">
<style type="text/css">
      body {
        padding-top: 40px;
        padding-bottom: 40px;
        background-color: #f5f5f5;
      }
</style>

</head>


<body>

<div class="container">
<form class="form-horizontal" action="adduser.jsp" method="post">
<h2 class="form-signin-heading">Create your account</h2>
  <div class="control-group"> <label class="control-label" for="email">*Email:</label><div class="controls"> <input type="text" class="input-block-level" name="email" id="email" placeholder="email"/></div></div><br>
  <div class="control-group"> <label class="control-label" for="pword1">*Password:</label><div class="controls"> <input type="text" class="input-block-level" name="password1" id="pword1" placeholder="password"/></div></div><br>
<div class="control-group"> <label class="control-label" for="pword2">*Re-enter password:</label><div class="controls"> <input type="text" class="input-block-level" name="password2" id="pword2" placeholder="password"/></div></div><br>
<div class="control-group"> <label class="control-label" for="fname">*First Name:</label><div class="controls"> <input type="text" class="input-block-level" name="firstname" id="fname" placeholder="name"/></div></div><br>
<div class="control-group"> <label class="control-label" for="lname">*Last Name:</label><div class="controls"> <input type="text" class="input-block-level" name="lastname" id="lname" placeholder="name"/></div></div><br>
<div class="control-group"> <label class="control-label" for="date">*Date of Birth (YYYY-MM-DD):</label><div class="controls"> <input type="text" class="input-block-level" name="dob" id="date" placeholder="1900-01-20"/></div></div><br>
<div class="control-group"> <label class="control-label" for="gen">Gender:</label><div class="controls"> <input type="text" class="input-block-level" name="gender" id="gen" placeholder="male"/></div></div><br>
<div class="control-group"> <label class="control-label" for="edu">Education:</label><div class="controls"> <input type="text" class="input-block-level" name="education" id="edu" placeholder="Boston University"/></div></div><br>
<div class="control-group"> <label class="control-label" for="bcity">Birth City:</label><div class="controls"> <input type="text" class="input-block-level" name="birthcity" id="bcity" placeholder=""/></div></div><br>
<div class="control-group"> <label class="control-label" for="bstate">Birth State:</label><div class="controls"> <input type="text" class="input-block-level" name="birthstate" id="bstate" placeholder=""/></div></div><br>
<div class="control-group"> <label class="control-label" for="bcountry">Birth Country:</label><div class="controls"> <input type="text" class="input-block-level" name="birthcountry" id="bcountry" placeholder=""/></div></div><br>
<div class="control-group"> <label class="control-label" for="ccity">Current City:</label><div class="controls"> <input type="text" class="input-block-level" name="currcity" id="ccity" placeholder=""/></div></div><br>
<div class="control-group"> <label class="control-label" for="cstate">Current State:</label><div class="controls"> <input type="text" class="input-block-level" name="currstate" id="cstate" placeholder=""/></div></div><br>
<div class="control-group"> <label class="control-label" for="ccountry">Current Country:</label><div class="controls"> <input type="text" class="input-block-level" name="currcountry" id="ccountry" placeholder=""/></div></div><br>

  <button class="btn btn-large btn-primary" type="submit">Create</button>
</form>
</div>
</body>
</html>
