<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.incapp.model.DAO"%>
<%
String email = (String) session.getAttribute("email");
if (email == null) {
	session.setAttribute("msg", "Please Login or Register !");
	response.sendRedirect("index.jsp");
} else {
	String name = (String) session.getAttribute("name");
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>PeopleTalk</title>
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/custom.css" rel="stylesheet">

<script language="Javascript" src="js/jquery.js"></script>
<script type="text/JavaScript" src='js/state.js'></script>
</head>
<body data-spy="scroll" data-target="#my-navbar">
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="profile.jsp">PeopleTalk</a>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav navbar-right">
					<li><div class="navbar-text">
							<p>
								Welcome: <b> <%=name%>
								</b> <img src="GetPhoto?email=<%=email%>" height="30px"
									style="border-radius: 50%">
							</p>
						</div></li>
					<li><a href="profile.jsp">Home</a></li>
					<li><a href="Logout">Logout</a>
					<li>
				</ul>
			</div>
		</div>
	</nav>
	</br>
	</br>
	<div class="container">
		<div class="panel-body">
			<form action="SearchPeople" method="post" class="form-horizontal">
				<div class="form-group">
					<label for="state" class="col-lg-3 control-label">State:</label>
					<div class="col-lg-9">
						<select name="state" class="form-control" id="listBox"
							onchange='selct_district(this.value)'>

						</select>
					</div>
				</div>
				<!--end form group-->
				<div class="form-group">
					<label for="city" class="col-lg-3 control-label">City:</label>
					<div class="col-lg-9">
						<select name="city" class="form-control" id='secondlist'>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="area" class="col-lg-3 control-label">Area:</label>
					<div class="col-lg-9">

						<input type="text" name="area" class="form-control" id="area"
							placeholder="Enter your Area" />
					</div>
				</div>
				<div class="form-group">
					<div class="col-lg-10 col-lg-offset-3">
						<button  class="btn btn-primary">Search</button>
					</div>
				</div>
			</form>
		</div>
		<div class="panel panel-default text center">
			<div class="panel-heading text-center">
				<%
				String state = (String) session.getAttribute("state");
				String city = (String) session.getAttribute("city");
				String area = (String) session.getAttribute("area");
				%>
				<h3>
					Search Results for:
					<%=state%>/<%=city%>/<%=area%></h3>
			</div>

		</div>
	</div>
	</br>
	</br>
	<div class="container">
		<%
		ArrayList<HashMap<String, String>> users = (ArrayList<HashMap<String, String>>) session.getAttribute("users");
		for (HashMap<String, String> user : users) {
			if(((String)user.get("email")).equalsIgnoreCase(email) != true){
		%>
		<div class="row">
			<div class="col-lg-3">
				<img src="GetPhoto?email=<%=user.get("email")%>" height="100px">
			</div>
			<div class="col-lg-7">
				<div class="form-group">
					<label for="email" class="control-label">Name: <font
						color="grey"><%=user.get("name")%></font></label><br> <label
						for="name" class="control-label">Email:<font color="grey">
							<%=user.get("email")%></font></label><br> <label for="gender"
						class="control-label">Gender: <font color="grey"><%=user.get("gender")%></font></label><br>
					<label for="dob" class="control-label">Date of Birth: <font
						color="grey"><%=user.get("dob")%></font></label><br> <label
						for="phone" class="control-label">Phone: <font
						color="grey"><%=user.get("phone")%></font></label><br>
				</div>
			</div>
			<form action="Talk.jsp" method="post" class="form-horizontal">
				<div class="col-lg-2">
					<div class="form-group">
						</br> </br>
						<input type="hidden" name="u_email" value="<%=user.get("email")%>" />
						<button class="btn btn-primary">Talk</button>
					</div>
				</div>
			</form>
		</div>
		<hr>
		<%
		}
		}
		%>
	</div>
	</section>
	</div>
	</br>
	<!--footer-->
	<div class="navbar navbar-inverse navbar-fixed-bottom">
		<div class="container">
			<div class="navbar-text pull-left">
				<p>Design and Develop by Nikhil Pathak</p>
			</div>

		</div>
	</div>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>
<%
}
%>