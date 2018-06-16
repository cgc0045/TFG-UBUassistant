package representation;

import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CaseComponent;

/**
 * 
 * @author Daniel Santidrian Alonso
 *
 */
public class CaseSolution implements CaseComponent { 

	/**
	 * Global variables
	 */
	private java.lang.Integer Id;
	private java.lang.String answer;

	/**
	 * Function that returns the id of the case solution.
	 * @return Id id of the case solution.
	 */
	public java.lang.Integer getId(){
		return Id;
	}
	
	/**
	 * Function that sets the id of the case solution.
	 * @param Id7 id of the case solution.
	 */
	public void setId(java.lang.Integer Id7){
		this.Id = Id7;
	}

	/**
	 * Function that returns the answer of the case solution.
	 * @return answer answer of the case solution.
	 */
	public java.lang.String getAnswer(){
		return answer;
	}
	
	/**
	 * Function that sets the answer of the case solution.
	 * @param answer6 answer of the case solution.
	 */
	public void setAnswer(java.lang.String answer6){
		this.answer = answer6;
	}

	/**
	 * Function that returns a new id attribute.
	 * @return new id attribute of case solution.
	 */
	@Override
	public Attribute getIdAttribute(){
		return new Attribute("Id",this.getClass());
	} 

	/**
	 * Function that return a string with the variables of the class.
	 * @return a string with the variables of the class.
	 */
	public String toString(){
		return "["+ Id + " , " + answer +"]";
	}
}
