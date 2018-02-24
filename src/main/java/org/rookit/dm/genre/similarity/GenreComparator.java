package org.rookit.dm.genre.similarity;

import static org.rookit.api.dm.genre.GenreFields.*;

import java.util.Collections;
import java.util.Map;

import org.rookit.api.dm.genre.Genre;
import org.rookit.dm.play.similarity.AbstractPlayableComparator;

@SuppressWarnings("javadoc")
public class GenreComparator extends AbstractPlayableComparator<Genre> {

	public GenreComparator() {
		this(DEFAULT_THESHOLD);
	}

	public GenreComparator(int threshold) {
		this(threshold, Collections.emptyMap());
	}
	
	public GenreComparator(Map<String, Float> percentages) {
		this(DEFAULT_THESHOLD, percentages);
	}

	public GenreComparator(int threshold, Map<String, Float> percentages) {
		super(threshold, percentages);
	}

	@Override
	protected Map<String, Double> createTopMap(Genre element1, Genre element2) {
		final Map<String, Double> scores = super.createTopMap(element1, element2);
		scores.put(NAME, compareStringIgnoreCase(element1.getName(), element2.getName()));
		return scores;
	}

}
