package handler;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import cbr.CBR;
import database.DatabaseConnection;
import jcolibri.cbrcore.CBRCase;
import jcolibri.exception.ExecutionException;
import jcolibri.method.retrieve.RetrievalResult;
import representation.CaseDescription;
import representation.CaseSolution;
import storage.Storage;
import util.ResultsComparator;

/**
 * 
 * @author Daniel Santidrian Alonso
 *
 */
public class UBUassistantHandler {

	/**
	 * Global variables
	 */
	
	private String sessionId;
	
    private String usertText;
    private String response;
    private String userId;
    
    private String starBar;
    private String starBarButton;
    
    private String suggestButtons;
    private String multipleButtons;
    
    private DatabaseConnection db;
    private List<String> sentenceList;
    private List<String> saluteList;
    private List<String> saluteResponseList;
    
    private Map<CBRCase,Double> casesToReatin;
    private Map<String, List<String>> parcialResults;
    private Map<LinkedHashSet<String>,List<String>> finalResults;
    private Map<String, List<RetrievalResult>> badResuts;
    List<RetrievalResult> listOfValues;
    private LinkedHashSet<String> currentWords;
    
    private Storage storage;
    
    private CBR cbrApp;
    
    private static final Logger logger = Logger.getLogger(UBUassistantHandler.class);
    
    /**
     * Constructor of the class.
     * Obtains the salute lists and sentence list of the database.
     * Creates a instance of CBR.
     * @param userID unique identifier for a user.
     */
    public UBUassistantHandler(String userID) { 
    	this.userId=userID;
    	usertText = null;  
    	response = null;
    	
    	db = new DatabaseConnection(userID);
    	sentenceList=db.getSentenceList();
		saluteList=db.getSaluteList();
		saluteResponseList=db.getSaluteResponseList();
		
		storage=new Storage();
		
		cbrApp = new CBR();
    }
    
    /**
     * Method that sets the user text and calls the searchAnswer method.
     * @param usertText text input by the user.
     */
    public void setUsertText( String usertText ) {
    	this.usertText = usertText; 
    	searchAnswer();
    }

    /**
     * Method that restart the variables and calls the answerNonReservedWord method.
     */
    private void searchAnswer(){
    	
    	starBar=null;
    	starBarButton=null;
    	suggestButtons=null;
    	multipleButtons=null;
    	String userTextLower=usertText.toLowerCase();
    	userTextLower=removeEspecialChar(userTextLower);
    	answerNonReservedWord(userTextLower);	
    }
    
    /**
     * Method that generates the response when a reserved word is input by the user.
     * @param userTextLower text input by the user.
     * @return true or false if the text input by the user is a reserved word.
     */
    private boolean answerReservedWord(String userTextLower) {
		
    	String userTextOneUp=userTextLower.substring(0, 1).toUpperCase() + userTextLower.substring(1);

		if(saluteList.contains(userTextOneUp)){
			
			int index=saluteList.indexOf(userTextOneUp);
			
			if(index!=-1){
				response=saluteResponseList.get(index);
			}
			return true;
		}
		return false;	
	}
    
    /**
     * Method that call other method for generating the response when the text input by the user
     * is not a reserved word.
     * @param userTextLower text input by the user.
     */
    private void answerNonReservedWord(String userTextLower){
    	
    	response="<p>"+getRandomSentence()+"</p>";
    	
    	boolean flag=answerReservedWord(userTextLower);
    	
    	if(!flag){
    		
    		String[] words = userTextLower.split("\\s+");
    		
    		cbrApp.buildEval(words);
    		
			cbrApp.buildFinalResults();
			
			parcialResults=cbrApp.getParcialResults();
			finalResults=cbrApp.getFinalResults();
			casesToReatin=cbrApp.getCasesToReatin();
			badResuts=cbrApp.getBadResuts();
			
			getAnswers();
			
			noAnswerSuggestions();
		}
    }
    

	
	/**
	 * Method that call the corresponding methods if there is only one answer 
	 * or multiple answers were found.
	 * After that it sets down the connection.
	 */
	public void getAnswers() {
		
		int cont=0;
		for(List<String> temp : finalResults.values()){
			cont+=temp.size();
		}

		if(!finalResults.isEmpty()){
			if(cont==1)
				oneAnswerResponse();
			else
				multipleAnswersResponse();
		}
		
		//Setting down the connection calling postCycle
		try {
			cbrApp.postCycle();
		} catch (ExecutionException e) {
			logger.error(e.toString());

		}
	}
	

	/**
	 * Method that generates the response when there is only one answer.
	 */
	private void oneAnswerResponse() {
		
		String temp=finalResults.get(finalResults.keySet().iterator().next()).iterator().next();
		
		if(temp.contains("http"))
			response += "<a href="+temp+" target=\"_blank\">"+temp+"</a>";
		else
			response += temp;
			
		LinkedHashSet<String> palabras=finalResults.keySet().iterator().next();
		currentWords=palabras;
		//Increasing the number of searches of the word
		db.aumentarNumBusquedas(palabras,finalResults.get(finalResults.keySet().iterator().next()).iterator().next());
		//Calling the method to ask the user about the utility of the answer
		putStarBar();
			
	}
	
