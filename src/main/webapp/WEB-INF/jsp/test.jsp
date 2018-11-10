<%@ taglib prefix="o" uri="http://openkm.com/tags/utils" %>
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
    <div class="jumbotron">
      <h2><strong>Quick test</strong></h2>
    </div>
    <div class="row">
      <div class="col-sm-8">
        <div style="text-align: center; width:100%;"><h3><strong>Sample text</strong></h3></div>
        ${content}
      </div>
      <div class="col-sm-4" style="text-align: center;">
        <div><h3><strong>Extracted keywords</strong></h3></div>
        <c:forEach items="${keywords}" var="keyword">
          <button type="button" class="btn btn-success btn-sm btn-block">${keyword}</button>
          <br/> 
        </c:forEach>
      </div>
    </div>
  </div>
  <jsp:include page="include/footer.jsp" />
</body>
</html>
