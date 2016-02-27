package edu.gslis.docscoring;

import java.util.Iterator;

import edu.gslis.queries.GQuery;
import edu.gslis.searchhits.SearchHit;

public class ScorerJelinekMercer extends QueryDocScorer {
	
	public static final String MIXING_PARAM = "lambda";
	public double epsilon = 1.0;
	
	public ScorerJelinekMercer() {
		setParameter(MIXING_PARAM, 0.5);
	}
	
	public void setQuery(GQuery query) {
		this.gQuery = query;
	}

	public double score(SearchHit doc) {
		double logLikelihood = 0.0;
		Iterator<String> termIterator = gQuery.getFeatureVector().iterator();
		while (termIterator.hasNext()) {
			String term = termIterator.next();
			
			double docProb = doc.getFeatureVector().getFeatureWeight(term) / doc.getLength();
			double collectionProb = (epsilon + collectionStats.termCount(term)) / collectionStats.getTokCount();
			
			double pr = (1-paramTable.get(MIXING_PARAM))*docProb + paramTable.get(MIXING_PARAM)*collectionProb;
			
			double queryWeight = gQuery.getFeatureVector().getFeatureWeight(term);
			logLikelihood += queryWeight*Math.log(pr);
		}
		return logLikelihood;
	}

}
