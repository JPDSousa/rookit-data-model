package org.rookit.dm.similarity;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.rookit.api.dm.MetadataHolder;
import org.rookit.dm.utils.DataModelValidator;

import com.google.common.collect.Maps;

@SuppressWarnings("javadoc")
public abstract class AbstractSimilarity<T extends MetadataHolder> implements Similarity<T> {

	private static final int LEV_DIS_THRESHOLD = 12;
	protected static final int DEFAULT_THESHOLD = 1;
	
	protected final DataModelValidator validator;
	protected final Map<String, Float> percentages;
	private final LevenshteinDistance distance;
	protected final int threshold;
	
	protected AbstractSimilarity(Map<String, Float> percentages) {
		this(DEFAULT_THESHOLD, percentages);
	}
	
	protected AbstractSimilarity(int threshold, Map<String, Float> percentages) {
		super();
		validator = DataModelValidator.getDefault();
		distance = LevenshteinDistance.getDefaultInstance();
		final Map<String, Float> percentagesImmutable = Collections.unmodifiableMap(percentages);
		if(!percentagesImmutable.isEmpty()) {
			validator.checkSumIs(percentagesImmutable.values(), 1);
		}
		this.threshold = threshold;
		this.percentages = percentagesImmutable;
	}
	
	protected final double applyPercentages(Map<String, Double> scores) {
		if(percentages.isEmpty()) {
			return scores.values().stream()
					.mapToDouble(Double::doubleValue)
					.average()
					.orElse(1);
		}
		double score = 0;
		for(String key : scores.keySet()) {
			score += percentages.get(key)*scores.get(key);
		}
		return score;
	}
	
	protected final <E> double reverseIntersect(Collection<E> col1, Collection<E> col2) {
		final int maxSize = Math.max(col1.size(), col2.size());
		final int result = maxSize - CollectionUtils.intersection(col1, col2).size();
		return Math.min(result, threshold);
	}
	
	protected Map<String, Double> createTopMap(T element1, T element2) {
		final Map<String, Double> scores = Maps.newLinkedHashMap();
		//scores.put(ID, compareFromEquals(element1.getId(), element2.getId()));
		// TODO compare these fields
		element1.getExternalMetadata();
		element2.getExternalMetadata();
		return scores;
	}
	
	protected final <O> double compareFromEquals(O t1, O t2) {
		return Objects.equals(t1, t2) ? 0 : threshold;
	}

	private double apply(String str1, String str2) {
		final int distance = Math.min(this.distance.apply(str1, str2), LEV_DIS_THRESHOLD);
		return Math.min(distance/ (double) LEV_DIS_THRESHOLD, threshold);
	}
	
	protected final double compareString(String str1, String str2) {
		return apply(str1, str2);
	}
	
	protected final double compareStringIgnoreCase(String str1, String str2) {
		return apply(str1.toLowerCase(), str2.toLowerCase());
	}

	@Override
	public double similarity(T o1, T o2) {
		if(o1 == o2) {
			return 0;
		}
		return applyPercentages(createTopMap(o1, o2));
	}

}
