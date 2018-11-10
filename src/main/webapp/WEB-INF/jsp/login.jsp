<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <jsp:include page="/header"/>
</head>
<body>
  <jsp:include page="/menu"/>
  <div class="container">
    <form role="form" action="<c:url value='/login' />" method="post">
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
      <div class="login-container">
        <div class="panel panel-default">
          <div class="panel-heading">
            <h3 class="panel-title">Login</h3>
          </div>
          <div class="panel-body">
            <div class="form-group">
              <label for="username">User</label>
              <input class="form-control" id="username" name="username" value="" placeholder="Usuario" type="text">
            </div>
            <div class="form-group">
              <label for="password">Password</label>
              <input class="form-control" id="password" name="password" placeholder="password" type="password">
            </div>
            <div class="checkbox">
              <label><input name="remember-me" type="checkbox">Remember me</label>
            </div>
          </div>
          <div class="panel-footer">
            <input type="submit" name="submit" value="Login" class="btn btn-primary"/>
          </div>
        </div>
        <c:if test="${not empty error}">
          <div class="alert alert-danger" role="alert">${error}</div>
        </c:if>
      </div>
    </form>
  </div>
  <jsp:include page="include/footer.jsp" />
</body>
</html>
