package org.rookit.dm.genre.similarity;

import static org.rookit.api.dm.genre.Genreable.GENRES;

import java.util.Map;
import java.util.function.BiFunction;

import org.rookit.api.dm.genre.Genreable;
import org.rookit.dm.play.similarity.AbstractPlayableComparator;

import com.google.common.base.Optional;

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

	protected <T> double compareOptionals(final Optional<T> opt1, final Optional<T> opt2, final BiFunction<T, T, Double> comparator) {
		if (opt1.isPresent() && opt2.isPresent()) {
			return comparator.apply(opt1.get(), opt2.get());
		} else if (opt1.isPresent() ^ opt2.isPresent()) {
			return this.threshold / 2;
		} else {
			return 0;
		}
	}

}
