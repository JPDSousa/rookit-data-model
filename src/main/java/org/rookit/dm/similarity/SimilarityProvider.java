package org.rookit.dm.similarity;

import org.rookit.api.dm.RookitModel;
import org.rookit.dm.similarity.calculator.SimilarityMeasure;

@SuppressWarnings("javadoc")
public interface SimilarityProvider {

	static SimilarityProvider create() {
		return new SimilarityProviderImpl();
	}
	
	<T extends RookitModel> SimilarityMeasure<T> getMeasure(Class<T> clazz, T base);
}
