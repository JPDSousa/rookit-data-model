package org.rookit.dm.similarity.calculator;

class SimilarityPlaceholderImpl<T> implements SimilarityPlaceholder<T> {

	private final double distance;
	private final T target;
	
	SimilarityPlaceholderImpl(double distance, T target) {
		super();
		this.distance = distance;
		this.target = target;
	}

	@Override
	public double getDistance() {
		return distance;
	}

	@Override
	public T getTarget() {
		return target;
	}
}
