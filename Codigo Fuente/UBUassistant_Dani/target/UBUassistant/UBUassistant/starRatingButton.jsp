<%@page contentType="text/html" errorPage="../error.jsp" %>
<%@page import="database.DatabaseConnection"%>
<%@page import="handler.UBUassistantHandler"%>
<%@page import="java.util.LinkedHashSet"%>
<%@page import="storage.Storage" %>
			

<html>

	<head>
		<title>UBUassistant</title>
		<link rel="stylesheet" href="../css/ubuassistant/normalize.css">
		<link rel="stylesheet" href="../css/ubuassistant/style.css">
		<script src="../js/ubuassistant.js"></script>
		<script>
		
		window.onload = function() {
			var objDiv = document.getElementById("chat-output");
			objDiv.scrollTop = objDiv.scrollHeight;
		};
		

		</script>
	</head>

	<body>


		<% 	UBUassistantHandler ubuassistant= (UBUassistantHandler) session.getAttribute("ubuassistantHandler"); 
		   	String vote=request.getParameter("vote");
		   	String wordButton=request.getParameter("wordButton");

		   	DatabaseConnection db = ubuassistant.getDb();
		   	LinkedHashSet<String> words = new LinkedHashSet<String>();
		   	words.add(wordButton.toLowerCase());
		   	db.saveVote(words, Integer.parseInt(vote));
		   	
		   	String buttonDiv=(String)session.getAttribute("buttonDiv");
		   	session.removeAttribute("buttonDiv");
		   	
		   	Storage storage = ubuassistant.getStorage();
		%>
		
		<div class="chat-output" id="chat-output">
		
			<%= storage.getChatOutput() %>
		</div>
		
		<%if(buttonDiv==null){ %>
			<div id="buttonPanelContent" class="buttonPanelContent">
				<div id="buttonPanel" class="buttonPanel">
					  	Voto guardado con éxito. Su voto ha sido <%=vote %>
				</div>
			</div>
		<%}else{ %>
			<div id="buttonPanelContent" class="buttonPanelContent">
			 	<div id="buttonPanel" class="buttonPanel">
			 		<%=buttonDiv %>
			 	</div>
			</div>
		<%} %>
		
		<%@ include file="form.html" %>
		
		<script>document.getElementById("div-content").value=getDivContent();</script>
		
	</body>
</html>
















