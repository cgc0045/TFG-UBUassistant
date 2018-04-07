
<html>
	<head>
		<title>UBUassistant</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<link rel="stylesheet" href="css/main.css" />
		
		<script src="./js/jquery.js"></script>
		<script src="./js/index.js"></script>


	</head>
	<body>
	

			<%@ include file="header.html" %>
			
			<div class="pringuinoDiv">
				
				<input type="image" class="btn-close-pinguino" src="img/close.png" />
				
				<input type="image" id="pinguino" class="pinguino" src="img/pinguino3.png" />
			</div>
			
			
			<div id="divchat-window" class="divchat-window" style="display:none;">
			
				<div id="divchat-button" class="divchat-button">
					<span class="text">UBUassistant</span>
					<input type="image" id="btn-minimize" class="btn-minimize" src="./img/min.png" />
					<input type="image" class="btn-close" src="img/close.png" />
				</div>
				
				   
				<div id="divchat" class="divchat">
					
				    <iframe id="ubuassistantFrame" class="iframe" src="./UBUassistant/hellouser.jsp"></iframe>
				</div>

			</div>
			
			<div class="admin"> 
				<a href="./admin/adminLogin.jsp;jsessionid=<%=session.getId()%>">
					<input type="image" class="adminLink" src="img/admin.png" />
				</a>
			</div>
			
			
			<div class="content">
			<p>
			<br><br><br><br><br><br><br>
			</p>
			
			</div>
			

			<%@ include file="footer.html" %>
	</body>
</html>
