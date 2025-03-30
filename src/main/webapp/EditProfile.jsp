<%@page import="java.util.HashMap"%>
<%@page import="com.incapp.model.DAO"%>
<%
String email=(String)session.getAttribute("email");
if(email==null){
	session.setAttribute("msg", "Please Login or Register !");
	response.sendRedirect("index.jsp");
}else{
	String name=(String)session.getAttribute("name");
%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>PeopleTalk</title>
    <!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" />
<link href="css/custom.css" rel="stylesheet" />	
<link href="datetimepicker/css/datetimepicker.min.css" rel="stylesheet"  />
	
	<script language="Javascript" src="js/jquery.js"></script>
	<script type="text/JavaScript" src='js/state.js'></script>
  </head>
  <body data-spy="scroll" data-target="#my-navbar">
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="profile.jsp">PeopleTalk</a>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav navbar-right">
					<li><div class="navbar-text"><p>
					Welcome: <b> <%=name %>  </b>
					<img src="GetPhoto?email=<%= email %>" height="30px"  style="border-radius: 50%">
					</p></div></li>
					<li><a href="profile.jsp">Home</a></li>
					<li><a href="Logout">Logout</a><li>
				</ul>			
			</div>
		</div>
	</nav>
	</br>
	</br>
		<div class="container">
			<section>
				<div class="panel panel-default">
					<div class="panel-heading text-center">
						<h3>Edit Profile</h3>
					</div>
					<%
						String msg=(String)session.getAttribute("msg");
						if(msg!=null){
					%>
						
						<script>
							alert("<%=msg %>");
						</script> 
						
						<%-- <p class="bg-danger text-center" style="padding:20px;"> <%=msg %> </p> --%>
					<%		
							session.setAttribute("msg", null);
						}
					%>
					<div class="panel-body">
						<div class="container">
							<div class="row">
							
							<form action="ChangePhoto"  method='post' enctype="multipart/form-data"  data-toggle="validator" class="form-horizontal">
								<div class="col-lg-2 col-lg-offset-1">
									<img src="GetPhoto?email=<%= email %>" height="100px">
								</div>
								<div class="col-lg-2">
									<div class="form-group">
									</br></br>
										<label for="changephoto" class="control-label">Change Photo:</label><br>
									</div>
								</div>
								<div class="col-lg-3">
									<div class="form-group">
									</br></br>
										<input type="file" name="photo" class="form-control" id="changephoto" required/>	
									</div>
								</div>
								<div class="col-lg-3 " >
								<div class="form-group">
									</br></br>
									<input type="hidden" name="email" value="<%= email%>" />
									<button type="submit" class="btn btn-primary">Submit</button>
								</div>	
								</div>
								</form>
							</div>
						</div>
						<hr>
						<div class="container">
							<%
								DAO db=new DAO();
								HashMap<String,String> user=db.getUserDetails(email);
								db.closeConnection();
							%>
							<form action="UpdateProfile" method='post' data-toggle="validator" class="form-horizontal">
								<div class="form-group">
									<label for="email" class="col-lg-2 control-label">Email:</label>
									<div class="col-lg-5">
                                                                            <label class="form-control" id="email" ><%= email %></label>
									</div>
								</div><!--end form group-->
								<div class="form-group">
									<label for="phone" class="col-lg-2 control-label">Phone:</label>
									<div class="col-lg-5">
										<input type="text" name='phone' value="<%= user.get("phone")%>" class="form-control" pattern="^[_0-9]{1,}$" maxlength="10" minlength="10" id="phone" placeholder="<%= user.get("phone")%>"  required/>
									</div>
								</div><!--end form group-->
								<div class="form-group">
									<label for="name" class="col-lg-2 control-label">Name:</label>
									<div class="col-lg-5">
										<input type="text" class="form-control" id="name" name="name" value="<%= user.get("name")%>" pattern="^[_A-Z a-z]{1,}$"  placeholder="<%= user.get("name")%>" required/>
									</div>
								</div><!--end form group-->
								<div class="form-group">
									<label for="gender" class="col-lg-2 control-label">Gender:</label>
									<div class="col-lg-5"> 
										<% 
										String gen=user.get("gender"); 
										if(gen.equalsIgnoreCase("male")){
											%>
											<input type="radio" id="gender"name="gender" value="male" checked/>Male
											<input type="radio" id="gender"name="gender" value="female"/>Female
											<%	
										}else{
											%>
											<input type="radio" id="gender"name="gender" value="male" />Male
											<input type="radio" id="gender"name="gender" value="female" checked/>Female
											<%		
										}
										%>
										
									</div>
								</div><!--end form group-->
								<div class="form-group">
									<label for="dob" class="col-lg-2 control-label">Date of Birth:</label>
									<div class="col-lg-5">
									<input type="text" name="dob" class="form-control" id="dob" placeholder="<%= user.get("dob")%>" required />
									</div>
								</div><!--end form group-->
								<div class="form-group">
									<label for="state" class="col-lg-2 control-label">State:</label>
									<div class="col-lg-5">
										<label><%= user.get("state")%> </label>
										<select class="form-control" name="state"  class="form-control" id="listBox" onchange='selct_district(this.value)'>
											
										</select>
									</div>
								</div><!--end form group-->
								<div class="form-group">
									<label for="city" class="col-lg-2 control-label">City:</label>
									<div class="col-lg-5">
										<label><%= user.get("city")%> </label>
										<select class="form-control" id='secondlist' name="city" required>
											
										</select>
									</div>
								</div><!--end form group-->
								<div class="form-group">
									<label for="area" class="col-lg-2 control-label">Area:</label>
									<div class="col-lg-5">
										<input type="text" class="form-control" id="area" name="area" value="<%= user.get("area")%>"  placeholder="<%= user.get("area")%>"  required/>
									</div>
								</div><!--end form group-->
								<div class="form-group">
									<div class="col-lg-10 col-lg-offset-2">
										<button type="submit" class="btn btn-primary">Update Profile</button>
									</div>
								</div>
							</form>		
						</div>
					</div>							
				</div>		
		</section>
	</div>
	<!--footer-->
	
	<div class="navbar navbar-inverse navbar-fixed-bottom">
		<div class="container">
			<div class="navbar-text pull-left">
				<p>Design and Develop by INCAPP</p>
			</div>
		</div>
	</div>
   <script type="text/javascript" src="js/jquery-2.2.2.min.js" ></script>
    <script type="text/javascript" src="js/bootstrap.min.js" ></script>
	<script type="text/javascript" src="js/script.js" ></script>
  <script type="text/javascript" src="js/validator.js" ></script>
<script type="text/javaScript" src='js/state.js' ></script>
<script type="text/javascript" src="datetimepicker/js/moment.min.js" ></script>
<script type="text/javascript" src="datetimepicker/js/datetimepicker.min.js" ></script>
	<script type="text/javascript">
    $(function () {
        $('#dob').datetimepicker({
        	format: 'DD/MM/YYYY',
                maxDate: new Date()
        });
    });
	</script>
  </body>
</html>
<%	
}
%>