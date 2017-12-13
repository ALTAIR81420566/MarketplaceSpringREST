<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>



<!DOCTYPE html>


<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>General</title>

    <!-- Bootstrap -->
    <link href="css/style.css" rel="stylesheet">
    <link href="css/bootstrap.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.js"></script>
    <![endif]-->
</head>
<body>

<div class = "logInfo">
    <div class="row col-md-offset-2">
        <div class="col-2 col-md-2 ">
            <p>You are logged in as: </p>
        </div>
        <div class="col-2 col-md-1 ">
            <p id="userLogin">${user.login}</p>
        </div>
        <div class="col-2 col-md-1 ">
            <a id="LogOut"  onclick="window.location.href='/';">LogOut</a>
        </div>
    </div>
</div>
<div class="container">

    <h1>Online marketplace</h1>
    <p>Search parameters</p>
    <p>Keyword: </p>

    <div class="row justify-content-md-center">

        <div class="row">
            <form method="get" action="/general">
                <div class="col-2 col-md-3 ">
                    <p><input maxlength="25" size="35" name="searchText" value="${fn:escapeXml(general.searchText)}"></p>
                </div>
                <div class="col-6 col-md-1">
                    <select name="findBy" value="${fn:escapeXml(general.findBy)}">
                        <option>Title</option>
                        <option>uId</option>
                        <option>Description</option>
                    </select>
                </div>
                <div class="col-2 col-md-1 ">
                     <button id = "searchBtn" type="submit">Search</button>
                </div>
            </form>
            <div class="col-6 col-md-3">
                 <c:if test="${!user.role.equals('guest')}">
                          <div class="col-2 col-md-6 ">
                               <form method="get" action="/add">
                                   <button id="addBtn" type="submit">Add my product</button>
                               </form>
                          </div>
                          <div class="col-5 col-md-6 ">
                             <button type="submit"  onclick="window.location.href='/my_product'">My products</button>
                          </div>
                 </c:if>
            </div>
        </div>



    </div>

    <form method="post" action="/general">
                <div class="table-responsive">
                <table class="table ">
                    <tr>
                        <th width="30">UID</th>
                        <th width="100">Title</th>
                        <th width="700">Descriprion</th>
                        <th width="100">Seller</th>
                        <th width="100">Start price</th>
                        <th width="100">Step bid</th>
                        <th width="200">Stop Date</th>
                        <th width="100">Best offer</th>
                        <th width="100">Bidder ID</th>
                        <th width="200">Action</th>
                        <th></th>
                    </tr>

                    <c:forEach items="${products}" var="item">
                        <tr>
                            <td>

                            ${item.key.uID}
                            </td>
                            <td>${item.key.title}</td>
                            <td>${item.key.description}</td>
                            <td>${item.key.sellerID}</td>
                            <td>${item.key.startPrice}</td>
                            <c:choose>
                                <c:when test="${item.key.buyNow == 0}">
                                    <td>${item.key.step}</td>
                                    <td>
                                        <jsp:useBean id="dateValue" class="java.util.Date"/>
                                        <jsp:setProperty name="dateValue" property="time"
                                                         value="${item.key.time + item.key.startBiddingDate}"/>
                                        <fmt:formatDate value="${dateValue}" pattern="dd/MM/yyyy HH:mm"/>

                                    </td>
                                </c:when>
                                <c:when test="${item.key.buyNow == 1}">
                                    <td>-</td>
                                    <td>-</td>
                                </c:when>
                            </c:choose>
                            <c:if test="${item.value.count == 0}">
                                <td> b</td>
                                <td> b</td>
                            </c:if>
                            <c:if test="${item.value.count != 0}">
                                <td> ${item.value.count}</td>
                                <td> ${item.value.userId}</td>
                            </c:if>
                            <c:if test="${item.key.sold == 1}">
                                 <td>SOLD</td>
                            </c:if>
                            <c:if test="${item.key.sold == 0}">
                            <jsp:useBean id="now" class="java.util.Date"/>

                                    <c:if test="${!user.role.equals('guest')}">
                                          <c:if test="${item.key.buyNow == 1}">
                                          <form  method="post" action="/buy">
                                             <input maxlength="10" type="hidden" name="productId" value="${item.key.uID}">
                                             <td><button id="buyNowBtn" type ="submit">Buy now</button></td>
                                          </form>
                                          </c:if>

                                          <c:if test="${item.key.buyNow == 0}">
                                              <c:if test="${dateValue.getTime() > now.getTime()}">
                                                  <form  method="post" action="/general">
                                                        <td>
                                                            <input type="hidden" name="buy" value="false">
                                                            <input maxlength="10" size="5" type="number" name="count" value="${fn:escapeXml(products.count)}">
                                                            <button id="bidBtn" type ="submit" name="productId" value="${item.key.uID}">Bid</button>
                                                        </td>
                                                  </form>
                                              </c:if>
                                              <c:if test="${dateValue.getTime() < now.getTime()}">
                                                  <td>Time is over</td>
                                              </c:if>
                                           </c:if>
                                    </c:if>


                            </c:if>

                        </tr>
                    </c:forEach>
                </table>
            </div>
    </form>
</div>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/bootstrap.js"></script>
<script src="js/GeneralPage.js"></script>
</body>
</html>