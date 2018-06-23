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
import storage.Storage;

@Path("/vote")
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
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/log")
	public Response log(String input) {
		Logger log = Logger.getLogger(getClass());
		JSONObject json = new JSONObject(input);
		
		log.info("Recibido el POST");
		log.info(input);
		
		return Response.status(201).entity(input).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
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
		
		
		for(Object o: json.getJSONArray("Palabras").getJSONArray(0)){
			words.add(o.toString());
		}
		
		log.info(words);
		log.info(json.getJSONArray("valoracion").getInt(0));
		
		db.saveVote(words, json.getJSONArray("valoracion").getInt(0));
		
		Storage storage = ubuassistant.getStorage();
		
		log.info(userID);
		
		
		return Response.status(201).entity(input).build();
	}
	
}
