<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ page session="true" language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Solr Auto Complete</title>

<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		//var URL_PREFIX = "http://localhost:8983/solr/websearch/select?q=notes_ngram:";
		var URL_PREFIX = "http://localhost:8080/TopicMeWeb/search/q=";
		var URL_SUFFIX = "&wt=json";
		$("#searchBox").autocomplete({
			source : function(request, response) {
				var URL = URL_PREFIX + $("#searchBox").val() + URL_SUFFIX;
				$.ajax({
					url : URL,
					success : function(data) {
						var docs = JSON.stringify(data.response.docs);
						var jsonData = JSON.parse(docs);
						response($.map(jsonData, function(value, key) {
							return {
								label : value.notes_ngram
							}
						}));
					},
					dataType : 'jsonp',
					jsonp : 'json.wrf'
				});
			},
			minLength : 1
		})
	});
	$(function() {
		//var URL_PREFIX = "http://localhost:8983/solr/websearch/select?q=notes_ngram:";
		var URL_PREFIX = "http://localhost:8080/TopicMeWeb/search/q=";
		var URL_SUFFIX = "&wt=json";
		$("#ngramBox").autocomplete({
			source : function(request, response) {
				//var searchString = "\"" + $("#ngramBox").val() + "\"";
				var searchString = $("#ngramBox").val();
				var URL = URL_PREFIX + searchString + URL_SUFFIX;
				$.ajax({
					url : URL,
					success : function(data) {
						var docs = JSON.stringify(data.response.docs);
						var jsonData = JSON.parse(docs);
						response($.map(jsonData, function(value, key) {
							return {
								label : value.notes_ngram
							}
						}));
					},
					dataType : 'jsonp',
					jsonp : 'json.wrf'
				});
			},
			minLength : 1
		})
	});
</script>
</head>
<body>
	<div>
		<p>Type 'A' or 'The'</p>
		<label for="searchBox">Tags: </label> <input id="searchBox"></input>
	</div>
	<div>
		<p>Type 'Solr'</p>
		<label for="ngramBox">Tags: </label> <input id="ngramBox"
			name="search" placeholder="Search.."></input>
	</div>

	<div>${message}</div>

	<c:forEach  items="${pages}" var="page">
		<div id="col1">
			<a href="#">${page.title}</a>
		</div>
		
	</c:forEach>
	
	
	
</body>
</html>