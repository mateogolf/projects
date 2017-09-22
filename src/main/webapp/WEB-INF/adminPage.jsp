<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin Page</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/style.css"/>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<c:set var = "now" value = "<%= new java.util.Date()%>" />
<nav class="navbar navbar-default col-lg-10 col-lg-offset-1 col-md-10 col-md-offset-1">
	<div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>                        
            </button>
        <p class="navbar-brand">Admin Page!</p>
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

<h2>Customers</h2>
<table class="table table-striped table-hover table-condensed table-responsive points">
    <thead>
        <tr>
            <!-- <th>Name</th> -->
            <th>Name</th>
            <th>Next Due Date</th>
            <th>Amount Due</th>
            <th>Package Type</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${users}" var="user">
        <c:if test="${!user.isAdmin()}">
        <tr>
            <td>${user.firstName} ${user.lastName}</td>
            <td><fmt:formatDate pattern="MMMMM dd, yyyy" value="${user.subscription.nextDueDate()}" /></td>
            <td>$${user.subscription.pack.cost}</td>
            <td>${user.subscription.pack.name}</td>
        </tr>
        </c:if>
        </c:forEach>
    </tbody>
</table>
<h2>Packages</h2>
<table class="table table-striped table-hover table-condensed table-responsive points">
    <thead>
        <tr>
            <!-- <th>Name</th> -->
            <th>Name</th>
            <th>Cost</th>
            <th>Available</th>
            <th>Users</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
        
        <c:forEach items="${packs}" var="pack">
        <tr>
        	<td>${pack.name}</td>
        	<td>${pack.cost}</td>
        	<c:choose>
        	<c:when test="${pack.availability}">
	        	<td>available</td>
	        	<td>${pack.countSubs()}</td>
				<td><a href="/deactivate/${pack.id}">deactivate</a> 
					<c:if test="${pack.countSubs()==0}">
	        			<a href="/events/${user.id}/delete">Delete</a>
	        		</c:if>
				</td>
        	</c:when>
        	<c:otherwise>
	        	<td>unavailable</td>
	        	<td>${pack.countSubs()}</td>
                <td><a href="/activate/${pack.id}">activate</a> 
					<c:if test="${pack.countSubs()==0}">
	        			<a href="/events/${user.id}/delete">Delete</a>
	        		</c:if>
				</td>
            </c:otherwise>
        	</c:choose>
        </tr>
        </c:forEach>
        
    </tbody>
</table>

<fieldset>
    <Legend>Create Package</Legend>
    <form:form method="POST" action="/admin" modelAttribute="pack">
    <table>
        <tr>
            <td><form:label path="name">Package Name:</form:label></td>
            <td></td>
            <td><form:input path="name" /></td>
        </tr>
        <tr>
            <td><form:label path="cost">Cost:</form:label></td>
            <td></td>
            <td><form:input path="cost" /></td>
        </tr>
        <tr>
            <td><form:hidden path="availability" value="true" /></td>
            <td></td>
            <td><input type="submit" value="Create new Package" /></td>
        </tr>
        </table>

    </form:form>
</fieldset>
</div>
</body>
</html>