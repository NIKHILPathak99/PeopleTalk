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
			<%
				String u_email=request.getParameter("u_email");
				if(u_email==null){
					u_email=(String)session.getAttribute("u_email");
					
				}else{
					session.setAttribute("u_email",u_email);
				} 
				
				DAO db=new DAO();
				HashMap<String,String> user=db.getUserDetails(u_email);
				//db.closeConnection();
			%>
			<div class="row">
				
				<div class="col-lg-4">
					<img src="GetPhoto?email=<%= u_email %>" height="170px">
				</div>
				<div class="col-lg-4">
					<div class="form-group">
					</br>
						<label for="email" class="control-label">Name: <font color="grey"><%= user.get("name") %></font></label><br>
						<label for="gender" class="control-label">Gender: <font color="grey"><%= user.get("gender") %></font></label><br>
						<label for="phone" class="control-label">Phone: <font color="grey"><%= user.get("phone") %></font></label><br>
					</div>
				</div>
				<div class="col-lg-4">
					<div class="form-group">
					</br>
						<label for="name" class="control-label">Email:<font color="grey"> <%= u_email %></font></label><br>
						<label for="dob" class="control-label">Date of Birth: <font color="grey"><%= user.get("dob") %></font></label><br>
						<label for="address" class="control-label">Address: <font color="grey"><%= user.get("area") %>, <%= user.get("city") %>, <%= user.get("state") %></font></label><br>
					</div>
				</div>
			</div>
		</div>
		</br>
		<div class="container text-center">
		<%
			String msg=(String)session.getAttribute("msg");
			if(msg!=null){
		%>
			<p class="bg-primary text-center" style="padding:20px;"> <%=msg %> </p>
		<%		
				session.setAttribute("msg", null);
			}
		%>
			<div class="panel panel-default">
				<div class="panel-body text-center">
					<form action="SendMessage" data-toggle="validator" method='post' enctype='multipart/form-data' class="form-horizontal">
						<div class="form-group">
							<label for="message" class="col-lg-2 control-label">Message:</label>
								<div class="col-lg-4">
									<textarea id="message" name="message" class="form-control" rows="5" cols="50" required></textarea>
								</div>
						</div><!--end form group-->
							<div class="form-group">
							<label for="filetosend" class="col-lg-2 control-label">File to Send:</label>
								<div class="col-lg-4">
									<input type="file" name="ufile" class="form-control" id="filetosend"/>
								</div>
								<div class="col-lg-2">
									<input type="hidden" name="u_email" value="<%=u_email%>" />
									<button type="submit" class="btn btn-primary">Send</button>
								</div>
						</div><!--end form group-->
					</form>
				</div>
			</div>
		</div>
		<div class="container text-center">
			<div class="panel panel-default">
				<div class="panel-body text-center">
					<div class="row">
						<div class="col-lg-6">
							<div class="panel panel-default">
								<div class="panel-heading text-center">
									<h5><%= name %>'s Messages</h5>
								</div>
								<%
								ArrayList<HashMap<String, String>> messages = db.getMessages(email,u_email);
								for (HashMap<String, String> message : messages) {
								%>
								<div class="panel-body text-left">
									<p><%= message.get("message") %></p>
									<div class="row">
										<font size="1">
										<div class="form-group">
											<div style="padding:10px;">
												<p >File: <a href="DownloadFile?id=<%= message.get("id") %>"><%= message.get("file_name") %></a></p>
												
												<p >Date: <%= message.get("msg_date") %> : <%= message.get("msg_time") %></p>
											</div>
										</div>
										</font>
									</div>
									<hr>
								</div>
								<%
								}	
								%>
							</div>
						</div>
						<div class="col-lg-6">
							<div class="panel panel-default">
								<div class="panel-heading text-center">
									<h5><%= user.get("name") %>'s Messages</h5>
								</div>
								<%
								messages = db.getMessages(u_email,email);
								for (HashMap<String, String> message : messages) {
								%>
								<div class="panel-body text-left">
									<p><%= message.get("message") %></p>
									<div class="row">
										<font size="1">
										<div class="form-group">
											<div style="padding:10px;">
												<p >File: <a href="DownloadFile?id=<%= message.get("id") %>"><%= message.get("file_name") %></a></p>
												
												<p >Date: <%= message.get("msg_date") %> : <%= message.get("msg_time") %></p>
											</div>
										</div>
										</font>
									</div>
									<hr>
								</div>
								<%
								}	
								%>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	<hr>
	
	<!--footer-->
	
	<div class="navbar navbar-inverse navbar-fixed-bottom">
		<div class="container">
			<div class="navbar-text pull-left">
				<p>Design and Develop by Nikhil Pathak</p>
			</div>
		</div>
	</div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/jquery-2.2.2.min.js"></script>
    <script src="js/validator.js"></script>
  </body>
</html>
<%
}
%>