package org.rookit.dm.play;

import org.rookit.dm.track.audio.AudioFeature;
import org.rookit.dm.track.audio.AudioFeatureSetter;

@SuppressWarnings("javadoc")
public interface DynamicPlaylist extends AudioFeature, Playlist, PlaylistSetter<Void>, AudioFeatureSetter<Void> {

	StaticPlaylist freeze();
	
	StaticPlaylist freeze(int limit);
}
