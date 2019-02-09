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
      <h2>Current model configuration parameters</h2>
      <table class="table">
        <tr>
          <th scope="row">Training path</th>
          <td>${trainingPath}</td>
        </tr>
        <tr>
          <th scope="row">Number of training files</th>
          <td>
              ${numerOfTrainingFiles}
              <c:if test="${numerOfTrainingFiles > 0}">
                <button type="button" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#cleanTrainingModal">clean</button>
              </c:if>
          </td>
        </tr>
        <tr>
          <th scope="row">Vocabulary file</th>
          <td>${vocabularyFile}</td>
        </tr>
        <tr>
          <th scope="row">Vocabulary type</th>
          <td>${vocabularyType}</td>
        </tr>
        <tr>
          <th scope="row">Stopword file</th>
          <td>${stopwordFile}</td>
        </tr>
        <tr>
          <th scope="row">Model file</th>
          <td>${modelFile}</td>
        </tr>
        <tr>
          <th scope="row">Number of model files</th>
          <td>
            ${numerOfModelFiles}
            <c:if test="${numerOfModelFiles > 0}">
              <a class="btn btn-primary btn-sm" href="<c:url value="/admin/model/list" />" role="button">List</a>
              <button type="button" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#cleanModelModal">clean</button>
            </c:if>
          </td>
        </tr>
        <tr>
          <th scope="row">Stemmer class</th>
          <td>${stemmerClass}</td>
        </tr>
        <tr>
          <th scope="row">Stopword class</th>
          <td>${stopwordClass}</td>
        </tr>
        <tr>
          <th scope="row">Language</th>
          <td>${language}</td>
        </tr>
        <tr>
          <th scope="row">Document encoding</th>
          <td>${documentEncoding}</td>
        </tr>
      </table>
    </div>
    <a class="btn btn-success center-block" href="<c:url value="/admin/model/create" />" role="button">Rebuild model</a>
  </div>

  <!-- Modal clean training -->
  <div id="cleanTrainingModal" class="modal fade" role="dialog">
    <div class="modal-dialog modal-sm">

      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Training files</h4>
        </div>
        <div class="modal-body">
          <p>You are going to delete training files</p>
          <p><strong>This action can not be reverted.</strong></p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
          <a class="btn btn-danger" href="<c:url value="/admin/model/cleanTrainig" />" role="button">Clean</a>
        </div>
      </div>

    </div>
  </div>

  <!-- Modal clean models -->
  <div id="cleanModelModal" class="modal fade" role="dialog">
    <div class="modal-dialog modal-sm">

      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Model files</h4>
        </div>
        <div class="modal-body">
          <p>You are going to delete model files</p>
          <p><strong>This action can not be reverted.</strong></p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
          <a class="btn btn-danger" href="<c:url value="/admin/model/cleanModels" />" role="button">Clean</a>
        </div>
      </div>

    </div>
  </div>


</body>
</html>
