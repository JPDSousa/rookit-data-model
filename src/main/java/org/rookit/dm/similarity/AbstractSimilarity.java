package org.rookit.dm.similarity;

import static org.rookit.dm.RookitModel.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.rookit.dm.MetadataHolder;
import org.rookit.dm.utils.DataModelValidator;

import com.google.common.collect.Maps;

@SuppressWarnings("javadoc")
public abstract class AbstractSimilarityDistance<T extends MetadataHolder> implements Comparator<T> {

	protected static final int DEFAULT_THESHOLD = 100;
	
	protected final DataModelValidator validator;
	protected final Map<String, Float> percentages;
	private final LevenshteinDistance distance;
	protected final int threshold;
	
	protected AbstractSimilarityDistance(int threshold, Map<String, Float> percentages) {
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
	
	protected final int applyPercentages(Map<String, Integer> scores) {
		if(percentages.isEmpty()) {
			return (int) Math.round(scores.values().stream().mapToInt(Integer::intValue).average().orElse(1));
		}
		float score = 0;
		for(String key : scores.keySet()) {
			score += percentages.get(key)*scores.get(key);
		}
		return Math.round(score);
	}
	
	protected final <E> int reverseIntersect(Collection<E> col1, Collection<E> col2) {
		final int maxSize = Math.max(col1.size(), col2.size());
		final int result = maxSize - CollectionUtils.intersection(col1, col2).size();
		return Math.min(result, threshold);
	}
	
	protected Map<String, Integer> createTopMap(T element1, T element2) {
		final Map<String, Integer> scores = Maps.newLinkedHashMap();
		scores.put(ID, compareFromEquals(element1.getId(), element2.getId()));
		// TODO compare these fields
		element1.getExternalMetadata();
		element2.getExternalMetadata();
		return scores;
	}
	
	protected final <O> int compareFromEquals(O t1, O t2) {
		return Objects.equals(t1, t2) ? 0 : 1;
	}

	protected final int apply(String str1, String str2) {
		final int distance = this.distance.apply(str1, str2);
		return Math.min(distance*10, threshold);
	}
	
	protected final int compareString(String str1, String str2) {
		return distance.apply(str1, str2);
	}
	
	protected final int compareStringIgnoreCase(String str1, String str2) {
		return apply(str1.toLowerCase(), str2.toLowerCase());
	}

	@Override
	public int compare(T o1, T o2) {
		return applyPercentages(createTopMap(o1, o2));
	}

}
