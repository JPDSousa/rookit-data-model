package org.rookit.dm.similarity.calculator;

import org.rookit.dm.similarity.Similarity;

class SimilarityMeasureImpl<T> implements SimilarityMeasure<T> {

	private final T base;
	private final Similarity<T> calculator;
	
	SimilarityMeasureImpl(T base, Similarity<T> calculator) {
		super();
		this.base = base;
		this.calculator = calculator;
	}

	@Override
	public SimilarityPlaceholder<T> measure(T target) {
		final double similarity = Math.abs(calculator.similarity(base, target));
		return new SimilarityPlaceholderImpl<>(similarity, target);
	}
	
	
	
}
