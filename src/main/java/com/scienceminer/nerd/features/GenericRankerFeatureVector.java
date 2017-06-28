package com.scienceminer.nerd.features;

import java.io.*;
import java.util.*;
import java.text.*;
import java.util.regex.*;

public class GenericRankerFeatureVector {
	public String title = "Generic";

	public String string = null; // lexical feature
	public double label = 0.0; // numerical label if known
	public String classes = "N"; // class label if known
	
	// mask of features
	public boolean Add_prob_c = false;  // conditional probability of the concept given the string
	public boolean Add_prob_i = false; // conditional probability of the string given the concept 
												 // (i.e. reverse prob_c)
	public boolean Add_frequencyStrict = false;  // frequency of usage of the string to refer to the 
													 // concept in a reference corpus (usually wikipedia)
	public boolean Add_frequencyConcept = false; // frequency of usage of the concept
	public boolean Add_termLength = false;
	public boolean Add_inDictionary = false; // true if the words of the term are all in the dictionary
													// of common words
	public boolean Add_relatedness = false;	// semantic relatedness measure bewteen candidate and context											
	public boolean Add_context_quality = false;	// quality of the context

	public boolean Add_ner_st = false; // boolean indicating if identified as NER mention by Stanford NER
	public boolean Add_ner_id = false; // boolean indicating if identified as NER mention by Idilia service 
	public boolean Add_ner_type = false; // wordnet ner type
	public boolean Add_ner_subtype = false; // wordnet ner subtype
	
	public boolean Add_isSubTerm = false; // true if sub-string of another term candidate
	
	public boolean Add_NERType_relatedness = false; // relateness between the concept and the estimated NER type
	public boolean Add_NERSubType_relatedness = false; // relateness between the concept and the estimated NER subtype
	
	public boolean Add_occ_term = false; // term frequency 
	
	public boolean Add_dice_coef = false; // lexical cohesion measure based on DICE coefficient

	// relateness with the context:
	// - with context terms
	// - with all other candidates (average)
	// - between NER subtype and context
	// - between NER subtype and context WSD

	// quality of the context:
	// - minimum depth of concept in wikipedia category tree
	// - general term frequency
	// - tf/idf
	// - more lexical cohesion measures

	// quality of the NE property:
	// - mention marked by NER
	
	// decision types
	public boolean target_numeric = false;
	public boolean target_class = true;
	
	// features		
	public double prob_c = 0.0;
	public double prob_i = 0.0;
	public int frequencyStrict = 0; 
	public int frequencyConcept = 0; 
	
	public int termLength = 0; 
	public boolean inDictionary = false; 
	
	public double relatedness = 0.0;
	public double context_quality = 0.0;
	
	public boolean ner_st = false;
	public boolean ner_id = false;
	public String ner_type = "NotNER";
	public String ner_subtype = "NotNER";

	public boolean isSubTerm = false;
	
	public double NERType_relatedness = 0.0;
	public double NERSubType_relatedness = 0.0;

	public long occ_term = 0;
	public double dice_coef = 0.0;

	/**
	 *  Write header of ARFF files.
	 */
	public String getArffHeader() throws IOException {
		StringBuilder header = new StringBuilder();
		header.append("% 1. Title: " + title + " \n");
		header.append("%\n"); 
		header.append("% 2. Sources: \n"); 
		header.append("% (a) Creator: (N)ERD \n"); 

		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Date date = new Date();
		header.append("% (c) Date: " + dateFormat.format(date) + " \n"); 

		header.append("%\n");
		header.append("@RELATION @NERD_"+ title.replace(" ","_") +" \n");
		
		if (Add_prob_c)
			header.append("@attribute prob_c REAL\n");
		if (Add_prob_i)		
			header.append("@attribute prob_i REAL\n");
		if (Add_frequencyStrict)	
			header.append("@attribute frequencyStrict NUMERIC\n");
		if (Add_frequencyConcept)	
			header.append("@attribute frequencyConcept NUMERIC\n");	
		if (Add_termLength)	
			header.append("@attribute termLength NUMERIC\n"); 
		if (Add_inDictionary)	
			header.append("@attribute inDictionary {false, true}\n");
		if (Add_relatedness)	
			header.append("@attribute relatedness REAL\n");
		if (Add_context_quality)	
			header.append("@attribute context_quality REAL\n");
		if (Add_isSubTerm)	
			header.append("@attribute isSubTerm {false, true}\n");	
		if (Add_ner_st) 
			header.append("@attribute ner_st {false, true}\n");
		if (Add_ner_id)
			header.append("@attribute ner_id {false, true}\n");
		if (Add_ner_type)
			header.append("@attribute ner_type {NotNER, PERSON, LOCATION, ORGANIZATION}\n");
		if (Add_ner_subtype)	
			header.append("@attribute ner_subtype string\n");
		if (Add_NERType_relatedness)
			header.append("@attribute NERType_relatedness REAL\n");
		if (Add_NERSubType_relatedness)
			header.append("@attribute NERSubType_relatedness REAL\n");
		if (Add_occ_term) 
			header.append("@attribute occ_term NUMERIC\n");	
		if (Add_dice_coef) 
			header.append("@attribute dice_coef REAL\n");			
		
		if (target_numeric)
			header.append("@attribute entity? REAL\n\n"); // target variable for regression
		else 
			header.append("@attribute entity? {Y, N}\n\n"); // target variable for binary classification
		header.append("@data\n");
		return header.toString();
	}
	
