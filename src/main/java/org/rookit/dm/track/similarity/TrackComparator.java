package org.rookit.dm.track.similarity;

import static org.rookit.dm.track.DatabaseFields.*;

import java.util.Collections;
import java.util.Map;

import org.rookit.dm.genre.similarity.AbstractGenreableComparator;
import org.rookit.dm.track.Track;
import org.rookit.dm.track.VersionTrack;

@SuppressWarnings("javadoc")
public class TrackComparator extends AbstractGenreableComparator<Track> {
	
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
	protected Map<String, Integer> createTopMap(Track element1, Track element2) {
		final Map<String, Integer> scores = super.createTopMap(element1, element2);
		scores.put(BPM, compareBPM(element1.getBPM(), element2.getBPM()));
		scores.put(MAIN_ARTISTS, reverseIntersect(element1.getMainArtists(), element2.getMainArtists()));
		scores.put(FEATURES, reverseIntersect(element1.getFeatures(), element2.getFeatures()));
		scores.put(PRODUCERS, reverseIntersect(element1.getProducers(), element2.getProducers()));
		scores.put(TITLE, compareStringIgnoreCase(element1.getTitle().toString(), element2.getTitle().toString()));
		scores.put(HIDDEN_TRACK, compareStringIgnoreCase(element1.getHiddenTrack(), element2.getHiddenTrack()));
		scores.put(TYPE, compareFromEquals(element1.getType(), element2.getType()));
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

	private Integer compareBPM(short bpm, short bpm2) {
		final short maxBPM = 20;
		final int diff = Math.abs(bpm-bpm2);
		if(diff > maxBPM) {
			return threshold;
		}
		return diff*threshold/maxBPM;
	}

}
