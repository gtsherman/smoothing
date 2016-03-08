package edu.gslis.docscoring;

import java.util.Iterator;

import edu.gslis.searchhits.SearchHit;

public class ScorerTwoStage extends QueryDocScorer {
	
	public static final String MIXING_PARAM = "lambda";
	private static double defaultMixingParam = 0.5;

	private ScorerDirichlet dirichlet;
	private QueryDocScorer noise;

	public ScorerTwoStage(ScorerDirichlet dirichlet, QueryDocScorer noise) {
		setDirichletScorer(dirichlet);
		setNoiseScorer(noise);
		setParameter(MIXING_PARAM, defaultMixingParam);
	}
	
	public ScorerDirichlet getDirichletScorer() {
		return dirichlet;
	}
	
	public void setDirichletScorer(ScorerDirichlet dirichlet) {
		this.dirichlet = dirichlet;
	}
	
	public QueryDocScorer getNoiseScorer() {
		return noise;
	}
	
	public void setNoiseScorer(QueryDocScorer noise) {
		this.noise = noise;
	}
	
	public double score(SearchHit doc) {
		double logLikelihood = 0.0;
		Iterator<String> termIterator = gQuery.getFeatureVector().iterator();
		while (termIterator.hasNext()) {
			String term = termIterator.next();
			
			TermScorer termScorer = new TermScorer(dirichlet);
			double dirichletScore = termScorer.score(doc, term);
			
			termScorer.setScorer(noise);
			double noiseScore = termScorer.score(doc, term);
			
			double pr = (1-paramTable.get(MIXING_PARAM))*dirichletScore + paramTable.get(MIXING_PARAM)*noiseScore;
			
			double queryWeight = gQuery.getFeatureVector().getFeatureWeight(term);
			logLikelihood += queryWeight*Math.log(pr);
		}
		return logLikelihood;
	}

}
