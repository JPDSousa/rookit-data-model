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

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.album.TypeAlbum;
import org.rookit.api.dm.artist.Artist;
import org.rookit.dm.album.release.MutableRelease;
import org.rookit.dm.album.tracks.MutableAlbumTracks;
import org.rookit.dm.play.able.event.MutableEventStatsFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * This class represents a single artist album, which represents an album that
 * has a fixed and declared primitive of authors (e.g. non VA-albums).
 *
 * @author Joao Sousa (jpd.sousa@campus.fct.unl.pt)
 *
 */
final class SingleArtistAlbum extends AbstractAlbum {

    /** Set of authors of the album */
    private final Set<Artist> artists;

    /**
     * The constructor has a package-view in order to forbid object creation
     * through objects on other packages. In order to create a new object of
     * this release, use the dedicated methods on the {@link AlbumFactoryImpl}
     * class.
     *
     * @param name official of the album
     * @param artists album authors
     */
    SingleArtistAlbum(final String name,
                      final MutableRelease release,
                      final Collection<Artist> artists,
                      final BiStream stream,
                      final MutableAlbumTracks tracks,
                      final MutableEventStatsFactory eventStatsFactory) {
        super(name, release, stream, tracks, eventStatsFactory);
        this.artists = ImmutableSet.copyOf(artists);
    }

    @Override
    public TypeAlbum type() {
        return TypeAlbum.ARTIST;
    }

    @Override
    public Collection<Artist> artists() {
        return Collections.unmodifiableSet(this.artists);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("artists", this.artists)
                .toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if ((o == null) || (getClass() != o.getClass())) return false;
        if (!super.equals(o)) return false;
        final SingleArtistAlbum otherAlbum = (SingleArtistAlbum) o;
        return Objects.equal(this.artists, otherAlbum.artists);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), this.artists);
    }
}
