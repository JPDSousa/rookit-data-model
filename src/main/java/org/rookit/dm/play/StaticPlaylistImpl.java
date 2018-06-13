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

import com.google.common.base.MoreObjects;
import com.google.common.collect.Sets;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.play.StaticPlaylist;
import org.rookit.api.dm.track.Track;
import org.rookit.dm.play.able.event.MutableEventStatsFactory;
import org.rookit.utils.VoidUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

import static org.rookit.api.dm.play.TypePlaylist.STATIC;

class StaticPlaylistImpl extends AbstractPlaylist implements StaticPlaylist {

    private static final long serialVersionUID = 4676007834622506278L;

    private final Set<Track> tracks;

    StaticPlaylistImpl(final String name,
                       final BiStream image,
                       final MutableEventStatsFactory eventStatsFactory) {
        super(STATIC, name, image, eventStatsFactory);
        this.tracks = Collections.synchronizedSet(Sets.newLinkedHashSet());
    }

    @Override
    public boolean addTrack(final Track e) {
        return this.tracks.add(e);
    }

    @Override
    public boolean addTracks(final Collection<? extends Track> collection) {
        return this.tracks.addAll(collection);
    }

    @Override
    public Void clear() {
        this.tracks.clear();
        return VoidUtils.returnVoid();
    }

    @Override
    public boolean contains(final Track o) {
        return this.tracks.contains(o);
    }

    @Override
    public boolean equals(final Object other) {
        return super.equals(other);
    }

    @Override
    public Collection<Track> tracks() {
        return Collections.unmodifiableSet(this.tracks);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean removeTrack(final Track o) {
        return this.tracks.remove(o);
    }

    @Override
    public boolean removeTracks(final Collection<? extends Track> tracks) {
        return this.tracks.removeAll(tracks);
    }

    @Override
    public int size() {
        return this.tracks.size();
    }

    @Override
    public Stream<Track> streamTracks() {
        return this.tracks.stream();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("tracks", this.tracks)
                .toString();
    }
}
