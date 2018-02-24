package org.rookit.dm.album;

import org.rookit.api.dm.album.TrackSlot;
import org.rookit.api.dm.track.Track;

@SuppressWarnings("javadoc")
public class TrackSlotImpl implements TrackSlot {

	private final String disc;
	private final int number;
	private final Track track;
	
	TrackSlotImpl(String disc, int number, Track track) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((disc == null) ? 0 : disc.hashCode());
		result = prime * result + number;
		result = prime * result + ((track == null) ? 0 : track.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		TrackSlotImpl other = (TrackSlotImpl) obj;
		if (disc == null) {
			if (other.disc != null) {
				return false;
			}
		} else if (!disc.equals(other.disc)) {
			return false;
		}
		if (number != other.number) {
			return false;
		}
		if (track == null) {
			if (other.track != null) {
				return false;
			}
		} else if (!track.equals(other.track)) {
			return false;
		}
		return true;
	}
	
	
	
	
}
