<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Edit or add</title>

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
            <p>Title of item: </p>
            <p>Description: </p>
        </div>
        <div class="col-6 col-md-3">
            <p><input maxlength="25" size="35" id="title"></p>
            <p><textarea id="description"></textarea></p>
        </div>
    </div>
    <div class="row justify-content-md-center">
        <div class="col-2 col-md-2 addText">
            <p>Start price: </p>
            <p>Time left: </p>
            <p>Step of bid: </p>
            <p>Buy it now: </p>
        </div>
        <div class="col-6 col-md-3">
            <p><input maxlength="25" size="35" id="startPrice"></p>
            <p><input maxlength="25" size="35" id="timeLeft"></p>
            <p><input maxlength="25" size="35" id="step"></p>
            <input type="checkbox" id="buyItNowCheckBox">
        </div>
    </div>
    <div class="row justify-content-md-center addBtns">
        <div class="col-2 col-md-2">
            <button id="publishBtn" >Publish/Add</button>
            <button>Reset</button>
        </div>
        <div class="col-2 col-md-2">
        <button onClick="location.href='/general'">Back</button>
        </div>

        <p id="response"></p>
    </div>
</div>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/bootstrap.js"></script>
<script src="js/EditAdding.js"></script>
</body>
</html>