package com.scienceminer.nerd.utilities;

import java.util.Map;

/**
 * This class is a bean for the YAML configuation data associated to a 
 * language specific NERD.  
 *
 * For each language, we will have a YAML configuration and an instance 
 * of this class. 
 */
public class NerdConfig {
	// ISO 2-digit language code 
	private String langCode;

	// path to the LMDB data
	private String dbDirectory;

	// path to the compiled CSV wikipedia files
	private String dataDirectory;

	// path to the stopwords file to consider for the target language
	// the file is a text file with one stopword per line
	private String stopwords;

	// path to the nerd ranker model	
	private String rankerModel;

	// path to the nerd selector model
	private String selectorModel;

	// minimum of inverse probability (e.g. probability of a string to 
	// realize a given entity considering all the possible strings 
	// that can realize this entity)
	private float minLinkProbability = 0;
	
	// minimum conditional probability of a sense for being an entity 
	// candidate (e.g. min probability of an entity to be realized by a 
	// given string, given all possible entities that can be realized 
	// by this string)
	private float minSenseProbability = 0;

	// if true, use links out for computing relatedness between two 
	// entities in addition to the links in (slower but more precise 
	// in principle)
	private boolean useLinkOut = false;

	public String getLangCode() {
		return langCode;
	}

	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

	public String getDbDirectory() {
		return dbDirectory;
	}

	public void setDbDirectory(String dbDirectory) {
		this.dbDirectory = dbDirectory;
	}	

	public String getDataDirectory() {
		return dataDirectory;
	}

	public void setDataDirectory(String dataDirectory) {
		this.dataDirectory = dataDirectory;
	}	

	public String getStopwords() {
		return stopwords;
	}

	public void setStopwords(String stopwords) {
		this.stopwords = stopwords;
	}

	public String getRankerModel() {
		return rankerModel;
	}

	public void setRankerModel(String rankerModel) {
		this.rankerModel = rankerModel;
	}

	public String getSelectorModel() {
		return selectorModel;
	}

	public void setSelectorModel(String selectorModel) {
		this.selectorModel = selectorModel;
	}

	public float getMinLinkProbability() {
		return minLinkProbability;
	}

	public void setMinLinkProbability(float minLinkProbability) {
		this.minLinkProbability = minLinkProbability;
	}

	public float getMinSenseProbability() {
		return minSenseProbability;
	}

	public void setMinSenseProbability(float minSenseProbability) {
		this.minSenseProbability = minSenseProbability;
	}

	public boolean getUseLinkOut() {
		return useLinkOut;
	}

	public void setUseLinkOut(boolean useLinkOut) {
		this.useLinkOut = useLinkOut;
	}

}