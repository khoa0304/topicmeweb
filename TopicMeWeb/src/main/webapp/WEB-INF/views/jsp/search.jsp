<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ page session="false" language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>TopicMe Search</title>

<link rel="stylesheet" 	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<spring:url value="/resources/core/js/searchpage.js" var="searchpagejs" />
<script src="${searchpagejs}"/></script>


<spring:url value="/resources/core/css/searchpage.css" var="searchpageCss" />
<link href="${searchpageCss}" rel="stylesheet" />

</head>
<body>

	<div>

		<div id="searchfield">
		
			<form:form method="POST"  modelAttribute="searchInputs"  action="${pageContext.request.contextPath}/search">
				<form:input type="text" id="query" path="query" name="query" placeholder="Start With a Keyword..." class="biginput"/>
				<form:input path="submit" type="submit" value="Go"/>
			</form:form>
		
		</div>

	</div>



</body>
</html>