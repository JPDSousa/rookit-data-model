package org.rookit.dm.playlist;

import java.util.stream.Stream;

import org.rookit.dm.playable.Playable;
import org.rookit.dm.track.Track;
import org.smof.element.Element;

@SuppressWarnings("javadoc")
public interface Playlist extends Element, Playable {
	
	String getName();
	
	Stream<Track> streamTracks();
	
	/**
	 * Returns the list of tracks with all the tracks that compose the playlist.
	 * 
	 * @return a list of tracks with all the tracks of the playlist
	 */
	Iterable<Track> getTracks();
	
	void addTrack(Track track);

}
