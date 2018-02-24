package org.rookit.dm.track.similarity;

import static org.rookit.api.dm.track.TrackFields.*;

import java.util.Collections;
import java.util.Map;

import org.rookit.api.dm.track.Track;
import org.rookit.api.dm.track.VersionTrack;
import org.rookit.dm.genre.similarity.AbstractGenreableComparator;

@SuppressWarnings("javadoc")
public class TrackComparator extends AbstractGenreableComparator<Track> {

	private static final short BPM_THRESHOLD = 20;
	
	public TrackComparator() {
		this(DEFAULT_THESHOLD);
	}
	
	public TrackComparator(int threshold) {
		this(threshold, Collections.emptyMap());
	}
	
	public TrackComparator(Map<String, Float> percentages) {
		this(DEFAULT_THESHOLD, percentages);
	}

	public TrackComparator(int threshold, Map<String, Float> percentages) {
		super(threshold, percentages);
	}

	@Override
	protected Map<String, Double> createTopMap(Track element1, Track element2) {
		final Map<String, Double> scores = super.createTopMap(element1, element2);
		final double isTypeEquals = compareFromEquals(element1.getType(), element2.getType());
		if(isTypeEquals == 1) {
			return Collections.singletonMap(TYPE, isTypeEquals);
		}
		scores.put(BPM, compareBPM(element1.getBPM(), element2.getBPM()));
		scores.put(MAIN_ARTISTS, reverseIntersect(element1.getMainArtists(), element2.getMainArtists()));
		scores.put(FEATURES, reverseIntersect(element1.getFeatures(), element2.getFeatures()));
		scores.put(PRODUCERS, reverseIntersect(element1.getProducers(), element2.getProducers()));
		scores.put(TITLE, compareStringIgnoreCase(element1.getTitle().toString(), element2.getTitle().toString()));
		scores.put(HIDDEN_TRACK, compareStringIgnoreCase(element1.getHiddenTrack(), element2.getHiddenTrack()));
		if(element1.isVersionTrack() && element2.isVersionTrack()) {
			final VersionTrack version1 = element1.getAsVersionTrack();
			final VersionTrack version2 = element2.getAsVersionTrack();
			scores.put(VERSION_ARTISTS, reverseIntersect(version1.getVersionArtists(), version2.getVersionArtists()));
			scores.put(VERSION_TOKEN, compareStringIgnoreCase(version1.getVersionToken(), version2.getVersionToken()));
			scores.put(VERSION_TYPE, compareFromEquals(version1.getVersionType(), version2.getVersionType()));
			version1.getVersionType();
		}
		return scores;
	}

	private double compareBPM(short bpm, short bpm2) {
		final int diff = Math.abs(bpm-bpm2);
		if(diff > BPM_THRESHOLD) {
			return threshold;
		}
		return diff*threshold/(double) BPM_THRESHOLD;
	}

}
