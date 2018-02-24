package org.rookit.dm.artist.similarity;

import static org.rookit.api.dm.artist.ArtistFields.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.Map;

import org.rookit.api.dm.artist.Artist;
import org.rookit.dm.genre.similarity.AbstractGenreableComparator;

@SuppressWarnings("javadoc")
public class ArtistComparator extends AbstractGenreableComparator<Artist> {

	public ArtistComparator() {
		this(DEFAULT_THESHOLD);
	}

	public ArtistComparator(int threshold) {
		this(threshold, Collections.emptyMap());
	}
	
	public ArtistComparator(Map<String, Float> percentages) {
		this(DEFAULT_THESHOLD, percentages);
	}

	public ArtistComparator(int threshold, Map<String, Float> percentages) {
		super(threshold, percentages);
	}

	@Override
	protected Map<String, Double> createTopMap(Artist element1, Artist element2) {
		final Map<String, Double> scores = super.createTopMap(element1, element2);
		scores.put(ALIASES, reverseIntersect(element1.getAliases(), element2.getAliases()));
		scores.put(BEGIN_DATE, compareDate(element1.getBeginDate(), element2.getBeginDate()));
		scores.put(END_DATE, compareDate(element1.getEndDate(), element2.getEndDate()));
		scores.put(IPI, compareFromEquals(element1.getIPI(), element2.getIPI()));
		scores.put(ISNI, compareFromEquals(element1.getISNI(), element2.getISNI()));
		scores.put(NAME, compareStringIgnoreCase(element1.getName(), element2.getName()));
		scores.put(ORIGIN, compareFromEquals(element1.getOrigin(), element2.getOrigin()));
		scores.put(TYPE, compareFromEquals(element1.getType(), element2.getType()));
		return scores;
	}

	private double compareDate(LocalDate beginDate, LocalDate beginDate2) {
		final Period period = Period.between(beginDate, beginDate2);
		final long totalMonths = Math.abs(period.toTotalMonths());
		final int maxMonths = 12;
		if(totalMonths > maxMonths) {
			return threshold;
		}
		return totalMonths*threshold/(double) maxMonths;
	}

}
