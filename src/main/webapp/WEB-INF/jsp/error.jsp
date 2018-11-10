<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <jsp:include page="include/header"/>
</head>
<body>
  <jsp:include page="include/menu"/>
  <div class="container">
    <div class="alert alert-danger" role="alert">
      <h3>Error</h3>
      <p>Por favor, contacta con soporte.</p>
      <p>&nbsp;</p>
      <div class="well well-sm" style="margin-bottom: 0;">
        <p><strong>Timestamp:</strong> ${timestamp}</p>
        <p><strong>Path:</strong> ${path}</p>
        <p><strong>Status:</strong> ${status}</p>
        <p><strong>Message:</strong> ${message}</p>
        <!--
          Timestamp: ${timestamp}
          Path: ${path}
          Status: ${status}
          Message: ${message}
          <%--
          <c:forEach items="${exception.stackTrace}" var="st">
            ${st}
          </c:forEach>
          --%>
        -->
      </div>
    </div>
  </div>
  <jsp:include page="include/footer.jsp" />
</body>
</html>
