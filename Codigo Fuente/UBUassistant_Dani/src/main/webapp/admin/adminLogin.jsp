<%@page contentType="text/html" errorPage="../error.jsp" %>
<%@ page import="com.mysql.jdbc.jdbc2.optional.MysqlDataSource" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.sql.Statement" %>


<html>
	<head>
		<title>UBUassistant Administration</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<link rel="stylesheet" href="../css/admin/login.css" />
		<script src="../js/admin.js"></script>
		
		<%
		
		String user=(String)request.getParameter("user");
		String password=(String)request.getParameter("password");
		
		String text="";
		
		if(user!=null && password!=null){
			
			MysqlDataSource ds = new MysqlDataSource();

			ds.setUser("root");
			ds.setPassword("1234");
			ds.setDatabaseName("ubuassistant");
			ds.setURL("jdbc:mysql://localhost/ubuassistant");
			
			Connection con=null;
			try {
				con = ds.getConnection();
			} catch (SQLException e) {
				System.err.println("Error al conectar con la base de datos.");
			}
			
			if(con!=null){
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM administradores");
				
				while(rs.next()){
					if(user.equals(rs.getString("mail")) && password.equals(rs.getString("contrasena"))){
						session.setAttribute("logged", true);
						response.sendRedirect("log.jsp;jsessionid="+session.getId());
						
					}else{
						text="Usuario y/o contraseña no válidos";
					}
				}
			}
		}
		
		%>
		
		
		
		<script src="../js/jquery.js"></script>

		<script>
		$('.message a').click(function(){
			   $('form').animate({height: "toggle", opacity: "toggle"}, "slow");
		});
		</script>
		
		
		
	</head>
	<body>
	
		<div class="login-page">
		  <div class="form">
		    <form class="login-form" method="post" onsubmit="return showErrorIfExists(this)">
		      <input type="text" id="user" name="user"  placeholder="email"/>
		      <input type="password" id="password" name="password" placeholder="contraseña"/>
		      <p id="error" class="error"></p>
		      <button id="ingresar">Ingresar</button>
		    </form>
		    
		    <a href="../index.jsp">
					<button id="volver2" class="volver2">Volver</button>		
				</a>
		  </div>
		 	 
		</div>
		
		
		<script>document.getElementById("error").innerHTML="<%=text%>"</script> 
		
		<a href="../index.jsp">
			<button id="volver" class="volver">Volver</button>		
		</a>

	</body>
</html>
