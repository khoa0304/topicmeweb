<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
body {
	font-family: "Lato", sans-serif;
}

.sidenav {
	display: none;
	height: 100%;
	width: 250px;
	position: fixed;
	z-index: 1;
	top: 0;
	left: 0;
	background-color: #fdfcfc;
	overflow-x: hidden;
	padding-top: 60px;
}

.sidenav a {
	padding: 8px 8px 8px 32px;
	text-decoration: none;
	font-size: 25px;
	color: #818181;
	display: block;
}

.sidenav a:hover {
	color: #f1f1f1;
}

.sidenav .closebtn {
	position: absolute;
	top: 0;
	right: 25px;
	font-size: 36px;
	margin-left: 50px;
}

@media screen and (max-height: 450px) {
	.sidenav {
		padding-top: 15px;
	}
	.sidenav a {
		font-size: 18px;
	}
}

* {
	box-sizing: border-box;
}

body {
	margin: 0;
}

/* Create two unequal columns that floats next to each other */
.column {
	float: left;
	padding: 10px;
	height: 300px; /* Should be removed. Only for demonstration */
}

.left {
	width: 15%;
}

.right {
	float: right;
	width: 85%;
}

/* Clear floats after the columns */
.row:after {
	content: "";
	display: table;
	clear: both;
}

input[type=text], select, textarea{
    width: 100%;
    padding: 12px;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
    resize: vertical;
}

label {
    padding: 12px 12px 12px 0;
    display: inline-block;
}

input[type=submit] {
    background-color: #4CAF50;
    color: white;
    padding: 12px 20px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    float: right;
}

input[type=submit]:hover {
    background-color: #45a049;
}

.container {
    border-radius: 5px;
    background-color: #f2f2f2;
    padding: 20px;
}

.col-25 {
    float: left;
    width: 15%;
    margin-top: 6px;
}

.col-75 {
    float: left;
    width: 85%;
    margin-top: 6px;
}

/* Responsive layout - when the screen is less than 600px wide, make the two columns stack on top of each other instead of next to each other */
@media (max-width: 600px) {
    .col-25, .col-75, input[type=submit] {
        width: 100%;
        margin-top: 0;
    }
}
</style>
<title>TopicMe</title>
</head>
<body>



	<span style="font-size: 30px; cursor: pointer" onclick="openNav()">&#9776;
		Topics</span>

	<div class="row">
		<div id="mySidenav" class="column left sidenav"
			style="background-color: #D3D3D3;">
			<a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
			<a href="${pageContext.request.contextPath}/index/Kafka" onclick="closeNav()">Kafka</a> 
			<a href="${pageContext.request.contextPath}/index/Java" onclick="closeNav()">Java</a> 
			<a href="${pageContext.request.contextPath}/index/Mongodb" onclick="closeNav()">Mongodb</a>
		</div>

		<div class="column right" style="background-color: #FFFFFF;">
			<h2>Add Link</h2>
			<form:form method="POST" modelAttribute="linkAndNotes" action="./addTopic">
				<div class="row">
					<div class="col-25">
						<label for="link">Link</label>
					</div>
					<div class="col-75">
						<form:input type="text" path="link" id="link" name="link" placeholder="Paste Link Here.."/>
					</div>
				</div>

				<div class="row">
					<div class="col-25">
						<label for="notes">Notes</label>
					</div>
					<div class="col-75">
						<form:textarea id="notes" path="notes" name="notes" placeholder="Write quick notes.." style="height: 100px"></form:textarea>
						<form:input type="hidden" path="topic" name="topic" value="${topic}"/>
					</div>
				</div>
				<div class="row">
					<input type="submit" value="Submit"/>
				</div>
			</form:form>
		</div>
	</div>
	<script>
		function openNav() {
			document.getElementById("mySidenav").style.display = "block";
		}

		function closeNav() {
			document.getElementById("mySidenav").style.display = "none";
		}
	</script>


</body>
</html>
