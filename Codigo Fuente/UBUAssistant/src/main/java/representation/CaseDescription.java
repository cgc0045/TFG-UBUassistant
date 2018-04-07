package representation;

import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CaseComponent;

/**
 * 
 * @author Daniel Santidrian Alonso
 *
 */
public class CaseDescription implements CaseComponent { 

	/**
	 * Global variables
	 */
	private java.lang.Integer id;
	private java.lang.String keyWord1;
	private java.lang.String keyWord2;
	private java.lang.String keyWord3;
	private java.lang.String keyWord4;
	private java.lang.String keyWord5;

	
	/**
	 * Function that returns the id of the case description.
	 * @return id id of the case description.
	 */
	public java.lang.Integer getId(){
		return id;
	}

	/**
	 * Function that sets the id of the case description.
	 * @param id1 id of the case description.
	 */
	public void setId(java.lang.Integer id1){
		this.id = id1;
	}


	/**
	 * Function that returns the keyWord1 of the case description.
	 * @return keyWord1 keyWord1 of the case description
	 */
	public java.lang.String getKeyWord1(){
		return keyWord1;
	}
	
	/**
	 * Function that sets the keyWord1 of the case description.
	 * @param keyWord10 keyWord1 of the case description.
	 */
	public void setKeyWord1(java.lang.String keyWord10){
		this.keyWord1 = keyWord10;
	}

	/**
	 * Function that returns the keyWord2 of the case description.
	 * @return keyWord2 keyWord2 of the case description
	 */
	public java.lang.String getKeyWord2(){
		return keyWord2;
	}
	
	/**
	 * Function that sets the keyWord2 of the case description.
	 * @param keyWord22 keyWord2 of the case description.
	 */
	public void setKeyWord2(java.lang.String keyWord22){
		this.keyWord2 = keyWord22;
	}

	/**
	 * Function that returns the keyWord3 of the case description.
	 * @return keyWord3 keyWord3 of the case description
	 */
	public java.lang.String getKeyWord3(){
		return keyWord3;
	}
	
	/**
	 * Function that sets the keyWord3 of the case description.
	 * @param keyWord33 keyWord3 of the case description.
	 */
	public void setKeyWord3(java.lang.String keyWord33){
		this.keyWord3 = keyWord33;
	}

	/**
	 * Function that returns the keyWord4 of the case description.
	 * @return keyWord4 keyWord4 of the case description
	 */
	public java.lang.String getKeyWord4(){
		return keyWord4;
	}
	
	/**
	 * Function that sets the keyWord4 of the case description.
	 * @param keyWord44 keyWord4 of the case description.
	 */
	public void setKeyWord4(java.lang.String keyWord44){
		this.keyWord4 = keyWord44;
	}

	/**
	 * Function that returns the keyWord5 of the case description.
	 * @return keyWord5 keyWord5 of the case description
	 */
	public java.lang.String getKeyWord5(){
		return keyWord5;
	}
	
	/**
	 * Function that sets the keyWord5 of the case description.
	 * @param keyWord55 keyWord5 of the case description.
	 */
	public void setKeyWord5(java.lang.String keyWord55){
		this.keyWord5 = keyWord55;
	}

	/**
	 * Function that returns a new id attribute.
	 * @return new id attribute of case description.
	 */
	@Override
	public Attribute getIdAttribute(){
		return new Attribute("id",this.getClass());
	} 

	/**
	 * Function that return a string with the variables of the class.
	 * @return a string with the variables of the class.
	 */
	public String toString()		{
			return "["+ id + " , " + keyWord1 + " , " + keyWord2 + " , " + keyWord3 + " , " + keyWord4 + " , " + keyWord5 +"]";
		}

}
