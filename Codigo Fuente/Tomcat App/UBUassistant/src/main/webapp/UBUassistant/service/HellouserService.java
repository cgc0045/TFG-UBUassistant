package UBUassistant.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import handler.UBUassistantHandler;

@Path("/service")
public class HellouserService {

	@GET
	public Response sayHello(@Context HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		DateFormat formatForId = new SimpleDateFormat("yyMMddHHmmssSSS");
		
		String userID=formatForId.format(new Date()); 
		
		UBUassistantHandler handler = new UBUassistantHandler(userID);
		
		handler.setSessionId(session.getId());
		
		session.setAttribute("ubuassistantHandler", handler); 
				
		UBUassistantHandler ubuassistant= (UBUassistantHandler) session.getAttribute("ubuassistantHandler");
		
		String salute=ubuassistant.getSalute();
		
		return Response.status(200).entity(salute).build();
	}
	
	
}
