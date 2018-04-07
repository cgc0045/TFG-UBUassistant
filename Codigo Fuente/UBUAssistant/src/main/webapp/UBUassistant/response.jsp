<%@page contentType="text/html" errorPage="../error.jsp" %>
<%@page import="handler.UBUassistantHandler"%>
<%@page import="java.util.LinkedHashSet"%>
<%@ page import="storage.Storage" %>
			

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
		
		function getVoteAndSubmit(param){
			
			var vote=param.value;
			document.getElementById("vote").value=vote;
			
			param.form.submit()
		}
		
		
		</script>
	</head>

	<body>

		<% 	
			UBUassistantHandler ubuassistant= (UBUassistantHandler) session.getAttribute("ubuassistantHandler");
			String userText = request.getParameter("usertText"); 
		%>
		
		<% 	String answer = null;
			String printText=null;
			if(userText.length()>0){
				printText=userText.substring(0, 1).toUpperCase() + userText.substring(1).toLowerCase();
				ubuassistant.setUsertText(userText);
				answer = ubuassistant.getResponse();
			}
			
			Storage storage = ubuassistant.getStorage();
			
			storage.setChatOutput("bot-message",printText);
			storage.setChatOutput("user-message",answer);
			
		%>
		
		<div class="chat-output" id="chat-output">
		
			<%=storage.getChatOutput() %>
		</div>
		

		<%String suggestButtons=ubuassistant.getSuggestButtons();
		if(suggestButtons!=null){%>
		
		<div id="buttonPanelContent" class="buttonPanelContent" >
			
			<div id="buttonPanel" class="buttonPanel" style="margin-bottom: -15px">
			  	<%=suggestButtons %>
			  	<% LinkedHashSet<String> temp = new LinkedHashSet<String>();
			  	temp.add(userText);
			  	ubuassistant.getDb().aumentarNumBusquedas(temp, null); %>
		  	</div>
		  	
		 </div>
		<%} %>
		
		<%String multipleButtons=ubuassistant.getMultipleButtons();
		if(multipleButtons!=null){%>
			
		<div id="buttonPanelContent" class="buttonPanelContent">
			<div id="buttonPanel" class="buttonPanel">
				
			  	<%=multipleButtons %>
			
		  	</div>
		  	
		</div>
		<%} %>
		
		<% String starBar = ubuassistant.getStarBar();
		if(starBar!=null){%>
		
		<div id="buttonPanelContent" class="buttonPanelContent">
			<div id="buttonPanel" class="buttonPanel">		
				  	<%=starBar %>
		  	</div>
		  	
		</div>
		<%} %>
		
		
		<%@ include file="form.html" %>
		
		
		<script>
		
		var buttonDiv = document.getElementById("buttonPanel").innerHTML;
		var num = document.getElementsByName("buttonDiv").length;
		var x = document.getElementsByName("buttonDiv")
		for (i=0; i < num; i++) {
			x[i].value=buttonDiv;
		}
		
		</script>
		
	</body>
</html>