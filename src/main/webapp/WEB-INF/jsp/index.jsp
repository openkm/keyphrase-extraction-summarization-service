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
      <h2><strong>OpenKM keyphrase extraction summarization service</strong></h2>
      <br/>
      <p><strong>Keywords and keyphrases</strong> (multi-word units) are widely used in large document collections.</p>
      <p>They describe the content of single documents and provide a kind of semantic metadata that is useful for a wide variety of purposes.</p> 
      <p>In libraries professional indexers select keyphrases from a controlled vocabulary (also called <i>Subject Headings</i>) according to defined cataloguing rules. On the Internet, digital libraries, or any depositories of data also use keyphrases (or here called content tags or content labels) to organize and provide a thematic access to their data.</p>
      <p><a href="http://community.nzdl.org/kea/">KEA</a> is an algorithm for extracting keyphrases from text documents. It can be either used for <strong>free indexing</strong> or for <strong>indexing with a controlled vocabulary</strong> build from The University of Waikato in the Digital Libraries and Machine Learning Labs of the Computer Science Department by <a href="http://www.cs.waikato.ac.nz/%7Eeibe/">Eibe Frank</a> and <a href="http://www.cs.waikato.ac.nz/%7Eolena/">Olena Modelyan</a></p>
      <p>OpenKM keyphrase extraction summarization service is an open-source software distributed under the <a href="https://www.gnu.org/licenses/agpl-3.0.en.html">GNU Affero General Public License</a>.
    </div>
    <a class="btn btn-success center-block" href="<c:url value="/test" />" role="button">Extract keywords quick test</a>
    <a class="btn btn-success center-block" href="<c:url value="/testTraining" />" role="button">Uploading training file test</a>
  </div>
  <jsp:include page="include/footer.jsp" />
</body>
</html>