	public int getNumFeatures() {
		int num = 0;
		if (Add_prob_c)
			num++;
		if (Add_prob_i)
			num++;
		if (Add_frequencyStrict)	
			num++;
		if (Add_frequencyConcept)	
			num++;
		if (Add_termLength)	
			num++;
		if (Add_inDictionary)
			num++;
		if (Add_relatedness)
			num++;
		if (Add_context_quality)
			num++;
		if (Add_isSubTerm)
			num++;
		if (Add_ner_st) 
			num++;
		if (Add_ner_id)
			num++;
		if (Add_ner_type)
			num++;
		if (Add_ner_subtype)	
			num++;
		if (Add_NERType_relatedness)
			num++;
		if (Add_NERSubType_relatedness)	
			num++;
		if (Add_occ_term) 
			num++;
		if (Add_dice_coef) 
			num++;
		// class
		num++;	
		return num;
	}
	
	public String printVector() {
		/*if (string == null) return null;
		if (string.length() == 0) return null;*/
		boolean first = true;
		
		StringBuffer res = new StringBuffer();
		
		// token string (1)
		//res.append(string);

		// conditional probability of the concept given the string
		if (Add_prob_c) {
			res.append(prob_c);
			first = false;
		}
		
		// conditional probability of the string given the concept (i.e. reverse prob_c)
		if (Add_prob_i) {
			if (first) {
				res.append(prob_i);	
				first = false;	
			}
			else 
				res.append("," + prob_i);	
		}
			
		// frequency of usage of the string to refer to the concept in a reference corpus (usually wikipedia)
		if (Add_frequencyStrict) {
			if (first) {
				res.append(frequencyStrict);	
				first = false;
			}
			else	
				res.append("," + frequencyStrict);
		}
			
		// frequency of usage of the concept
		if (Add_frequencyConcept) {
			if (first) {
				res.append(frequencyConcept);
				first = false;
			}
			else		
				res.append("," + frequencyConcept);
		}
		
		// term length
		if (Add_termLength) {
			if (first) {
				res.append(termLength);
				first = false;
			}
			else
				res.append("," + termLength);
		}

		if (Add_inDictionary) {
			if (inDictionary) 
				res.append(",true");
			else 
				res.append(",false");
		}
		
		if (Add_relatedness) {
			res.append(","+relatedness);
		}

		if (Add_context_quality) {
			res.append(","+context_quality);
		}
		
		if (Add_isSubTerm) {
			if (isSubTerm) 
				res.append(",true");
			else 
				res.append(",false");
		}
		if (Add_ner_st) {
			if (ner_st) 
				res.append(",true");
			else 
				res.append(",false");
		}
		if (Add_ner_id) {
			if (ner_id) 
				res.append(",true");
			else 
				res.append(",false");
		}
		if (Add_ner_type) {
			res.append(","+ner_type);
		}
		if (Add_ner_subtype) {
			res.append(","+ner_subtype);
		}
		if (Add_NERType_relatedness) {
			res.append("," + NERType_relatedness);
		}
		if (Add_NERType_relatedness) {
			res.append("," + NERSubType_relatedness);
		}
		if (Add_occ_term) {
			res.append("," + occ_term);
		}
		if (Add_dice_coef) {
			res.append("," + dice_coef);
		}
		
		// target variable - for training data (regression: 1.0 or 0.0 for training data)
		if (target_numeric)
			res.append("," + label);
		else
			res.append("," + classes);
			
		return res.toString();	
	}
	
	public double[] toVector() {
		double[] result = new double[this.getNumFeatures()];
		int i = 0;
		if (Add_prob_c) {
			result[i] = prob_c;
			i++;
		}

		if (Add_prob_i) {
			result[i] = prob_i;
			i++;
		}
			
		// frequency of usage of the string to refer to the concept in a reference corpus (usually wikipedia)
		if (Add_frequencyStrict) {
			result[i] = frequencyStrict;
			i++;
		}
			
		// frequency of usage of the concept
		if (Add_frequencyConcept) {
			result[i] = frequencyConcept;
			i++;
		}
		
		// term length
		if (Add_termLength) {
			result[i] = termLength;
			i++;
		}

		/*if (Add_inDictionary) {
			if (inDictionary) 
				result[i] = true;
			else 
				result[i] = false;
			i++;
		}*/
		
		if (Add_relatedness) {
			result[i] = relatedness;
			i++;
		}

		if (Add_context_quality) {
			result[i] = context_quality;
			i++;
		}
		
		/*if (Add_isSubTerm) {
			if (isSubTerm) 
				result[i] = true;
			else 
				result[i] = false;
			i++;
		}
		if (Add_ner_st) {
			if (ner_st) 
				result[i] = true;
			else 
				result[i] = false;
			i++;
		}
		if (Add_ner_id) {
			if (ner_id) 
				result[i] = true;
			else 
				result[i] = false;
			i++;
		}*/
		/*if (Add_ner_type) {
			result[i] = ner_type;
			i++;
		}
		if (Add_ner_subtype) {
			result[i] = ner_subtype;
			i++;
		}*/
		if (Add_NERType_relatedness) {
			result[i] = NERType_relatedness;
			i++;
		}
		if (Add_NERType_relatedness) {
			result[i] = NERSubType_relatedness;
			i++;
		}
		if (Add_occ_term) {
			result[i] = occ_term;
			i++;
		}
		if (Add_dice_coef) {
			result[i] = dice_coef;
			i++;
		}

		return result;
	}
}