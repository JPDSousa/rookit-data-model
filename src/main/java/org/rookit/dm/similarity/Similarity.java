package org.rookit.dm.similarity;

@SuppressWarnings("javadoc")
public interface Similarity<T> {

	double similarity(T a, T b);
}
