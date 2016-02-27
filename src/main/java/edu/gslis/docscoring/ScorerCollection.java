package edu.gslis.docscoring;

public class ScorerCollection extends ScorerJelinekMercer {
	
	public ScorerCollection() {
		/* Simply reuse a Jelinek-Mercer scorer (through inheritance) with
		 * all the weight on the collection probability.
		 */
		setParameter(MIXING_PARAM, 1.0);
	}

}
