/*******************************************************************************
 * Copyright (C) 2018 Joao Sousa
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
package org.rookit.dm.track.artists;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.track.artist.TrackArtists;

import java.util.Collection;
import java.util.Collections;

final class MutableVersionTrackArtistsImpl extends AbstractTrackArtists implements MutableVersionTrackArtists {

    private final TrackArtists delegate;
    private final Collection<Artist> version;

    MutableVersionTrackArtistsImpl(final TrackArtists delegate, final Iterable<Artist> version) {
        this.delegate = delegate;
        this.version = ImmutableSet.copyOf(version);
    }

    @Override
    public Collection<Artist> versionArtists() {
        return Collections.unmodifiableCollection(this.version);
    }

    @Override
    public Collection<Artist> mainArtists() {
        return this.delegate.mainArtists();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("delegate", this.delegate)
                .add("version", this.version)
                .toString();
    }
}
