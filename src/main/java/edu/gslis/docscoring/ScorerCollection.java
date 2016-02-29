package edu.gslis.docscoring;

import java.util.Iterator;

import edu.gslis.queries.GQuery;
import edu.gslis.searchhits.SearchHit;

public class ScorerCollection extends QueryDocScorer {
	
	private double epsilon = 1.0;
	
	public void setQuery(GQuery query) {
		this.gQuery = query;
	}
	
	public double score(SearchHit doc) {
		double logLikelihood = 0.0;
		Iterator<String> termIterator = gQuery.getFeatureVector().iterator();
		while (termIterator.hasNext()) {
			String term = termIterator.next();
			
			double collectionProb = (epsilon + collectionStats.termCount(term)) / collectionStats.getTokCount();
			
			double queryWeight = gQuery.getFeatureVector().getFeatureWeight(term);
			logLikelihood += queryWeight*Math.log(collectionProb);
		}
		return logLikelihood;
	}

}
