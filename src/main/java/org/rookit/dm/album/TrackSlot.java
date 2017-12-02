package org.rookit.dm.album;

import org.rookit.dm.track.Track;

@SuppressWarnings("javadoc")
public interface TrackSlot {

	static TrackSlot create(String disc, int number, Track track) {
		return new TrackSlotImpl(disc, number, track);
	}

	String getDisc();

	int getNumber();

	Track getTrack();
	
	boolean isEmpty();
}
