package com.scienceminer.nerd.kb;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KBUtilitiesTest {
	
	//private LowerKnowledgeBase wikipedia = null;

	@Before
	public void setUp() {
		try {
        	UpperKnowledgeBase.getInstance(); 
        	//wikipedia = UpperKnowledgeBase.getInstance().getWikipediaConf("en");
        } catch(Exception e) {
        	e.printStackTrace();
        }
	}

	@Test
	public void testGetConcept(){
		try{
			Concept concept = UpperKnowledgeBase.getInstance().getConcept("Q76");
			System.out.println("Wikipedia Id : " + concept.getPageIdByLang("en") + ", Wikidata Id : " + concept.getId());
			System.out.println("Features : " + concept.getStatements());
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Test
	public void testGetStatements(){
		try{
			List<Statement> statements = UpperKnowledgeBase.getInstance().getStatements("Q76");
			System.out.println(statements);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Test
	public void testGetReverseStatements(){
		try {
			List<Statement> reverseStatements = UpperKnowledgeBase.getInstance().getReverseStatements("Q76");
			System.out.println(reverseStatements);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Test
	public void testImmediateTaxonParents() {
		try {
			List<String> parents = UpperKnowledgeBase.getInstance().getParentTaxons("Q7377");
System.out.println(parents);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testFullTaxonParents() {
		try {
			List<String> parents = UpperKnowledgeBase.getInstance().getFullParentTaxons("Q18498");
System.out.println("Q18498: " + parents);

			parents = UpperKnowledgeBase.getInstance().getFullParentTaxons("Q3200306");
System.out.println("Q3200306: " + parents);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testNerdKidPredictedClass(){
		try {
			List<String> wikidataId = new ArrayList<>();
			wikidataId.add("Q18498");
			wikidataId.add("Q1098");
			wikidataId.add("Q27145");
			wikidataId.add("Q27146");
			wikidataId.add("Q27470");
			wikidataId.add("Q27471");
			wikidataId.add("Q27549");
			wikidataId.add("Q29416");
			wikidataId.add("Q29417");
			wikidataId.add("Q29418");
			wikidataId.add("Q29423");
			wikidataId.add("Q29424");
			wikidataId.add("Q35315");
			wikidataId.add("Q412546");
			wikidataId.add("Q123220"); // the new Nerd-Kid database has element until 'Q123220' -- predicted class LOCATION
			for (String wiki : wikidataId){
				String predictedClass = UpperKnowledgeBase.getInstance().getNerdKidPredictedClass(wiki);
				System.out.println("Predicted class for " + wiki + " : " + predictedClass);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testKingdomMethods() {
		try {
			boolean isAnimal = KBUtilities.isAnimal("Q18498");
			System.out.println("Q18498.isAnimal = " + isAnimal);

			isAnimal = KBUtilities.isAnimal("Q3200306");
			System.out.println("Q3200306.isAnimal = " + isAnimal);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/*@After
	public void testClose() {
		try {
			wikipedia.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}*/
}