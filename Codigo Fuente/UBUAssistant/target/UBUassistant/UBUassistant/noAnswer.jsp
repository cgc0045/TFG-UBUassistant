<%@page contentType="text/html" errorPage="../error.jsp" %>
<%@page import="handler.UBUassistantHandler"%>
<%@page import="java.util.LinkedHashSet"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="jcolibri.method.retrieve.RetrievalResult"%>
<%@page import="representation.CaseSolution"%>
<%@ page import="storage.Storage" %>
			

<html>

	<head>
		<title>UBUassistant</title>
		<link rel="stylesheet" href="../css/ubuassistant/normalize.css">
		<link rel="stylesheet" href="../css/ubuassistant/style.css">
		
		<script>
		
		window.onload = function() {
			var objDiv = document.getElementById("chat-output");
			objDiv.scrollTop = objDiv.scrollHeight;
		};
		
		
		</script>
	</head>

	<body>


		<% 	UBUassistantHandler ubuassistant= (UBUassistantHandler) session.getAttribute("ubuassistantHandler");
			String userText = request.getParameter("usertText");  
		   	String answer = request.getParameter("answer");
		   	String numString = request.getParameter("num");
		   	int num = Integer.parseInt(numString);

			String printText=userText.substring(0, 1).toUpperCase() + userText.substring(1).toLowerCase();
			String printAnswer=null;
			if(answer.contains("http")){
				printAnswer="<p>"+ubuassistant.getRandomSentence()+"<p>"+"<a href="+answer+" target=\"_blank\">"+answer+"</a>";
			}else{
				printAnswer="<p>"+ubuassistant.getRandomSentence()+"<p>"+answer;
			}
			
			Storage storage = ubuassistant.getStorage();
			
			storage.setChatOutput("bot-message", printText);
			storage.setChatOutput("user-message", printAnswer);
			
			LinkedHashSet<String> words = new LinkedHashSet<String>();
			words.add(userText);
			ubuassistant.getDb().aumentarNumBusquedas(words, answer);
			
		    LinkedHashSet<String> suggestWord = new LinkedHashSet<String>();
			Map<String, List<RetrievalResult>> badResuts=ubuassistant.getBadResuts();
			List<RetrievalResult> listOfValues = ubuassistant.getListOfValues();
			
			for (String o : badResuts.keySet()) {
				if (badResuts.get(o).contains(listOfValues.get(num))) {
					suggestWord.add(o);
				}		
			}

			//When there are no answers and the user push a suggestion button it is supposed
			//That the text input by the user is related with the button so we store all this 
			//information for making the system learn
			ubuassistant.getDb().learnCases(suggestWord.iterator().next(), ((CaseSolution)listOfValues.get(num).get_case().getSolution()).getAnswer().toString());
			%>
		
		<script>
		function getVoteAndSubmit(param){
			
			var vote=param.value;
			document.getElementById("vote").value=vote;
			document.getElementById("wordButton").value="<%=userText%>";
			
			param.form.submit()
		}
		
		</script>
		
		<div class="chat-output" id="chat-output">
		
			<%= storage.getChatOutput() %>	
		
		</div>
		
		<% String starBar = ubuassistant.getStarBarButton();
		if(starBar!=null){%>
			<div id="buttonPanelContent" class="buttonPanelContent">
				<div id="buttonPanel" class="buttonPanel">		
					  	<%=starBar %>
			  	</div>
			</div>
		<%} %>
		
		<%@ include file="form.html" %>
		
	</body>
</html>
















