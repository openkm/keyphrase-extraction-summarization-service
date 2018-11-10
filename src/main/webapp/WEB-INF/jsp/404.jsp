<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <jsp:include page="/header"/>
</head>
<body>
  <jsp:include page="/menu"/>
  <div class="container">
    <div class="alert alert-danger" role="alert">
      <h3>Page Not Found</h3>
      <p>Por favor, contacta con soporte.</p>
    </div>
  </div>
  <jsp:include page="include/footer.jsp" />
</body>
</html>
