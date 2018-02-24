package org.rookit.dm.album.similarity;

import static org.rookit.api.dm.album.AlbumFields.*;
import static org.rookit.api.dm.album.Album.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import org.rookit.api.dm.album.Album;
import org.rookit.dm.genre.similarity.AbstractGenreableComparator;

@SuppressWarnings("javadoc")
public class AlbumComparator extends AbstractGenreableComparator<Album> {

	public AlbumComparator() {
		this(DEFAULT_THESHOLD);
	}

	public AlbumComparator(int threshold) {
		this(threshold, Collections.emptyMap());
	}
	
	public AlbumComparator(Map<String, Float> percentages) {
		this(DEFAULT_THESHOLD, percentages);
	}

	public AlbumComparator(int threshold, Map<String, Float> percentages) {
		super(threshold, percentages);
	}
	
	private double compareDates(LocalDate d1, LocalDate d2) {
		if(Objects.equals(d1, d2)) {
			return 0;
		}
		final int maxMonths = 6;
		final Period period = Period.between(d1, d2);
		final long totalMonths = Math.abs(period.toTotalMonths());
		if(totalMonths <= maxMonths) {
			return totalMonths*threshold/ (double) maxMonths;
		}
		return threshold;
	}

	@Override
	protected Map<String, Double> createTopMap(Album element1, Album element2) {
		final Map<String, Double> scores = super.createTopMap(element1, element2);
		scores.put(TITLE, compareStringIgnoreCase(element1.getTitle(), element2.getTitle()));
		scores.put(TYPE, compareFromEquals(element1.getAlbumType(), element2.getAlbumType()));
		scores.put(RELEASE_DATE, compareDates(element1.getReleaseDate(), element2.getReleaseDate()));
		scores.put(ARTISTS, reverseIntersect(element1.getArtists(), element2.getArtists()));
		scores.put(ID, compareFromEquals(element1.getId(), element2.getId()));
		scores.put(RELEASE_TYPE, compareFromEquals(element1.getReleaseType(), element2.getReleaseType()));
		scores.put(TRACKS, reverseIntersect(element1.getTracks(), element2.getTracks()));

		element1.getExternalMetadata();
		
		return scores;
	}

}
