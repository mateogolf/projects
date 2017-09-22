<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home Page</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/style.css"/>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<nav class="navbar navbar-default col-lg-10 col-lg-offset-1 col-md-10 col-md-offset-1">
	<div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>                        
            </button>
        <p class="navbar-brand">Welcome to DojoSubscriptions ${currentUser.firstName}!</p>
    </div>
    
    <div class="collapse navbar-collapse" id="myNavbar">
        <ul class="nav navbar-nav navbar-right">
        	<li>
            	<form id="logoutForm" method="POST" action="/logout">
			        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			        <input type="submit" class="btn btn-link" value="Logout" />
			    </form>
            </li>
            
        </ul>
    </div>
</nav>

<div class="container-fluid col-lg-4 col-lg-offset-4 col-md-6 col-md-offset-3 col-sm-10 col-sm-offset-1">
<form:errors path="subscription.*" />

<form:form method="POST" action="/selection" modelAttribute="subscription">
    <h2>Please choose a subscription and start date</h2>
    <table>
    <tr>
        <td><form:label path="dueDate">Due Day:</form:label></td>
        <td></td>
        <td>
			<form:select path="dueDate">
            <c:forEach items="${dates}" var="date">
                <form:option value="${date}">${date}</form:option>
            </c:forEach>
        	</form:select>
		</td>
    </tr>
    <tr>
        <td><form:label path="pack">Date of Event:</form:label></td>
        <td></td>
        <td>
			<form:select path="pack">
            <c:forEach items="${packages}" var="pack">
                <form:option value="${pack.id}">${pack.name} ($${pack.cost})</form:option>
            </c:forEach>
        	</form:select>
		</td>
    </tr>
    <tr>
        <td><form:hidden path="user" value="${currentUser.id}" /></td>
        <td></td>
        <td><input type="submit" value="Register!" /></td>
    </tr>
    </table>
</form:form>


</div>
</body>
</html>