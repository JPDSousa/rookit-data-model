package org.rookit.dm.genre.similarity;

import static org.rookit.dm.genre.Genreable.GENRES;

import java.util.Map;

import org.rookit.dm.genre.Genreable;
import org.rookit.dm.play.similarity.AbstractPlayableComparator;

@SuppressWarnings("javadoc")
public abstract class AbstractGenreableComparator<T extends Genreable> extends AbstractPlayableComparator<T> {

	protected AbstractGenreableComparator(int threshold, Map<String, Float> percentages) {
		super(threshold, percentages);
	}

	@Override
	protected Map<String, Double> createTopMap(T element1, T element2) {
		final Map<String, Double> scores = super.createTopMap(element1, element2);
		scores.put(GENRES, reverseIntersect(element1.getGenres(), element2.getGenres()));
		
		return scores;
	}

}
