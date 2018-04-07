<%@page contentType="text/html" errorPage="../error.jsp" %>
<%@ page import="handler.UBUassistantHandler" %>
<%@ page import="storage.Storage" %>
<%@ page import="java.text.DateFormat" import="java.text.SimpleDateFormat" import="java.util.Date"%>


<html>

	<head>
		<title>UBUassistant</title>
		<link rel="stylesheet" href="../css/ubuassistant/normalize.css">
		<link rel="stylesheet" href="../css/ubuassistant/style.css">
		
	</head>

	<body>	
	
		<%
		DateFormat formatForId = new SimpleDateFormat("yyMMddHHmmssSSS");
			
		String userID=formatForId.format(new Date()); 
		
		UBUassistantHandler handler = new UBUassistantHandler(userID);
		
		handler.setSessionId(session.getId());
		
		session.setAttribute("ubuassistantHandler", handler); 
				
		UBUassistantHandler ubuassistant= (UBUassistantHandler) session.getAttribute("ubuassistantHandler");
		
		
		
		String salute=ubuassistant.getSalute();	
		Storage storage = ubuassistant.getStorage();
		if(storage.getChatOutput().length()==0)
			storage.setChatOutput("user-message",salute);
		%>

		<div class="chat-output" id="chat-output">
			<%=storage.getChatOutput() %>
		</div>
		
		<%@ include file="form.html" %>
			  
	</body>
	
</html>