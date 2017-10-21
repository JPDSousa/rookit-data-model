package org.rookit.dm.play;

import org.rookit.dm.track.Track;

@SuppressWarnings("javadoc")
public interface PlaylistSetter<T> {

	T addTrack(Track track);

	T setImage(byte[] image);

}