	/**
	 * Method that generates the response when there are multiple answers.
	 */
	private void multipleAnswersResponse() {
		
		multipleButtons="";
		
		response="<p>Vaya, parece que tengo demasiadas respuestas.<p>"+
				 "<p>Intenta ser más concreto o selecciona alguna sugerencia.<p>";
		
		
	
		int max=0;
		if(casesToReatin.size()>10)
			max=10;
		else
			max=casesToReatin.size();
		
		int i=0;
		
		casesToReatin=casesToReatin.entrySet().stream()
        .sorted(Map.Entry.<CBRCase, Double>comparingByValue().reversed())
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v2, LinkedHashMap::new));
		
		for(CBRCase c : casesToReatin.keySet()){
			
			
			if(i<max){
				
				String temp=((CaseDescription)c.getDescription()).getKeyWord1();
				String word=temp.substring(0, 1).toUpperCase() + temp.substring(1);
				String answer=((CaseSolution)c.getSolution()).getAnswer();
				
				multipleButtons+="<form method=\"post\" id=\"multipleForm\" class=\"multipleForm\" action=\"multipleAnswer.jsp;jsessionid="+getSessionId()+"\">"+
										"<input type=\"hidden\" id=\"keyWord\" name=\"usertText\" value=\""+word+"\">"+
									    "<input type=\"hidden\" id=\"answer\" name=\"answer\" value=\""+answer+"\">"+
									    "<input type=\"hidden\" id=\"buttonDiv\" name=\"buttonDiv\">"+
									    "<input type=\"button\" id=\"but\" class=\"multBut\" onclick=\"hideAndSubmit(this)\" value=\""+word+"\">"+
									"</form>";
			}else{
				break;
			}
			
			i+=1;
			
		}
		
		//Calling the method to ask the user about the utility of the answer
		putStarBarButton();
	}

	/**
	 * Method that generates the response when there is not an answer.
	 */
	public void noAnswerSuggestions() {
		
		if(parcialResults.isEmpty()){
		
			response="<p>Lo siento no tengo respuestas a tu pregunta :(<p>";
			
			Collection<List<RetrievalResult>> values = badResuts.values();
			listOfValues = new ArrayList<>();
			for(List<RetrievalResult> list : values){
				for(RetrievalResult r : list){
					listOfValues.add(r);
				}
			}
			
			Collections.sort(listOfValues, new ResultsComparator());
			
			//If there are at least one neighbor for the input text
			if(!badResuts.isEmpty()){
	
				response += "Echa un vistazo a las sugerencias de búsqueda";
				suggestButtons = "";
				
				for(int i=0;i<3;i++){
					
					String word=((CaseDescription)listOfValues.get(i).get_case().getDescription()).getKeyWord1();
					String answer=((CaseSolution)listOfValues.get(i).get_case().getSolution()).getAnswer();

					suggestButtons+="<form method=\"post\" action=\"noAnswer.jsp;jsessionid="+getSessionId()+"\" style=\"display: inline-block;\">"+
						"<input type=\"hidden\" id=\"num\" name=\"num\" value=\""+i+"\">"+
					    "<input type=\"hidden\" id=\"keyWord\" name=\"usertText\" value=\""+word+"\">"+
					    "<input type=\"hidden\" id=\"answer\" name=\"answer\" value=\""+answer+"\">"+
					    "<input type=\"submit\" class=\"sugBut\" value=\""+word+"\">"+
					    "</form>";
				}
				
				putStarBarButton();
			}	
		}
	}
	
	/**
	 * Method that generates the string of a star bar.
	 */
	private void putStarBar(){
		starBar="<form method=\"post\" id=\"starForm\" class=\"multipleForm\" action=\"starRating.jsp;jsessionid="+getSessionId()+"\">"+
							"<div class=\"rate\">"+
							"<div class=\"rate-text\">Valora esta respuesta </div> "+
							"<input type=\"hidden\" id=\"vote\" name=\"vote\">"+
					        "<input type=\"radio\" id=\"star5\" name=\"rate\" value=\"5\" onclick=\"getVoteAndSubmit(this)\" /><label for=\"star5\" title=\"text\"></label>"+
					        "<input type=\"radio\" id=\"star4\" name=\"rate\" value=\"4\" onclick=\"getVoteAndSubmit(this)\"/><label for=\"star4\" title=\"text\"></label>"+
					        "<input type=\"radio\" id=\"star3\" name=\"rate\" value=\"3\" onclick=\"getVoteAndSubmit(this)\"/><label for=\"star3\" title=\"text\"></label>"+
					        "<input type=\"radio\" id=\"star2\" name=\"rate\" value=\"2\" onclick=\"getVoteAndSubmit(this)\"/><label for=\"star2\" title=\"text\"></label>"+
					        "<input type=\"radio\" id=\"star1\" name=\"rate\" value=\"1\" onclick=\"getVoteAndSubmit(this)\"/><label for=\"star1\" title=\"text\"></label>"+
					        "</div></form>";
	}
    
	/**
	 * Method that generates the string of a star bar when there are buttons available.
	 */
    private void putStarBarButton(){
		starBarButton="<form method=\"post\" id=\"starForm\" class=\"multipleForm\" action=\"starRatingButton.jsp;jsessionid="+getSessionId()+"\">"+
							"<div class=\"rate\">"+
							"<div class=\"rate-text\">Valora esta respuesta </div> "+
							"<input type=\"hidden\" id=\"wordButton\" name=\"wordButton\">"+
							"<input type=\"hidden\" id=\"buttonDiv\" name=\"buttonDiv\">"+
							"<input type=\"hidden\" id=\"vote\" name=\"vote\">"+
					        "<input type=\"radio\" id=\"star5\" name=\"rate\" value=\"5\" onclick=\"getVoteAndSubmit(this)\" /><label for=\"star5\" title=\"text\"></label>"+
					        "<input type=\"radio\" id=\"star4\" name=\"rate\" value=\"4\" onclick=\"getVoteAndSubmit(this)\"/><label for=\"star4\" title=\"text\"></label>"+
					        "<input type=\"radio\" id=\"star3\" name=\"rate\" value=\"3\" onclick=\"getVoteAndSubmit(this)\"/><label for=\"star3\" title=\"text\"></label>"+
					        "<input type=\"radio\" id=\"star2\" name=\"rate\" value=\"2\" onclick=\"getVoteAndSubmit(this)\"/><label for=\"star2\" title=\"text\"></label>"+
					        "<input type=\"radio\" id=\"star1\" name=\"rate\" value=\"1\" onclick=\"getVoteAndSubmit(this)\"/><label for=\"star1\" title=\"text\"></label>"+
					        "</div></form>";
	}
	
	
	/**
	 * Method that returns a random salute.
	 * @return random salute.
	 */
	public String getSalute(){
    	
    	return sentenceList.get((int) (Math.random()*5));
    }
    
	/**
	 * Method that returns a random sentence.
	 * @return random sentence.
	 */
    public String getRandomSentence(){
    	return sentenceList.get((int)(Math.random()*5+5));
    }
	
    /**
	 * Method that returns the user text.
	 * @return usertText Text input by the user.
	 */
    public String getUsertText() {   
        return usertText;     
    }

    /**
   	 * Method that returns the response.
   	 * @return response response generated for the input text.
   	 */
	public String getResponse() {
		return response;
	}

	/**
	 * Method that sets the response.
	 * @param response response for the input text.
	 */
	public void setResponse(String response) {
		this.response = response;
	}

	/**
   	 * Method that returns the userId.
   	 * @return userId unique identifier of the user.
   	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Method that sets the user id.
	 * @param userId unique identifier of the user.
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
   	 * Method that returns the starBar.
   	 * @return starBar method for rate the response given.
   	 */
	public String getStarBar() {
		return starBar;
	}

	/**
   	 * Method that returns the suggestButtons.
   	 * @return suggestButtons string with the buttons generated when there is not answer.
   	 */
	public String getSuggestButtons() {
		return suggestButtons;
	}

	/**
   	 * Method that returns the multipleButtons.
   	 * @return multipleButtons string with the buttons generated when there are multiple answers.
   	 */
	public String getMultipleButtons() {
		return multipleButtons;
	}

	/**
	 * Method that returns the currentWords.
	 * @return currentWords words that generates the simple answer.
	 */
	public LinkedHashSet<String> getCurrentWords() {
		return currentWords;
	}

	/**
	 * Method that returns the db.
	 * @return db connection to the database.
	 */
	public DatabaseConnection getDb() {
		return db;
	}

	/**
	 * Method that returns the badResuts.
	 * @return badResuts map that stores all the results in value with the word that generate them in key.
	 */
	public Map<String, List<RetrievalResult>> getBadResuts() {
		return badResuts;
	}

	/**
	 * Method that returns the listOfValues.
	 * @return listOfValues list with the results of badResults.
	 */
	public List<RetrievalResult> getListOfValues() {
		return listOfValues;
	}

	/**
   	 * Method that returns the starBarButton.
   	 * @return starBarButton method for rate the response given.
   	 */
	public String getStarBarButton() {
		return starBarButton;
	}

	/**
	 * Method that returns the storage object
	 * @return the storage
	 */
	public Storage getStorage() {
		return storage;
	}
	
	/**
	 * Function that returns a String without special characters
	 * @param input String with special characters
	 * @return output String without special characters
	 */
	private String removeEspecialChar(String input) {
	    String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ´";
	    String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC ";
	    
	    String output = input;
	    for (int i=0; i<original.length(); i++) {
	        output = output.replace(original.charAt(i), ascii.charAt(i));
	    }
	    return output;
	}

	/**
	 * Function that returns the identifier of the HTTPsession.
	 * @return the sessionId identifier of the HTTPsession.
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * Method that sets the identifier of the HTTPsession.
	 * @param sessionId identifier of the HTTPsession.
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}


}