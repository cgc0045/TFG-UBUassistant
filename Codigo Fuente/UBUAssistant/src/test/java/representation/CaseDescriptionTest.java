package representation;

import static org.junit.Assert.*;

import org.junit.Test;

import jcolibri.cbrcore.Attribute;

/**
 * 
 * @author dansa
 *
 */
public class CaseDescriptionTest {
	
	private static final String GRADO= "grado";
	private static final String INGENIERIA= "ingenieria";
	private static final String INFORMATICA= "informatica";
	private static final String ONLINE= "online";
	

	@Test
	public void fieldTest() {
		
		CaseDescription caseDescription = new CaseDescription();
		caseDescription.setId(1);
		caseDescription.setKeyWord1(GRADO);
		caseDescription.setKeyWord2(INGENIERIA);
		caseDescription.setKeyWord3(INFORMATICA);
		caseDescription.setKeyWord4(ONLINE);
		caseDescription.setKeyWord5(null);
		
		assertSame(caseDescription.getId(),1);
		assertEquals("No coincide la palabra clave 1",caseDescription.getKeyWord1(),GRADO);
		assertEquals("No coincide la palabra clave 2",caseDescription.getKeyWord2(),INGENIERIA);
		assertEquals("No coincide la palabra clave 3",caseDescription.getKeyWord3(),INFORMATICA);
		assertEquals("No coincide la palabra clave 4",caseDescription.getKeyWord4(),ONLINE);
		assertNull("La palabra clave 5 debe ser null",caseDescription.getKeyWord5());

	}
	
	@Test
	public void stringTest() {
		
		CaseDescription caseDescription = new CaseDescription();
		caseDescription.setId(1);
		caseDescription.setKeyWord1(GRADO);
		caseDescription.setKeyWord2(INGENIERIA);
		caseDescription.setKeyWord3(INFORMATICA);
		caseDescription.setKeyWord4(ONLINE);
		caseDescription.setKeyWord5(null);
		
		String expectedOutput="[1 , grado , ingenieria , informatica , online , null]";
		assertEquals(caseDescription.toString(), expectedOutput);

	}
	
	@Test
	public void idTest() {
		
		CaseDescription caseDescription = new CaseDescription();
		assertTrue(caseDescription.getIdAttribute() instanceof Attribute);

	}
	


}
