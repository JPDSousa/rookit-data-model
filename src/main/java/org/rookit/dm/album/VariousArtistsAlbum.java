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

package org.rookit.dm.album;

import com.google.common.collect.Sets;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.album.TypeAlbum;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.track.Track;
import org.rookit.dm.album.release.MutableRelease;
import org.rookit.dm.album.tracks.MutableAlbumTracks;
import org.rookit.dm.play.able.event.MutableEventStatsFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

final class VariousArtistsAlbum extends AbstractAlbum {

    VariousArtistsAlbum(final String name,
                        final MutableRelease release,
                        final BiStream cover,
                        final MutableAlbumTracks albumTracks,
                        final MutableEventStatsFactory eventStatsFactory) {
        super(name, release, cover, albumTracks, eventStatsFactory);
    }

    @Override
    public TypeAlbum type() {
        return TypeAlbum.VA;
    }

    @Override
    public Collection<Artist> artists() {
        final Set<Artist> artists = Sets.newHashSet();

        for (final Track track : tracks().asCollection()) {
            artists.addAll(track.mainArtists());
        }

        return Collections.unmodifiableSet(artists);
    }

}
