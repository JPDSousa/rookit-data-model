package org.rookit.dm.play;

import java.util.stream.Stream;

import org.rookit.dm.track.Track;
import org.smof.annnotations.SmofIndex;
import org.smof.annnotations.SmofIndexField;
import org.smof.annnotations.SmofIndexes;
import org.smof.element.Element;
import org.smof.index.IndexType;

@SuppressWarnings("javadoc")
@SmofIndexes({
	@SmofIndex(fields = {@SmofIndexField(name = DatabaseFields.NAME, type = IndexType.ASCENDING)}),
})
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
	
	boolean removeTrack(Track track);

}
