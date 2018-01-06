package org.rookit.dm.similarity.calculator;

@SuppressWarnings("javadoc")
public interface SimilarityPlaceholder<T> {

	double getDistance();
	
	T getTarget();
	
}
