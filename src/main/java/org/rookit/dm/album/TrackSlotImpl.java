package org.rookit.dm.album;

import java.util.Objects;
import java.util.Optional;

import org.rookit.api.dm.album.TrackSlot;
import org.rookit.api.dm.track.Track;
import org.rookit.dm.utils.DataModelValidator;

import javax.annotation.Generated;
import com.google.common.base.MoreObjects;

@SuppressWarnings("javadoc")
public class TrackSlotImpl implements TrackSlot {
	
	private static final DataModelValidator VALIDATOR = DataModelValidator.getDefault();

	private final String disc;
	private final int number;
	private final Track track;
	
	TrackSlotImpl(String disc, int number, Track track) {
		super();
		VALIDATOR.checkArgument().isNotEmpty(disc, "disc");
		VALIDATOR.checkArgument().isPositive(number, "number");
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
	public Optional<Track> getTrack() {
		return Optional.ofNullable(this.track);
	}

	@Override
	public boolean isEmpty() {
		return Objects.isNull(this.track);
	}

	@Override
	@Generated(value = "GuavaEclipsePlugin")
	public String toString() {
		return MoreObjects.toStringHelper(this).add("super", super.toString()).add("disc", disc).add("number", number)
				.add("track", track).toString();
	}

	@Override
	@Generated(value = "GuavaEclipsePlugin")
	public int hashCode() {
		return Objects.hash(super.hashCode(), disc, number, track);
	}

	@Override
	@Generated(value = "GuavaEclipsePlugin")
	public boolean equals(Object object) {
		if (object instanceof TrackSlotImpl) {
			TrackSlotImpl that = (TrackSlotImpl) object;
			return Objects.equals(this.disc, that.disc) && this.number == that.number
					&& Objects.equals(this.track, that.track);
		}
		return false;
	}
	
}
