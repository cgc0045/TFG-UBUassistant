package UBUassistant.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;

import handler.UBUassistantHandler;

@Path("/service")
public class ResponseUser {
	
	@XmlRootElement
	private class Respuesta{
		private String message;
		private List<List<String>> responses;
//		private String responses;
		private Respuesta() {}
		
		private Respuesta(String message, List<List<String>> responses) {
			this.message = message;
			this.responses = responses;
		}
		
		public String getMessage(){
			return message;
		}
		
		public List<List<String>> getResponses(){
			return responses;
		}
	}

	@GET
	@Path("/{question}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getResponse(@PathParam("question") String userText, @Context HttpServletRequest request) {
		
		Logger log = Logger.getLogger(getClass());
		
		HttpSession session = request.getSession();
		
		DateFormat formatForId = new SimpleDateFormat("yyMMddHHmmssSSS");
		
		String userID=formatForId.format(new Date()); 
		
		UBUassistantHandler handler = new UBUassistantHandler(userID);
		
		handler.setSessionId(session.getId());
		
		session.setAttribute("ubuassistantHandler", handler); 
				
		UBUassistantHandler ubuassistant= (UBUassistantHandler) session.getAttribute("ubuassistantHandler");
		
		List<List<String>> response = new ArrayList<>();
//		String response = new String();
		
		session = request.getSession();
		
		ubuassistant= (UBUassistantHandler) session.getAttribute("ubuassistantHandler");
		
		String answer = null;
		if(userText.length()>0){
			//printText=userText.substring(0, 1).toUpperCase() + userText.substring(1).toLowerCase();
			ubuassistant.setUsertText(userText);
			answer = ubuassistant.getResponse();
		}
		
		String message = answer;
		
		int status = 0;
		
		List<List<String>> suggestButtons=ubuassistant.getSuggestButtons();
		
		if(suggestButtons != null && suggestButtons.size() > 0){
		
			LinkedHashSet<String> temp = new LinkedHashSet<String>();
		  	temp.add(userText);
		  	ubuassistant.getDb().aumentarNumBusquedas(temp, null);
		  	
		  	response.addAll(suggestButtons);
		  	status=201;
//		  	for (String s: suggestButtons) {
//		  		response+=s;
//		  	}
		}
		
		List<List<String>> multipleButtons=ubuassistant.getMultipleButtons();
		if(multipleButtons != null && multipleButtons.size() > 0){
			response.addAll(multipleButtons);
			status=202;
//			for (String s: multipleButtons) {
//		  		response+=s;
//		  	}
		}
		
		if(response.size() == 0 && !ubuassistant.answerReservedWord(userText)) {
			/*List<List<String>> mes = new ArrayList<>();
			List<String> cont = new ArrayList<>();
			cont.add(message);
			mes.add(cont);
			response.add(cont);*/
//			response = message;
			response.add(ubuassistant.getButton());
			message = ubuassistant.getRandomSentence();
			status=200;
		}else if (ubuassistant.answerReservedWord(userText)){
			status=203;
		}
		
		Respuesta respuesta = new Respuesta(message, response);
				
		log.info(respuesta.getMessage());
		
		log.info(respuesta.getResponses());
		
		return Response.status(status).entity(respuesta).build();
	}
}
