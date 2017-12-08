<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Registration</title>

    <!-- Bootstrap -->
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="style.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.js"></script>

    <![endif]-->
</head>
<body>
<div class="container">
    <div class="row justify-content-md-center">
        <div class="col-2 col-md-2 auhtorozationText">
            <p>Full name: </p>
            <p>Billing address: </p>
        </div>
        <div class="col-6 col-md-3">
            <p><input maxlength="25" size="35" id="fullName"></p>
            <p><input maxlength="45" size="35" id="address"></p>
        </div>
    </div>
    <div class="row justify-content-md-center">
        <div class="col-2 col-md-2 auhtorozationText">
            <p>Login: </p>
            <p>Password: </p>
        </div>
        <div class="col-6 col-md-3">
            <p><input maxlength="20" size="35" id="login"></p>
            <p><input maxlength="25" size="35" id="password" type="password"></p>
        </div>
    </div>
    <p class="passwordRecommendation">6 character minimum</p>
    <div class="row justify-content-md-center">
        <div class="col-2 col-md-2 auhtorozationText">
            <p>Re-enter password: </p>
            <button type="submit" class="btn btn-success registerBtn" id="register" >Register</button>
            <button type="submit" class="btn btn-primary registerBtn" id="back"  onclick="window.location.href='/authorization';">Back</button>
        </div>
        <div class="col-6 col-md-3">
            <p><input maxlength="25" size="35" id="rePassword" type="password"></p>
            <p class = "errorText"></p>
        </div>
    </div>
</div>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/bootstrap.js"></script>
<script src="js/Registration.js"></script>
</body>
</html>