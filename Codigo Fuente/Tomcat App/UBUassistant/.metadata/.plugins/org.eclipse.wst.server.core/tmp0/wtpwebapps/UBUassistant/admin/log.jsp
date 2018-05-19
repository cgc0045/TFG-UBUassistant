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
		
		<script src="../js/jquery.js"></script>
		<script src="../js/admin.js"></script>
		<script src="../js/sorttable.js"></script>
		
		
  		<script src="../js/tableExport/tableExport.js"></script>
		<script src="../js/tableExport/jquery.base64.js"></script>
		<script src="../js/tableExport/html2canvas.js"></script>
		<script src="../js/tableExport/jspdf/libs/sprintf.js"></script>
		<script src="../js/tableExport/jspdf/jspdf.js"></script>
		<script src="../js/tableExport/jspdf/libs/base64.js"></script>

		<script>
			$(document).ready(function(){
			   $(".saveas").click(function(){
			    $("#saveButtons").toggle( "slide" );
			  });
			});
		</script>
		
		<script type="text/javascript">
		window.onload = function() {
			  document.getElementById('log').className = 'active';
			};
			
		function ask(){
			var r = confirm("¿Está seguro de que desea borrar la tabla de logs?");
		    if (r == true) {
		        return true;
		    } else {
		        return false;
		    }
		}

		</script>
		
		<%@ include file="checkSession.jsp" %>
		
	</head>
	<body>
	
		<%
		
		if(request.getParameter("borrar")!=null){
			DatabaseAdministration dba = new DatabaseAdministration();
			dba.clearLog();
		}
		%>
	
		<%@ include file="header.html" %>
		
		<script type="text/javascript">
			document.getElementById("title").innerHTML="Log de uso";
			document.getElementById("subtitle").innerHTML="Log de uso de la apicación con las busquedas y su valoración por usuario.";
		</script>
		
		<div id="content" class="content">
		
		<div class="buttonsDiv">
			<%@ include file="saveMenu.html" %>
			<div class="saveMenu">
				<form action="log.jsp;jsessionid=<%=session.getId()%>" method="POST" onsubmit="return ask()" class="formClear">
					<input type="hidden" name="borrar" value="borrar">
					<input type="submit" class='clean' value="Limpiar tabla">
				</form>
				
			</div>
		</div>	
		
	
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
		
		<% }%>
		
		
		<br>
		
		<% 
		//Tabla estadisticas
		
		if(con!=null){
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM logger");
		%>
		
		
		
		<div class="divtable">
			<table id="tabla" class="sortable">
			<thead>
			  <tr><th>ID Usuario</th><th>Fecha</th><th>Palabra clave 1</th>
			  <th>Palabra clave 2</th><th>Palabra clave 3</th><th>Palabra clave 4</th>
			  <th>Palabra clave 5</th><th>Categoria</th><th>Respuesta</th><th>Numero de busquedas</th>
			  <th>Numero de votos</th><th>Valoracion total</th></tr>
			</thead>
			<tbody>
		<%
			while (rs.next()) {
			%>
			  <tr><td><%=rs.getString("userid")  %></td><td><%=rs.getString("fecha") %></td><td><%=rs.getString("keyWord1") %></td>
			  <td><%=rs.getString("keyWord2") %></td><td><%=rs.getString("keyWord3") %></td><td><%=rs.getString("keyWord4") %></td>
			  <td><%=rs.getString("keyWord5") %></td><td><%=rs.getString("categoria") %></td><td class="resp"><%=rs.getString("respuesta") %></td>
			  <td><%=rs.getString("num_busquedas") %></td><td><%=rs.getString("num_votos") %></td><td><%=rs.getString("valoracion_total") %></td></tr>
			<%
			}
		}
		%>
			</tbody>
			</table>
		
		</div>
		
	</div>
	
	<script>
		$('td').each(function () {
		    $(this).html($(this).html().replace('null', '-'));
		});
	</script>
	
	<script src="../js/resizeTable.js"></script>
	
	<%@ include file="footer.html" %>
	
	</body>
</html>
