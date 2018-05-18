package cbr;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

import org.apache.log4j.Logger;
import jcolibri.cbraplications.StandardCBRApplication;
import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CBRCase;
import jcolibri.cbrcore.CBRCaseBase;
import jcolibri.cbrcore.CBRQuery;
import jcolibri.cbrcore.Connector;
import jcolibri.exception.ExecutionException;
import jcolibri.exception.InitializingException;
import jcolibri.method.retrieve.RetrievalResult;
import jcolibri.method.retrieve.NNretrieval.NNConfig;
import jcolibri.method.retrieve.NNretrieval.NNScoringMethod;
import representation.CaseDescription;
import representation.CaseSolution;


/**
 * 
 * @author Daniel Santidrian Alonso
 *
 */
public class CBR implements StandardCBRApplication {
	
	
	/**
	 * Global variables
	 */
	@Generated(value = { "ColibriStudio" })
	Connector connector;
	
	@Generated(value = { "ColibriStudio" })
	CBRCaseBase casebase;


	private Collection<RetrievalResult> eval;
	private CBRQuery query;
	private CaseDescription cd = new CaseDescription();
	
	private Map<CBRCase,Double> casesToReatin;
	private Map<CBRCase,Double> casesToReatinGood;
    private Map<String, List<String>> parcialResults;
    private Map<LinkedHashSet<String>,List<String>> finalResults;
    private HashMap<String, List<RetrievalResult>> badResuts = new HashMap<>();

    private static final Logger logger = Logger.getLogger(CBR.class);
    
	

	/**
     * Constructor of the class
     */
    public CBR() {
    	configureCBR();	
	}
    

	//******************************************************************/
	// Configuration
	//******************************************************************/
    
    /**
     * Method that calls the configuration methods
     * @throws ExecutionException exception that is thrown when an error occurs at execution time.
     */
	@Override
	public void configure() throws ExecutionException {
		try{
			configureConnector();
			configureCaseBase();
		} catch (Exception e){
			throw new ExecutionException(e);
		}
	}

	/**
	 * Method that configures the connector and load the cases from the database.
	 * @throws InitializingException Exception that is thrown when it is not possible to build the connector or the case base.
	 */
	@Generated(value = { "CS-PTConector" })	
	private void configureConnector() throws InitializingException{
				
		CBRConnector CBRconnector=CBRConnector.getInstance();
		connector=CBRconnector.getConnector();
	}


	/**
	 * Method that configures the case base
	 * @throws InitializingException Exception that is thrown when it is not possible to build the connector or the casebase
	 */
	@Generated(value = { "CS-CaseManager" })	
	private void configureCaseBase() throws InitializingException{
		casebase = new jcolibri.casebase.LinearCaseBase();
	}

	//******************************************************************/
	// Similarity
	//******************************************************************/
	
	/**
	 * Function that configures the similarity configuration for the keyWords.
	 * @return simConfig object that contains the similarity configuration.
	 */
	@Generated(value = { "CS-Similarity" })	
	private NNConfig getSimilarityConfig() {
		NNConfig simConfig = new NNConfig();
		simConfig
				.setDescriptionSimFunction(new jcolibri.method.retrieve.NNretrieval.similarity.global.Euclidean());
		Attribute attribute0 = new Attribute("keyWord5", CaseDescription.class);
		simConfig
				.addMapping(
						attribute0,
						new jcolibri.method.retrieve.NNretrieval.similarity.local.MaxString());
		simConfig.setWeight(attribute0, 0.75);
		Attribute attribute1 = new Attribute("keyWord4", CaseDescription.class);
		simConfig
				.addMapping(
						attribute1,
						new jcolibri.method.retrieve.NNretrieval.similarity.local.MaxString());
		simConfig.setWeight(attribute1, 0.75);
		Attribute attribute2 = new Attribute("keyWord3", CaseDescription.class);
		simConfig
				.addMapping(
						attribute2,
						new jcolibri.method.retrieve.NNretrieval.similarity.local.MaxString());
		simConfig.setWeight(attribute2, 0.75);
		Attribute attribute3 = new Attribute("keyWord2", CaseDescription.class);
		simConfig
				.addMapping(
						attribute3,
						new jcolibri.method.retrieve.NNretrieval.similarity.local.MaxString());
		simConfig.setWeight(attribute3, 0.75);
		
		Attribute attribute5 = new Attribute("keyWord1", CaseDescription.class);
		simConfig
				.addMapping(
						attribute5,
						new jcolibri.method.retrieve.NNretrieval.similarity.local.MaxString());
		simConfig.setWeight(attribute5, 0.75);
		return simConfig;
	}

	//******************************************************************/
	// Methods
	//******************************************************************/
	
	/**
	 * Method preCycle that will be called to initialize and return the case base
	 * @throws ExecutionException exception that is thrown when an error occurs at execution time.
	 * @return casebase object that contains all the cases.
	 */
	@Generated(value = { "ColibriStudio" })
	@Override
	public CBRCaseBase preCycle() throws ExecutionException {
		casebase.init(connector);
		return casebase;
	}
	
