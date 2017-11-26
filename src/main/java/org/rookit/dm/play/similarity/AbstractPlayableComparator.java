package org.rookit.dm.play.similarity;

import java.util.Map;

import org.rookit.dm.play.Playable;
import org.rookit.dm.similarity.AbstractSimilarityDistance;

@SuppressWarnings("javadoc")
public class AbstractPlayableComparator<T extends Playable> extends AbstractSimilarityDistance<T> {

	protected AbstractPlayableComparator(int threshold, Map<String, Float> percentages) {
		super(threshold, percentages);
	}

	@Override
	protected Map<String, Integer> createTopMap(T element1, T element2) {
		// all fields in playable are ignored in a comparison
		return super.createTopMap(element1, element2);
	}
	

}
