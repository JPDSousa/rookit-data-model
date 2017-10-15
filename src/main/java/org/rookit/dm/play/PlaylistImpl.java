package org.rookit.dm.play;

import java.util.Set;
import java.util.stream.Stream;

import org.rookit.dm.track.Track;
import org.smof.annnotations.SmofArray;
import org.smof.annnotations.SmofString;
import org.smof.parsers.SmofType;

import com.google.common.collect.Sets;

import static org.rookit.dm.play.DatabaseFields.*;

class PlaylistImpl extends AbstractPlayable implements Playlist {
	
	@SmofString(name = NAME)
	private final String name;
	
	@SmofArray(name = TRACKS, type = SmofType.OBJECT)
	private final Set<Track> tracks;
	
	PlaylistImpl(String name) {
		this.name = name;
		this.tracks = Sets.newLinkedHashSet();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Stream<Track> streamTracks() {
		return tracks.stream();
	}

	@Override
	public Iterable<Track> getTracks() {
		return tracks;
	}

	@Override
	public void addTrack(Track track) {
		VALIDATOR.checkArgumentNotNull(track, "Cannot add a null track");
		tracks.add(track);
	}

	@Override
	public boolean removeTrack(Track track) {
		return tracks.remove(track);
	}

}
