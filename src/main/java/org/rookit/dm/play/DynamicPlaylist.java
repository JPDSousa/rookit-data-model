package org.rookit.dm.play;

import org.rookit.dm.track.audio.AudioFeature;

@SuppressWarnings("javadoc")
public interface DynamicPlaylist extends AudioFeature<Void>, Playlist, PlaylistSetter<Void> {

	StaticPlaylist freeze();
	
	StaticPlaylist freeze(int limit);
	
}