	/**
	 * Method that save in a collection the RetrievalResults of the similar cases.
	 * @param query specific case.
	 * @throws ExecutionException exception that is thrown when an error occurs at execution time.
	 */
	@Generated(value = { "ColibriStudio" })	
	@Override
	public void cycle(CBRQuery query) throws ExecutionException {
		NNConfig simConfig = getSimilarityConfig();
		eval= NNScoringMethod.evaluateSimilarity(casebase.getCases(), query, simConfig);
	}

	/**
	 * Method that will be called to close the connection.
	 * @throws ExecutionException exception that is thrown when an error occurs at execution time.
	 */
	@Generated(value = { "ColibriStudio" })
	@Override
	public void postCycle() throws ExecutionException {
		connector.close();
	}

	/**
	 * Method that calls the configuration method, the preCycle method, and creates a new query.
	 */
	@Generated(value = { "ColibriStudio" })
	public void configureCBR() {
		
		try {
			configure();
			preCycle();
			
			query = new CBRQuery();
			
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * Method that query for each word, generate the responses and store the best answers
	 * with the word that generated it.
	 * @param words array with each word of the input text
	 */
	public void buildEval(String[] words) {
		
		//Map with all the responses found in value and the word that generate them in key.
		parcialResults=new HashMap<>();
		//Set that store all the cases with some similarity with the words input.
		casesToReatin = new HashMap<>();
		casesToReatinGood = new HashMap<>();
		//Map that saves all the results in value with the word that generate them in key.
		badResuts = new HashMap<>();
		
		//For each word in the input text
		for (String word : words) {
			
			if(word.length()>2){
				
				cd.setKeyWord1(word);
				cd.setKeyWord2(word);
				cd.setKeyWord3(word);
				cd.setKeyWord4(word);
				cd.setKeyWord5(word);
				query.setDescription(cd);
				
				try {
					cycle(query);
				} catch (ExecutionException e1) {
					logger.error(e1.toString());
				}
				
				badResuts.put(word, new ArrayList<RetrievalResult>(eval));
				//Remove the results with similarity worst than 0.35
				eval.removeIf(e -> e.getEval()<0.35);
				List<String> answer = new ArrayList<>();
				for(RetrievalResult r : eval){
					CBRCase c = r.get_case();
					casesToReatin.put(c,r.getEval());
					answer.add(((CaseSolution)r.get_case().getSolution()).getAnswer());
				}
				
				if(!answer.isEmpty())
					parcialResults.put(word, answer);
			}
		}
	}

	/**
	 * Method that generates the final results with the partial results of builEval.
	 */
	public void buildFinalResults() {
		
		//Map with the response in value and all the words that generated them in key.
		finalResults = new HashMap<>();
		
		//If there is an answer for the input text
		if(!parcialResults.isEmpty()){
			
			//Getting the common answer (the best answer) of the different words input by the user
			List<String> aux= new ArrayList<>(parcialResults.get(parcialResults.keySet().iterator().next()));
			
			for (Map.Entry<String,List<String>> entry : parcialResults.entrySet()) {
			    String key = entry.getKey();
			    aux.retainAll(parcialResults.get(key));
			}

			if(aux.isEmpty()){
				for (Map.Entry<String,List<String>> entry : parcialResults.entrySet()) {
				    String key = entry.getKey();
					aux.addAll(parcialResults.get(key));
				}
			}
			
			//Getting all the words that have generated the best answer and storing them in a map
			LinkedHashSet<String> word = new LinkedHashSet<>();
			
			for(String res : aux){	
				
				for (Map.Entry<String,List<String>> entry : parcialResults.entrySet()) {
				    String o = entry.getKey();
				    if (parcialResults.get(o).contains(res)) {
				        word.add(o);
				      }
				}
				
				if(finalResults.containsKey(word)){
					List<String> temp = new ArrayList<>(finalResults.get(word));
					temp.add(res);
					finalResults.put(word, temp);
				}else{
					List<String> lista = new ArrayList<>();
					lista.add(res);
					finalResults.put(word, lista);
				}
				
				word = new LinkedHashSet<>();
			}
			
			
			for(List<String> list : finalResults.values()){
				for(String answer : list){
					for (Map.Entry<CBRCase,Double> entry : casesToReatin.entrySet()) {
					    CBRCase cs = entry.getKey();
						if(((CaseSolution)cs.getSolution()).getAnswer().equals(answer)){
							casesToReatinGood.put(cs,casesToReatin.get(cs));
						}
					}
				}
			}
		}
	}
	
	/**
	 * Function that returns the casesToRetain
	 * @return casesToReatin set that store all the cases with some similarity with the words input.
	 */
	public Map<CBRCase,Double> getCasesToReatin() {
		return casesToReatinGood;
	}

	/**
	 * Function that returns the parcialResults
	 * @return parcialResults map with all the responses found for a word in value and the word that generate them in key.
	 */
	public Map<String, List<String>> getParcialResults() {
		return parcialResults;
	}

	/**
	 * Function that returns the finalResults
	 * @return finalResults map with the response in value and all the words that generated them in key.
	 */
	public Map<LinkedHashSet<String>, List<String>> getFinalResults() {
		return finalResults;
	}

	/**
	 * Function that returns the badResuts
	 * @return badResuts map that stores all the results in value with the word that generate them in key.
	 */
	public Map<String, List<RetrievalResult>> getBadResuts() {
		return badResuts;
	}

}
