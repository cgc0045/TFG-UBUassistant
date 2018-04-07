<%
boolean logged=false;

if(session.getAttribute("logged")!=null){
	logged=(boolean)session.getAttribute("logged");
}

if(!logged){	
	response.sendRedirect("adminLogin.jsp");
}%>