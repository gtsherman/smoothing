package edu.gslis.similarity;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import edu.gslis.textrepresentation.FeatureVector;

public class CosineSimilarityScorer {
	
	private FeatureVector doc;
	private FeatureVector otherdoc;
	
	public CosineSimilarityScorer(FeatureVector doc, FeatureVector otherdoc) {
		setDocs(doc, otherdoc);
	}
	
	public void setDocs(FeatureVector doc, FeatureVector otherdoc) {
		this.doc = doc;
		this.otherdoc = otherdoc;
	}
	
	public FeatureVector[] getDocs() {
		FeatureVector[] docs = {this.doc, this.otherdoc};
		return docs;
	}
	
	public double score() {
		/*double num = 0.000001;
		double denomX = 0.000001;
		double denomY = 0.000001;
		*/
		double num = 0.0;
		double denomX = 0.0;
		double denomY = 0.0;
		
		Set<String> vocab = new HashSet<String>();
		vocab.addAll(doc.getFeatures());
		vocab.addAll(otherdoc.getFeatures());
		System.err.println("Doc length 1: "+doc.getFeatures().size()+", Doc length 2:" +otherdoc.getFeatures().size()+", Total: "+vocab.size());
		
		Iterator<String> termIt = vocab.iterator();
		String term;
		while (termIt.hasNext()) {
			term = termIt.next();
			
			double docWeight = doc.getFeatureWeight(term);
			double otherdocWeight = otherdoc.getFeatureWeight(term); 
			
			num += docWeight * otherdocWeight;
			denomX += docWeight * docWeight;
			denomY += otherdocWeight * otherdocWeight;
		}
		double denom = Math.sqrt(denomX)*Math.sqrt(denomY);
		if (denom == 0) denom = 1;
		return num/denom;
	}
}
