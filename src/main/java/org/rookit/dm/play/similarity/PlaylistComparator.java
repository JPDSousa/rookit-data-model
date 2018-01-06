package org.rookit.dm.play.similarity;

import static org.rookit.dm.play.DatabaseFields.*;

import java.util.Collections;
import java.util.Map;

import org.rookit.dm.play.StaticPlaylist;

@SuppressWarnings("javadoc")
public class PlaylistComparator extends AbstractPlayableComparator<StaticPlaylist> {
	
	public PlaylistComparator() {
		this(DEFAULT_THESHOLD);
	}

	public PlaylistComparator(int threshold) {
		this(threshold, Collections.emptyMap());
	}
	
	public PlaylistComparator(Map<String, Float> percentages) {
		this(DEFAULT_THESHOLD, percentages);
	}

	public PlaylistComparator(int threshold, Map<String, Float> percentages) {
		super(threshold, percentages);
	}

	@Override
	protected Map<String, Double> createTopMap(StaticPlaylist element1, StaticPlaylist element2) {
		final Map<String, Double> scores = super.createTopMap(element1, element2);
		scores.put(NAME, compareStringIgnoreCase(element1.getName(), element2.getName()));
		scores.put(TRACKS, reverseIntersect(element1.getTracks(), element2.getTracks()));
		return scores;
	}

}
