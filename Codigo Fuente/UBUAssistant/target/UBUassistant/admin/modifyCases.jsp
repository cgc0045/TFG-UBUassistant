<%@page contentType="text/html" errorPage="../error.jsp" %>
<%@ page import="com.mysql.jdbc.jdbc2.optional.MysqlDataSource" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="database.DatabaseAdministration" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

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

		<script>
			$(document).ready(function(){
			   $(".saveas").click(function(){
			    $("#saveButtons").toggle( "slide" );
			  });
			});
		</script>
		
		<script type="text/javascript">
		window.onload = function() {
			  document.getElementById('edit').className = 'active';
			};
			
		function confirmDelete(){
				 
				var r = confirm("¿Está seguro de que desea borrar el caso?");
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
		
		if(request.getParameter("edit")!=null){
			
			String id="";
			String[] keyWords = new String[5];
			String categoria="";
			String respuesta="";
			
			if(request.getParameter("id")!=null){
				id=request.getParameter("id");
			}
			if(request.getParameter("keyWord1")!=null){
				if(!request.getParameter("keyWord1").equals("")){
					keyWords[0]=request.getParameter("keyWord1");
				}
			}
			if(request.getParameter("keyWord2")!=null){
				if(!request.getParameter("keyWord2").equals("")){
					keyWords[1]=request.getParameter("keyWord2");
				}
			}
			if(request.getParameter("keyWord3")!=null){
				if(!request.getParameter("keyWord3").equals("")){
					keyWords[2]=request.getParameter("keyWord3");
				}
			}
			if(request.getParameter("keyWord4")!=null){
				if(!request.getParameter("keyWord4").equals("")){
					keyWords[3]=request.getParameter("keyWord4");
				}
			}
			if(request.getParameter("keyWord5")!=null){
				if(!request.getParameter("keyWord5").equals("")){
					keyWords[4]=request.getParameter("keyWord5");
				}
			}
			if(request.getParameter("categoria")!=null){
				if(!request.getParameter("categoria").equals("")){
					categoria=request.getParameter("categoria");
				}
			}
			if(request.getParameter("respuesta")!=null){
				if(!request.getParameter("respuesta").equals("")){
					respuesta=request.getParameter("respuesta");
				}
			}
			
			DatabaseAdministration dba = new DatabaseAdministration();
			dba.editCase(id, keyWords, categoria, respuesta);
			
		}else if(request.getParameter("id")!=null){
			
			String id=request.getParameter("id");
			
			DatabaseAdministration dba = new DatabaseAdministration();
			
			dba.removeCase(id);
		}
		
		%>
	
		<%@ include file="header.html" %>
		
		<script type="text/javascript">
			document.getElementById("title").innerHTML="Modificar casos";
			document.getElementById("subtitle").innerHTML="Tabla que muestra todos los casos y permite eleminarlos o editarlos.";
		</script>
		
		<div id="content" class="content">
		
		<div class="saveMenu">
		
			<button class='saveas'><span>Guardar como</span></button>
			
			<ul id='saveButtons' class="copyright" style="display: none;">
			
			    <li><a onclick="$('#tabla').tableExport({type:'json',escape:'false'});">JSON</a></li>
			    <li><a onclick="$('#tabla').tableExport({type:'xml',escape:'false'});">XML</a></li>
			    <li><a onclick="$('#tabla').tableExport({type:'sql'});">SQL</a></li>
			    <li><a onclick="$('#tabla').tableExport({type:'csv',escape:'false'});">CSV</a></li>
			    <li><a onclick="$('#tabla').tableExport({type:'txt',escape:'false'});">TXT</a></li>
			    <li><a onclick="$('#tabla').tableExport({type:'excel',escape:'false'});">XLS</a></li>
			    <li><a onclick="$('#tabla').tableExport({type:'doc',escape:'false'});">Word</a></li>
			 </ul>
			 
			 <div class="dropdown">
			    <button class="btn2 dropdown-toggle" type="button" data-toggle="dropdown">Guardar como
			    <span class="caret"></span></button>
			    <ul class="color dropdown-menu">
			      <li><a onclick="$('#tabla').tableExport({type:'json',escape:'false'});">JSON</a></li>
				    <li><a onclick="$('#tabla').tableExport({type:'xml',escape:'false'});">XML</a></li>
				    <li><a onclick="$('#tabla').tableExport({type:'sql'});">SQL</a></li>
				    <li><a onclick="$('#tabla').tableExport({type:'csv',escape:'false'});">CSV</a></li>
				    <li><a onclick="$('#tabla').tableExport({type:'txt',escape:'false'});">TXT</a></li>
				    <li><a onclick="$('#tabla').tableExport({type:'excel',escape:'false'});">XLS</a></li>
				    <li><a onclick="$('#tabla').tableExport({type:'doc',escape:'false'});">Word</a></li>
			    </ul>
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
			ResultSet rs = stmt.executeQuery("SELECT * FROM casedescription");
			Statement stmt2 = con.createStatement();
			ResultSet rs1 = stmt2.executeQuery("SELECT * FROM casesolution");
		%>
		
		
		
		<div class="divtable">
			<table id="tabla" class="sortable" >
			<thead>
			  <tr><th>ID</th><th>Palabra clave 1</th><th>Palabra clave 2</th><th>Palabra clave 3</th>
			  <th>Palabra clave 4</th><th>Palabra clave 5</th><th>Categoria</th><th>Respuesta</th><th>Acci&oacute;n</th></tr>
			</thead>
			<tbody>
		<%
			while (rs.next() && rs1.next()) {
			%>
			  <tr><td><%=rs.getInt("id") %></td><td><%=rs.getString("keyWord1") %></td><td><%=rs.getString("keyWord2") %></td>
			  <td><%=rs.getString("keyWord3") %></td><td><%=rs.getString("keyWord4") %></td>
			  <td><%=rs.getString("keyWord5") %></td><td><%=rs.getString("categoria") %></td>
			  <td class="resp"><%=rs1.getString("answer") %></td>
			  
			  <td >
			  	
			  	<form class="modifyForm" action="editCase.jsp;jsessionid=<%=session.getId()%>" method="POST">
			  		<input type="hidden" name="id" value="<%=rs.getInt("id") %>">
			  		<input type="hidden" name="keyWord1" value="<%=rs.getString("keyWord1") %>">
			  		<input type="hidden" name="keyWord2" value="<%=rs.getString("keyWord2") %>">
			  		<input type="hidden" name="keyWord3" value="<%=rs.getString("keyWord3") %>">
			  		<input type="hidden" name="keyWord4" value="<%=rs.getString("keyWord4") %>">
			  		<input type="hidden" name="keyWord5" value="<%=rs.getString("keyWord5") %>">
			  		<input type="hidden" name="categoria" value="<%=rs.getString("categoria") %>">
			  		<input type="hidden" name="answer" value="<%=rs1.getString("answer") %>">
			  		<input type="submit" id="button" class="aprender" value="Editar">
			  	</form>
			  	
			  	<form class="modifyForm" action="modifyCases.jsp;jsessionid=<%=session.getId()%>" method="POST" onsubmit="return confirmDelete();">
			  		<input type="hidden" name="id" value="<%=rs.getInt("id") %>">
			  		<input type="submit" id="button" class="descartar" value="Eliminar">
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
		
	</div>
	
	<script src="../js/resizeTable.js"></script>
	
	<script>
	
		$('td').each(function () {
		    $(this).html($(this).html().replace('null', '-'));
		});
		

	</script>
	
	<%@ include file="footer.html" %>
	
	</body>
</html>
