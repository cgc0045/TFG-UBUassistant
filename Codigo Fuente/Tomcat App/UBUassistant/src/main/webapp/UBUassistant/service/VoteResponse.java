package UBUassistant.service;


import java.util.LinkedHashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import database.DatabaseConnection;
import handler.UBUassistantHandler;
/**
 * 
 * @author Carlos González Calatrava
 *
 */
@Path("/post")
public class VoteResponse {

	@XmlRootElement
	public class JSONConsume {
	    private String hello;
	    
	    public JSONConsume(String hello) {
	    	this.hello = hello;
	    }
	    
	    public String getHello() {
	    	return hello;
	    }
	}
	
	/**
	 * Method used to learn new cases send it by the client.
	 * @param request Http connection request.
	 * @param input JSON file to consume.
	 * @return State code
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/learn")
	public Response learn(@Context HttpServletRequest request, String input) {
		Logger log = Logger.getLogger(getClass());
		JSONObject json = new JSONObject(input);
		
		log.info("Recibido el POST");
		log.info(input);
		
		HttpSession session = request.getSession();	
		String id=json.get("userID").toString();
		UBUassistantHandler handler = new UBUassistantHandler(id);		
		handler.setSessionId(session.getId());		
		session.setAttribute("ubuassistantHandler", handler); 				
		UBUassistantHandler ubuassistant= (UBUassistantHandler) session.getAttribute("ubuassistantHandler");
		
		DatabaseConnection db = ubuassistant.getDb();
		
		
		String p1 = json.get("p1").toString();
		String p2 = json.get("p2").toString();
		
		db.learnCases(id, p1, p2);
		
		return Response.status(201).build();
	}
	
	/**
	 * Method used to store in log one response opened by the user
	 * @param request Http connection request.
	 * @param input JSON file to consume.
	 * @return Status code.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/log")
	public Response log(@Context HttpServletRequest request, String input) {
		Logger log = Logger.getLogger(getClass());
		JSONObject json = new JSONObject(input);
		
		log.info("Recibido el POST");
		log.info(input);
		
		HttpSession session = request.getSession();	
		String userID=json.getJSONArray("userID").getString(0);
		UBUassistantHandler handler = new UBUassistantHandler(userID);		
		handler.setSessionId(session.getId());		
		session.setAttribute("ubuassistantHandler", handler); 				
		UBUassistantHandler ubuassistant= (UBUassistantHandler) session.getAttribute("ubuassistantHandler");
		
		DatabaseConnection db = ubuassistant.getDb();
		LinkedHashSet<String> words = new LinkedHashSet<String>();
		
		for(Object o: json.getJSONArray("palabras")){
			words.add(o.toString());
		}
		
		db.aumentarNumBusquedas(words, json.getJSONArray("respuesta").getString(0));
		
		return Response.status(201).build();
	}
	
	/**
	 * Method used to store a vote emitted by a user.
	 * @param request Http connection request.
	 * @param input JSON file to consume.
	 * @return Status code.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/vote")
	public Response vote(@Context HttpServletRequest request, String input) {
		Logger log = Logger.getLogger(getClass());
		
		log.info("Recibido el POST");
		log.info(input);
		
		JSONObject json = new JSONObject(input);
		
		HttpSession session = request.getSession();	
		String userID=json.getJSONArray("userID").getString(0);
		UBUassistantHandler handler = new UBUassistantHandler(userID);		
		handler.setSessionId(session.getId());		
		session.setAttribute("ubuassistantHandler", handler); 				
		UBUassistantHandler ubuassistant= (UBUassistantHandler) session.getAttribute("ubuassistantHandler");
		
		log.info(ubuassistant.toString());
		
		DatabaseConnection db = ubuassistant.getDb();
		LinkedHashSet<String> words = new LinkedHashSet<String>();
		
		
		for(Object o: json.getJSONArray("palabras")){
			words.add(o.toString());
		}
		
		log.info(words);
		log.info(json.getJSONArray("valoracion").getInt(0));
		
		db.saveVote(words, json.getJSONArray("valoracion").getInt(0));
		
		log.info(userID);		
		
		return Response.status(201).build();
	}
	
}
