<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
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
    <form:form method="POST" commandName="params" action="advanced" class="form-group">
         <div class="row">
             <div class="form-group col-xs-3">
                      <td>UID:</td>
                      <td><form:input path="uId"  class="form-control" value="${cookie.uIdCookie.value}"/></td>
                      <td ><form:errors path="uId" cssClass="errorText"/></td>
             </div>
         </div>
         <div class="row">
             <div class="form-group col-xs-3">
                      <td>Title:</td>
                      <td><form:input path="title"  class="form-control" value="${cookie.titleCookie.value}"/></td>

             </div>
         </div>
         <div class="row">
             <div class="form-group col-xs-3">
                      <td>Description:</td>
                      <td><form:input path="description"  class="form-control" value="${cookie.descriptionCookie.value}"/></td>


             </div>
         </div>
         <div class="row">
             <div class="form-group col-xs-2">
                      <td>minPrice:</td>
                      <td><form:input path="minPrice"  class="form-control" value="${cookie.minPriceCookie.value}"/></td>
             </div>
             <div class="form-group col-xs-2">
                      <td>maxPrice:</td>
                      <td><form:input path="maxPrice"  class="form-control" value="${cookie.maxPriceCookie.value}"/></td>
             </div>
         </div>
         <div class="row">
             <div class="form-group col-xs-3">
                    <c:if test="${cookie.buyNowCookie.value == true}">
                          <form:checkbox path="buyNow" value="${cookie.buyNowCookie.value}"  checked="checked"/> Show only buy it now items
                    </c:if>
                    <c:if test="${cookie.buyNowCookie.value != true}">
                          <form:checkbox path="buyNow" value="${cookie.buyNowCookie.value}" /> Show only buy it now items
                    </c:if>
             </div>
         </div>
         <div class="row">
             <div class="form-group col-xs-3">
                      <td>startDate:</td>
                      <td><form:input path="startDate" class="form-control" value="${cookie.startDateCookie.value}"/></td>
                       <td ><form:errors path="startDate" cssClass="errorText"/></td>

             </div>
         </div>
         <div class="row">
             <div class="form-group col-xs-3">
                      <td>expireDate:</td>
                      <td><form:input path="expireDate" class="form-control" value="${cookie.expireDateCookie.value}"/></td>
                      <td ><form:errors path="expireDate" cssClass="errorText"/></td>
             </div>
         </div>
         <div class="row">
             <div class="form-group col-xs-3">
                      <td><font color="red"><p>Format date: DD/MM/YYYY HH:MI</p></font></td>
             </div>
         </div>
         <div class="row">
             <div class="form-group col-xs-3">
                      <td>bidCount:</td>
                      <td><form:input path="bidCount"  class="form-control" value="${cookie.bidCookie.value}" /></td>
                      <td ><form:errors path="bidCount" cssClass="errorText"/></td>
             </div>
         </div>
         <div class="row">
             <div class="col-2 col-md-1 ">
                <button type="submit" class="btn btn-success" value="Save Changes" >Search</button>
             </div>

         </div>
    </form:form>
    <form:form method="GET"  action="clear" class="form-group">
          <button type="submit" class="btn btn-primary" value="Save Changes">Clear Search</button>
    </form:form>
    <div>
</div>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/bootstrap.js"></script>
<script src="js/Authorization.js"></script>
</body>
</html>