package org.rookit.dm.similarity;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

import org.rookit.dm.RookitModel;
import org.rookit.dm.utils.DataModelValidator;

@SuppressWarnings("javadoc")
public abstract class AbstractSimilarityDistance<T extends RookitModel> implements Comparator<T> {

	protected final DataModelValidator validator;
	private final Map<String, Float> percentages;

	protected AbstractSimilarityDistance() {
		this(Collections.emptyMap());
	}
	
	protected AbstractSimilarityDistance(Map<String, Float> percentages) {
		super();
		validator = DataModelValidator.getDefault();
		final Map<String, Float> percentagesImmutable = Collections.unmodifiableMap(percentages);
		if(!percentagesImmutable.isEmpty()) {
			validator.checkSumIs(percentagesImmutable.values(), 1);
		}
		this.percentages = percentagesImmutable;
	}

	protected int applyPercentages(Map<String, Integer> scores) {
		if(percentages.isEmpty()) {
			return (int) Math.round(scores.values().stream().mapToInt(Integer::intValue).average().orElse(1));
		}
		float score = 0;
		for(String key : scores.keySet()) {
			score += percentages.get(key)*scores.get(key);
		}
		return Math.round(score);
	}

}
