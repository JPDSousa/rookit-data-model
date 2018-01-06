package org.rookit.dm.play;

import java.util.stream.Stream;

import org.mongodb.morphia.annotations.Entity;
import org.rookit.dm.play.able.Playable;
import org.rookit.dm.track.Track;
import org.rookit.dm.utils.bistream.BiStream;

//@SmofIndexes({
//	@SmofIndex(fields = {@SmofIndexField(name = DatabaseFields.NAME, type = IndexType.ASCENDING)}),
//})
@Entity("Playlist")
@SuppressWarnings("javadoc")
public interface Playlist extends Playable, PlaylistSetter<Void> {

	String IMAGE_BUCKET = "Playlist_Images";

	String getName();
	
	BiStream getImage();

	Stream<Track> streamTracks();
	
	Playlist intersectWith(Playlist other);
	
	Playlist joinWith(Playlist other);
	
	TypePlaylist getType();

}
