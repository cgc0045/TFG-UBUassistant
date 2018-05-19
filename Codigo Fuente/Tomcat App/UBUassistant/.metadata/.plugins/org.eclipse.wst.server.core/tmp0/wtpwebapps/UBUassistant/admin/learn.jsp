<%@page contentType="text/html" errorPage="../error.jsp" %>
<%@ page import="com.mysql.jdbc.jdbc2.optional.MysqlDataSource" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="database.DatabaseAdministration" %>


<html>
	<head>
		<title>UBUassistant Administration</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<link rel="stylesheet" href="../css/admin/main.css" />
		<link rel="stylesheet" href="../css/admin/resizeTable.css" />
		
		<script src="../js/admin.js"></script>
		<script src="../js/jquery.js"></script>
		<script src="../js/sorttable.js"></script>
		
		<script src="../js/tableExport/tableExport.js"></script>
		<script src="../js/tableExport/jquery.base64.js"></script>
		<script src="../js/tableExport/html2canvas.js"></script>
		<script src="../js/tableExport/jspdf/libs/sprintf.js"></script>
		<script src="../js/tableExport/jspdf/jspdf.js"></script>
		<script src="../js/tableExport/jspdf/libs/base64.js"></script>
		
		<script type="text/javascript">
		window.onload = function() {
			  document.getElementById('learn').className = 'active';
			};
		</script>
		
		<script>
			$(document).ready(function(){
			   $(".saveas").click(function(){
			    $("#saveButtons").toggle( "slide" );
			  });
			});
		</script>
		
		<%@ include file="checkSession.jsp" %>
		
	</head>
	<body>
	
		<%
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
		%> 
		<h3>Error al conectar con la base de datos.</h3> 
		
		<%
		 }
		
		if(request.getParameter("palabra")!=null && request.getParameter("respuesta")!=null && request.getParameter("accion")!=null){
			
			String palabra=request.getParameter("palabra");
			String respuesta=request.getParameter("respuesta");
			String accion=request.getParameter("accion");
			DatabaseAdministration dba = new DatabaseAdministration();
			
			if(accion.equals("descartar")){
				dba.ignoreLearn(palabra, respuesta);
			}
			
			if(accion.equals("aprender")){
				dba.executeLearn(palabra, respuesta);
			}
		}
		%>
	
		<%@ include file="header.html" %>
		
		<script type="text/javascript">
			document.getElementById("title").innerHTML="Aprendizaje";
			document.getElementById("subtitle").innerHTML="Tabla con las recomendaciones obtenidas por el uso de la aplicación.";
		</script>
		
		<div id="content" class="content">
		
		<%@ include file="saveMenu.html" %>
	
		<%
		//Tabla aprendizaje
		
		if(con!=null){
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM aprendizaje"); %>
		
		<br>
		<div class="divtable">
			<table id="tabla" class="sortable">
			<thead>
			  <tr><th>ID Usuario</th><th>Palabra sugerida</th><th>Respuesta sugerida</th><th>Acción</th></tr>
			</thead>
			<tbody>
		<%
			while (rs.next()) {
			%>
			  <tr>
				  <td><%=rs.getString("userid") %></td>
				  <td><%=rs.getString("palabra1") %></td>
				  <td><%=rs.getString("palabra2") %></td>
				  <td>
				  	<form method="post" action="learn.jsp;jsessionid=<%=session.getId()%>" class="actionform">
				  		<input type="hidden" name="palabra" value="<%=rs.getString("palabra1") %>">
				  		<input type="hidden" name="respuesta" value="<%=rs.getString("palabra2") %>">
				  		<input type="hidden" name="accion" value="aprender">
				  		<input type="submit" id="button" class="aprender" value="Aprender">
				  	</form>
				  	<form method="post" action="learn.jsp;jsessionid=<%=session.getId()%>" class="actionform">
				  		<input type="hidden" name="palabra" value="<%=rs.getString("palabra1") %>">
				  		<input type="hidden" name="respuesta" value="<%=rs.getString("palabra2") %>">
				  		<input type="hidden" name="accion" value="descartar">
				  		<input type="submit" id="button" class="descartar" value="Descartar">
				  	</form>
				  </td>
			  </tr>
			<%
			}
		}
		%>
			</tbody>
			</table>
			
			

		</div>
		
		<br>

	</div>
	
	<script src="../js/resizeTable.js"></script>
	<%@ include file="footer.html" %>
	</body>
</html>
