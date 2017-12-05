package org.rookit.dm.play;

import java.util.stream.Stream;

import org.rookit.dm.track.Track;
import org.smof.annnotations.SmofIndex;
import org.smof.annnotations.SmofIndexField;
import org.smof.annnotations.SmofIndexes;
import org.smof.gridfs.SmofGridRef;
import org.smof.index.IndexType;

@SmofIndexes({
	@SmofIndex(fields = {@SmofIndexField(name = DatabaseFields.NAME, type = IndexType.ASCENDING)}),
})
@SuppressWarnings("javadoc")
public interface Playlist extends Playable, PlaylistSetter<Void> {

	String IMAGE_BUCKET = "Playlist_Images";

	String getName();
	
	SmofGridRef getImage();

	Stream<Track> streamTracks();
	
	Playlist intersectWith(Playlist other);
	
	Playlist joinWith(Playlist other);

}
