<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Authorization</title>

    <!-- Bootstrap -->
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.js"></script>
    <![endif]-->
</head>
<body>
<div class ="container">
    <div class="row col-md-offset-2">
    <form:form method="POST" commandName="user" action="authorization" class="form-group">
         <div class="form-group">
                  <td>Login:</td>
                  <td><form:input path="login"  class="form-control" /></td>
         </div>
         <div class="form-group">
                  <td>Password:</td>
                  <td><form:input path="password" class="form-control" /></td>
                  <p class = "errorText">${error}</p>
         </div>
         <div class="col-2 col-md-1 ">
            <button type="submit" class="btn btn-success" value="Save Changes" >Sign in</button>
         </div>
    </form:form>

        <div class="col-2 col-md-2 ">
             <form:form method="POST" commandName="user" action="/authorization/guest" class="form-group">
                <button type="submit" class="btn btn btn-primary">Enter as guest</button>
             </form:form>
        </div>
        <div class="col-2 col-md-1 ">
            <form:form method="GET" commandName="user" action="/registration" class="form-group">
                <button type="submit" class="btn btn btn-primary">Register</button>
            </form:form>
        </div>
    <div>
</div>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/bootstrap.js"></script>
<script src="js/Authorization.js"></script>
</body>
</html>