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

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;

import org.rookit.dm.track.Track;
import org.smof.annnotations.SmofArray;
import org.smof.parsers.SmofType;

import com.google.common.collect.Sets;

import static org.rookit.dm.play.DatabaseFields.*;

class StaticPlaylistImpl extends AbstractPlaylist implements StaticPlaylist {
	
	@SmofArray(name = TRACKS, type = SmofType.OBJECT)
	private final Set<Track> tracks;
	
	StaticPlaylistImpl(String name) {
		super(name);
		this.tracks = Sets.newLinkedHashSet();
	}

	@Override
	public Stream<Track> streamTracks() {
		return stream();
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
	public boolean contains(Object o) {
		return tracks.contains(o);
	}

	@Override
	public Iterator<Track> iterator() {
		return tracks.iterator();
	}

	@Override
	public Object[] toArray() {
		return tracks.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return tracks.toArray(a);
	}

	@Override
	public boolean add(Track e) {
		return tracks.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return tracks.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return tracks.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends Track> c) {
		return tracks.addAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return tracks.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return tracks.retainAll(c);
	}

	@Override
	public void clear() {
		tracks.clear();
	}

}
