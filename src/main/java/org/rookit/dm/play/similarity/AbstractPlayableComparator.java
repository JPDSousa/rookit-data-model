package org.rookit.dm.play.similarity;

import java.util.Map;

import org.rookit.api.dm.play.able.Playable;
import org.rookit.dm.similarity.AbstractSimilarity;

@SuppressWarnings("javadoc")
public class AbstractPlayableComparator<T extends Playable> extends AbstractSimilarity<T> {

	protected AbstractPlayableComparator(int threshold, Map<String, Float> percentages) {
		super(threshold, percentages);
	}

	@Override
	protected Map<String, Double> createTopMap(T element1, T element2) {
		// all fields in playable are ignored in a comparison
		return super.createTopMap(element1, element2);
	}
	

}
