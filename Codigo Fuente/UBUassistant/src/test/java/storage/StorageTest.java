package storage;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class StorageTest {
	
	private static final String USRMSG = "user-message";

	@Test
	public void storageSingleTextTest() {
		
		
		String expectedOutput="<div class=\"user-message\">"+"<div class=\"message\">becas internacionales</div></div>\n";

		Storage storage = new Storage();
		
		storage.setChatOutput(USRMSG, "becas internacionales");
		
		assertEquals(expectedOutput,storage.getChatOutput());

	}
	
	@Test
	public void storageMultipleTextTest() {
		
		String[] messageType = new String[5];
		messageType[0]=USRMSG;
		messageType[1]="bot-message";
		messageType[2]="invented-message";
		messageType[3]=USRMSG;
		messageType[4]="bot-message";
		
		String[] messageText = new String[5];
		messageText[0]="Hola";
		messageText[1]="Adios";
		messageText[2]="Texto";
		messageText[3]="becas";
		messageText[4]="informatica";
		
		StringBuilder expectedOutput = new StringBuilder();
		Storage storage = new Storage();
		
		for(int i=0;i<messageText.length;i++){
			expectedOutput.append("<div class=\""+messageType[i]+"\">"+"<div class=\"message\">"+messageText[i]+"</div></div>\n");
			storage.setChatOutput(messageType[i], messageText[i]);
		}
		
		assertEquals(expectedOutput.toString(),storage.getChatOutput());
	}
	


}
