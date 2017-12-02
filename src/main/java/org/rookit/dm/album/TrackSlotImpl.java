package org.rookit.dm.album;

import org.rookit.dm.track.Track;

@SuppressWarnings("javadoc")
public class TrackSlotImpl implements TrackSlot {

	private final String disc;
	private final int number;
	private final Track track;
	
	public TrackSlotImpl(String disc, int number, Track track) {
		super();
		assert disc != null && !disc.isEmpty();
		assert number > 0;
		this.disc = disc;
		this.number = number;
		this.track = track;
	}

	@Override
	public String getDisc() {
		return disc;
	}

	@Override
	public int getNumber() {
		return number;
	}

	@Override
	public Track getTrack() {
		return track;
	}

	@Override
	public boolean isEmpty() {
		return track == null;
	}
	
	
}
