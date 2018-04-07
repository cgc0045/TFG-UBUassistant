<%@page contentType="text/html" errorPage="../error.jsp" %>
<%@page import="database.DatabaseConnection"%>
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
		
		</script>
		
	</head>
	
	<body>
	
	
	<% 		UBUassistantHandler ubuassistant= (UBUassistantHandler) session.getAttribute("ubuassistantHandler");
			String userText = request.getParameter("userText"); 

		   	String responseQ = request.getParameter("response");
		   	String starBar=ubuassistant.getStarBarButton();
		   	String buttonDiv=(String)session.getAttribute("buttonDiv");
		   
			Storage storage = ubuassistant.getStorage();
		   	
		%>
		
		<script type="text/javascript">
		
		function getVoteAndSubmit(param){
			
			var vote=param.value;
			document.getElementById("vote").value=vote;
			document.getElementById("wordButton").value="<%=userText%>";
			document.getElementById("buttonDiv").value=document.getElementById("buttonPanel").innerHTML;
			
			param.form.submit()
		}
		
		
		</script>
		
		<div class="chat-output" id="chat-output">
			<%= storage.getChatOutput() %>
		</div>
		
		<div id="buttonPanelContent" class="buttonPanelContent">
		
			<div id="buttonPanel" class="buttonPanel">
				  <%if(responseQ.equals("no")){ %>
				  	<%=buttonDiv %>
				  	<%session.removeAttribute("buttonDiv"); %>
				  <%}else{ %>
				  	<%=starBar %>
				  <%} %>
			</div>
			
		</div>
		
		<%@ include file="form.html" %>
		
	</body>
</html>
