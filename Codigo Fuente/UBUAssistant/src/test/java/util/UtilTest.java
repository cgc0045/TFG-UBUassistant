package util;



import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import jcolibri.cbrcore.CBRCase;
import jcolibri.method.retrieve.RetrievalResult;


public class UtilTest {

	@Test
	public void resultsComparatorTest() {
		
		CBRCase c1 = new CBRCase();
		
		RetrievalResult r1 = new RetrievalResult(c1, 0.56);
		RetrievalResult r2 = new RetrievalResult(c1, 0.1);
		RetrievalResult r3 = new RetrievalResult(c1, 0.06);
		RetrievalResult r7 = new RetrievalResult(c1, 0.06);
		RetrievalResult r4 = new RetrievalResult(c1, 0.8);
		RetrievalResult r5 = new RetrievalResult(c1, 2.56);
		RetrievalResult r6 = new RetrievalResult(c1, 0.567);
		
		
		List<RetrievalResult> list = new ArrayList<>();
		
		list.add(r1);
		list.add(r2);
		list.add(r3);
		list.add(r7);
		list.add(r4);
		list.add(r5);
		list.add(r6);
		
		
		Collections.sort(list, new ResultsComparator());
		
		assertEquals(list.get(0),r5);
		
		assertEquals(list.get(2),r6);
		assertEquals(list.get(3),r1);
		
		assertEquals(list.get(list.size()-1),r7);
	
	}
	


}
