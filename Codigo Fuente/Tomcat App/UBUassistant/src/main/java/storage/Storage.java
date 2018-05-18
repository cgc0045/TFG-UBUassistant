package storage;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Daniel Santidrian Alonso
 *
 */
public class Storage {
	
	private List<String> chatOutput;
	
	/**
	 * Constructor of the class.
	 */
	public Storage() {
		chatOutput=new ArrayList<>();
	}

	/**
	 * Function that returns the text of the chat output.
	 * @return text text of the chat output.
	 */
	public String getChatOutput() {
		
		StringBuilder text = new StringBuilder();
		
		for(String s : chatOutput)
			text.append(s);
		
		return text.toString();
	}

	/**
	 * Method that sets the text of the chat output.
	 * @param typeMessage message type for the user or system.
	 * @param text text of the chat output.
	 */
	public void setChatOutput(String typeMessage, String text) {
		
		String chatText="<div class=\""+typeMessage+"\">"+"<div class=\"message\">"+text+"</div></div>\n";

		for(int i=0; i<chatOutput.size();i++){
			if(chatOutput.get(i).concat(chatText).length()<Integer.MAX_VALUE){
				String newText=chatOutput.get(i).concat(chatText);
				chatOutput.remove(i);
				chatOutput.add(newText);
			}else{
				chatOutput.add(chatText);
			}
		}
		
		if(chatOutput.isEmpty())
			chatOutput.add(chatText);
		
	}

}
