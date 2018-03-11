/*******************************************************************************
 * Copyright (C) 2017 Joao Sousa
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package org.rookit.dm.play;

import static org.rookit.api.dm.play.PlaylistFields.*;
import static org.rookit.api.dm.play.TypePlaylist.STATIC;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.play.StaticPlaylist;
import org.rookit.api.dm.track.Track;
import org.rookit.api.storage.DBManager;

import com.google.common.collect.Sets;

@Entity("Playlist")
class StaticPlaylistImpl extends AbstractPlaylist implements StaticPlaylist {
	
	@Reference(value = TRACKS, lazy = true, idOnly = true)
	private final Set<Track> tracks;
	
	@SuppressWarnings("unused")
	@Deprecated
	private StaticPlaylistImpl() {
		this(null, null);
	}
	
	StaticPlaylistImpl(String name, BiStream image) {
		super(STATIC, name, image);
		this.tracks = Sets.newLinkedHashSet();
	}

	@Override
	public Stream<Track> streamTracks() {
		return tracks.stream();
	}

	@Override
	public int size() {
		return tracks.size();
	}

	@Override
	public boolean isEmpty() {
		return tracks.isEmpty();
	}

	@Override
	public boolean contains(Track o) {
		return tracks.contains(o);
	}

	@Override
	public boolean addTrack(Track e) {
		return tracks.add(e);
	}

	@Override
	public boolean removeTrack(Track o) {
		return tracks.remove(o);
	}

	@Override
	public boolean addTracks(Collection<? extends Track> c) {
		return tracks.addAll(c);
	}

	@Override
	public boolean removeTracks(Collection<? extends Track> c) {
		return tracks.removeAll(c);
	}

	@Override
	public void clear() {
		tracks.clear();
	}

	@Override
	public Collection<Track> getTracks() {
		return Collections.unmodifiableSet(tracks);
	}
	
	@Override
	public boolean equals(Object other) {
		return super.equals(other);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public StaticPlaylist freeze(final DBManager db, final int limit) {
		final StaticPlaylist playlist = db.getFactories()
				.getPlaylistFactory()
				.createStaticPlaylist(getName());
		streamTracks().limit(limit).forEach(playlist::addTrack);
		return playlist;
	}
	
}
