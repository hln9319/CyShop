<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title><c:if test="${pageTitle != null}">${pageTitle} |</c:if> CyShop</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery-ui.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/cyShop.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/dataTables.bootstrap.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-formhelpers.min.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/alert.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ShoppingCartManager.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap-formhelpers-datepicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap-formhelpers-states.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap-formhelpers.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/additional-methods.js"></script>
</head>
<%
	String username = "guest";
	String userType = "Guest";
	String profilePicture = "";
	Integer userId = 0;
	boolean loggedIn = false;

	if (session != null) {
		username = (String) session.getAttribute("username");
		userType = (String) session.getAttribute("userType");
		profilePicture = (String) session.getAttribute("profilePicture");
		userId = (Integer) session.getAttribute("userId");
		loggedIn = (Boolean) session.getAttribute("loggedIn");
	}
%>
<body>
<nav id="main-navbar" class="navbar navbar-primary" role="navigation">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a href="${pageContext.request.contextPath}/controller/store/view"><img src="${pageContext.request.contextPath}/img/logo2.png" /></a>
    </div>
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <form class="navbar-form navbar-left" role="search" method="GET" action="/CyShop/controller/categories/search">
        <div class="form-group"><input type="text" class="form-control" placeholder="Search" name="keyword"></div>
        <button type="submit" class="btn btn-danger" style="margin-right: 1em">Search</button>
        Welcome, <%= session.getAttribute("username") %>!
        <c:if test="${fact.fact != null}">
        	<span class="text-danger"><b>Did you know?</b> ${fact.fact}</span>
        </c:if>
      </form>
      <ul id="header-list" class="nav navbar-nav navbar-right" style="font-weight:bold;">
      	<% if (!loggedIn) { %>
      		<li class="active"><a href="${pageContext.request.contextPath}/controller/store/login" class="text-danger">Login</a></li>
      		<li class="active"><a href="${pageContext.request.contextPath}/controller/user/create" class="text-danger">Sign Up!</a></li>
      	<%
		   } else {
		%>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle text-danger" data-toggle="dropdown">
                        <c:choose>
                        	<c:when test="<%= profilePicture != null %>">
                        		<img id="item-picture" style="max-width:25px; max-height:25px;" src="http://proj-309-r14.cs.iastate.edu/CyShopUserImages/<%= profilePicture %>" alt="Profile Picture" />
                        	</c:when>
                        	<c:otherwise>
                        		<img src="${pageContext.request.contextPath}/img/itemPlaceholder.jpg" style="max-width: 100px; max-height: 100px;" alt="No image uploaded!" />
                        	</c:otherwise>
                        </c:choose>
                        <strong><%= username %></strong>
                        <span class="glyphicon glyphicon-chevron-down"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li>
                            <div class="navbar-login">
                                <div class="row">
                                    <div class="col-lg-4">
                                        <p class="text-center">
                                            <c:choose>
                        						<c:when test="<%= profilePicture != null %>">
                        							<img id="item-picture" style="max-width:100px; max-height:100px;" src="http://proj-309-r14.cs.iastate.edu/CyShopUserImages/<%= profilePicture %>" alt="Profile Picture" />
                        						</c:when>
                        						<c:otherwise>
                        							<img src="${pageContext.request.contextPath}/img/itemPlaceholder.jpg" style="max-width: 100px; max-height: 100px;" alt="No image uploaded!" />
                        						</c:otherwise>
                        					</c:choose>
                                            <strong><%= username %></strong>
                                        </p>
                                    </div>
                                    <div class="col-lg-8">
                                        <c:if test="${userType == 'Administrator'}"><p class="text-left">
                                            <a href="${pageContext.request.contextPath}/controller/admin/view" class="btn btn-warning btn-block btn-sm">Administration</a>
                                        </p></c:if>
                                        <c:if test="${userType == 'Administrator' || userType == 'Seller'}"><p class="text-left">
                                            <a href="${pageContext.request.contextPath}/controller/seller/view" class="btn btn-warning btn-block btn-sm">Seller</a>
                                        </p></c:if>
                                        <p class="text-left">
                                            <a href="${pageContext.request.contextPath}/controller/shopper/view" class="btn btn-warning btn-block btn-sm">Shopper</a>
                                        </p>
                                        <p class="text-left">
                                            <a href="${pageContext.request.contextPath}/controller/user/profile/<%= userId %>" class="btn btn-warning btn-block btn-sm">My Profile</a>
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <div class="navbar-login navbar-login-session">
                                <div class="row">
                                    <div class="col-lg-12">
                                        <p>
                                            <a href="${pageContext.request.contextPath}/controller/user/logUserOut/<%= userId %>" class="btn btn-danger btn-block">Logout</a>
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </li>
                    </ul>
                </li>
            </ul>
      	<% } %>
	</div>
  </div>
</nav>
