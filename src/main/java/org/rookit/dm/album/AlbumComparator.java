package org.rookit.dm.album;

import static org.rookit.dm.album.DatabaseFields.*;
import static org.rookit.dm.album.Album.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.rookit.dm.similarity.AbstractSimilarityDistance;

import com.google.common.collect.Maps;

@SuppressWarnings("javadoc")
public class AlbumComparator extends AbstractSimilarityDistance<Album> {

	private static final int DEFAULT_THESHOLD = 100;
	
	private final LevenshteinDistance distance;
	private final int threshold;

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
		super(percentages);
		this.threshold = threshold;
		distance = LevenshteinDistance.getDefaultInstance();
	}

	@Override
	public int compare(Album o1, Album o2) {
		final Map<String, Integer> scores = Maps.newHashMapWithExpectedSize(9);
		final int title = distance.apply(o1.getTitle(), o2.getTitle());
		if(!valid(title)) {
			return 1;
		}
		scores.put(TITLE, title);
		scores.put(TYPE, compareFromEquals(o1.getAlbumType(), o2.getAlbumType()));
		scores.put(RELEASE_DATE, compareDates(o1.getReleaseDate(), o2.getReleaseDate()));
		scores.put(GENRES, reverseIntersect(o1.getGenres(), o2.getGenres()));
		scores.put(ARTISTS, reverseIntersect(o1.getArtists(), o2.getArtists()));
		scores.put(ID, compareFromEquals(o1.getId(), o2.getId()));
		scores.put(RELEASE_TYPE, compareFromEquals(o1.getReleaseType(), o2.getReleaseType()));
		scores.put(TRACKS, reverseIntersect(o1.getTracks(), o2.getTracks()));

		o1.getExternalMetadata();
		return applyPercentages(scores);
	}

	private <T> int reverseIntersect(Collection<T> col1, Collection<T> col2) {
		final int maxSize = Math.max(col1.size(), col2.size());
		final int result = maxSize - CollectionUtils.intersection(col1, col2).size();
		return Math.min(result, threshold);
	}

	private int compareDates(LocalDate d1, LocalDate d2) {
		if(Objects.equals(d1, d2)) {
			return 0;
		}
		final int maxMonths = 6;
		final Period period = Period.between(d1, d2);
		final long totalMonths = Math.abs(period.toTotalMonths());
		if(totalMonths <= maxMonths) {
			return (int) (totalMonths*threshold/maxMonths);
		}
		return threshold;
	}

	private int compareFromEquals(Object t1, Object t2) {
		return Objects.equals(t1, t2) ? 0 : 1;
	}

	private boolean valid(int similarity) {
		return similarity <= threshold;
	}

}
