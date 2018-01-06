package org.rookit.dm.similarity.calculator;

import org.rookit.dm.similarity.Similarity;

@SuppressWarnings("javadoc")
public interface SimilarityMeasure<T> {

	static <T> SimilarityMeasure<T> create(T base, Similarity<T> formula) {
		return new SimilarityMeasureImpl<>(base, formula);
	}
	
	SimilarityPlaceholder<T> measure(T target);
}
