package edu.gslis.docscoring;

import edu.gslis.queries.GQuery;
import edu.gslis.searchhits.SearchHit;
import edu.gslis.textrepresentation.FeatureVector;

public class TermScorer {
	
	private QueryDocScorer scorer;
	
	public TermScorer(QueryDocScorer scorer) {
		setScorer(scorer);
	}
	
	public void setScorer(QueryDocScorer scorer) {
		this.scorer = scorer;
	}
	
	public double score(SearchHit doc, String term) {
		FeatureVector fv = new FeatureVector(null);
		fv.addTerm(term);

		GQuery oldQuery = scorer.gQuery;
		
		GQuery newQuery = new GQuery();
		newQuery.setFeatureVector(fv);
		scorer.setQuery(newQuery);
		
		double termScore = scorer.score(doc);
		
		// Revert back to the original query in case we want to reuse this scorer elsewhere
		scorer.setQuery(oldQuery);
		
		return termScore;
	}

}
