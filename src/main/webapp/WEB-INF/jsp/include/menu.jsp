<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="<c:url value="/"/>">Home</a>
    </div>
    <div id="navbar" class="collapse navbar-collapse">
      <ul class="nav navbar-nav">  
        <sec:authorize access="hasRole('ADMIN')">
          <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
              Admin <span class="caret"></span>
            </a>
            <ul class="dropdown-menu">
              <li><a href="<c:url value='/admin/home'/>">Home</a></li>              
            </ul>
          </li>
          <li><a href="<c:url value='/admin/model/home'/>">Model</a></li>
        </sec:authorize>
      </ul>

      <ul class="nav navbar-nav navbar-right">
      <c:choose>
      	<c:when test="${not empty user}">
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
            ${user.fullName} <span class="caret"></span>
          </a>
          <ul class="dropdown-menu">
            <li>
              <a href="<c:url value="/logout" />">
                <span class="glyphicon glyphicon-log-out"></span>
                Logout
              </a>
            </li>
          </ul>
        </li>
        </c:when>
        <c:otherwise>
        	<ul class="nav navbar-nav">
        		<li>
        			<a href="<c:url value="/login" />">
                		<span class="glyphicon glyphicon-log-in"></span>
                		Login
              		</a>
              	</li>
			</ul>
        </c:otherwise>
        </c:choose>
      </ul>
    </div>
  </div>
</nav>