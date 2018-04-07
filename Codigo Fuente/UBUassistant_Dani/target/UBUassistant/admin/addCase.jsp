<%@page contentType="text/html" errorPage="../error.jsp" %>
<%@ page import="database.DatabaseAdministration" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<html>
	<head>
		<title>UBUassistant Administration</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<link rel="stylesheet" href="../css/admin/main.css" />
		
		<script src="../js/jquery.js"></script>
		<script src="../js/admin.js"></script>
		
		<script type="text/javascript">
		window.onload = function() {
			  document.getElementById('add').className = 'active';
			};
			
			//Navbar Scroll Event
			var lastScrollTop = 0;
			var navbar        = $('.navbar');
			$(window).scroll(function(event){
			   var st = $(this).scrollTop();
			   if (st > lastScrollTop){
			       navbar.addClass('navbar-scroll-custom');
			   } else {
			      navbar.removeClass('navbar-scroll-custom');
			   }
			   lastScrollTop = st;
			});
		
		</script>
		
		<%@ include file="checkSession.jsp" %>
		
	</head>
	<body>
	
		<%
		
		List<String> lista = new ArrayList<String>();
		String categoria="";
		String respuesta="";
		
		if(request.getParameter("keyWord1")!=null){
			if(!request.getParameter("keyWord1").equals("")){
				lista.add(request.getParameter("keyWord1"));
			}
		}
		if(request.getParameter("keyWord2")!=null){
			if(!request.getParameter("keyWord2").equals("")){
				lista.add(request.getParameter("keyWord2"));
			}
		}
		if(request.getParameter("keyWord3")!=null){
			if(!request.getParameter("keyWord3").equals("")){
				lista.add(request.getParameter("keyWord3"));
			}
		}
		if(request.getParameter("keyWord4")!=null){
			if(!request.getParameter("keyWord4").equals("")){
				lista.add(request.getParameter("keyWord4"));
			}
		}
		if(request.getParameter("keyWord5")!=null){
			if(!request.getParameter("keyWord5").equals("")){
				lista.add(request.getParameter("keyWord5"));
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
		
		
		if(lista.size()>0 && !categoria.equals("") && !respuesta.equals("")){
			DatabaseAdministration dba = new DatabaseAdministration();
			dba.addCase(lista,categoria, respuesta);
		}
		
		
		%>
	
		<%@ include file="header.html" %>
		
		<script type="text/javascript">
			document.getElementById("title").innerHTML="Añadir caso";
			document.getElementById("subtitle").innerHTML="Formulario para añadir un caso a la base de datos.";
		</script>
		

		<div id="content" class="content">
		
		
			<form id="addForm" class="form-horizontal" action="addCase.jsp;jsessionid=<%=session.getId()%>" method="POST" onsubmit="return checkInput()">
		    
				<div class="form-group">
					<label class="control-label col-sm-2" for="keyWord1">Palabra Clave 1:</label>
				    <div class="col-sm-10">
				    	<input id="keyWord1" class="form-control" type="text" name="keyWord1">
				    </div>
		    	</div>
		    	
		    	
		    	<div class="form-group">
					<label class="control-label col-sm-2" for="keyWord2">Palabra Clave 2:</label>
				    <div class="col-sm-10">
				    	<input id="keyWord2" class="form-control" type="text" name="keyWord2">
				    </div>
		    	</div>
		    	
		    	<div class="form-group">
					<label class="control-label col-sm-2" for="keyWord3">Palabra Clave 3:</label>
				    <div class="col-sm-10">
				    	<input id="keyWord3" class="form-control" type="text" name="keyWord3">
				    </div>
		    	</div>
		    	
		    	<div class="form-group">
					<label class="control-label col-sm-2" for="keyWord4">Palabra Clave 4:</label>
				    <div class="col-sm-10">
				    	<input id="keyWord4" class="form-control" type="text" name="keyWord4">
				    </div>
		    	</div>
		    	
		    	<div class="form-group">
					<label class="control-label col-sm-2" for="keyWord5">Palabra Clave 5:</label>
				    <div class="col-sm-10">
				    	<input id="keyWord5" class="form-control" type="text" name="keyWord5">
				    </div>
		    	</div>
		    	
		    	<div class="form-group">
					<label class="control-label col-sm-2" for="categoria">Categor&iacute;a:</label>
				    <div class="col-sm-10">
				    	<input id="categoria" class="form-control" type="text" name="categoria">
				    </div>
		    	</div>
		    	
		    	<div class="form-group">
					<label class="control-label col-sm-2" for="respuesta">Respuesta:</label>
				    <div class="col-sm-10">
				    	<textarea rows="4" cols="50" id="respuesta" class="form-control" name="respuesta"></textarea>
				    </div>
		    	</div>

				<div class="form-group">        
			      <div class="col-sm-offset-2 col-sm-10">
			        <p id="error">
						*Debe rellenar al menos la palabra clave 1, la categor&iacute;a y la respuesta.
					</p>
			      </div>
			    </div>

			    <div class="form-group">        
			      <div class="col-sm-offset-2 col-sm-10">
			        <input class="aceptButton" type="submit" value="Aceptar">
			      </div>
			    </div>
		    
		  </form>
		
			
			
	
		</div>
		

		<%@ include file="footer.html" %>
	
	</body>
	
</html>
