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
import com.google.inject.Inject;
import org.dozer.Mapper;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.key.Key;
import org.rookit.api.dm.track.artist.TrackArtists;
import org.rookit.api.dm.track.artist.VersionTrackArtists;
import org.rookit.api.dm.track.artist.VersionTrackArtistsFactory;

final class MutableVersionTrackArtistsFactoryImpl implements MutableVersionTrackArtistsFactory {

    private final MutableTrackArtistsFactory artistsFactory;
    private final VersionTrackArtistsFactory factory;
    private final Mapper mapper;

    @Inject
    private MutableVersionTrackArtistsFactoryImpl(final MutableTrackArtistsFactory artistsFactory,
                                                  final VersionTrackArtistsFactory factory,
                                                  final Mapper mapper) {
        this.artistsFactory = artistsFactory;
        this.factory = factory;
        this.mapper = mapper;
    }

    private MutableVersionTrackArtists fromVersionTrackArtists(final VersionTrackArtists artists) {
        if (artists instanceof MutableVersionTrackArtists) {
            return (MutableVersionTrackArtists) artists;
        }
        final MutableTrackArtists delegate = this.artistsFactory.create(artists.mainArtists());
        this.mapper.map(artists, delegate);
        final MutableVersionTrackArtists mutableArtists = new MutableVersionTrackArtistsImpl(delegate,
                artists.versionArtists());
        this.mapper.map(artists, mutableArtists);
        return mutableArtists;
    }

    @Override
    public MutableVersionTrackArtists create(final TrackArtists originalArtists, final Iterable<Artist> versionArtists) {
        return fromVersionTrackArtists(this.factory.create(originalArtists, versionArtists));
    }

    @Override
    public MutableVersionTrackArtists create(final Key key) {
        return fromVersionTrackArtists(this.factory.create(key));
    }

    @Override
    public MutableVersionTrackArtists createEmpty() {
        return fromVersionTrackArtists(this.factory.createEmpty());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("artistsFactory", this.artistsFactory)
                .add("factory", this.factory)
                .add("mapper", this.mapper)
                .toString();
    }
}
