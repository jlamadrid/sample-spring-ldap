<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>
<h2>People</h2>
<c:forEach var="name" items="${names}">
    ${name} <br/>
</c:forEach>
</html>
