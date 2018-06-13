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

import org.junit.jupiter.api.Test;
import org.rookit.api.dm.album.Album;
import org.rookit.api.dm.album.TypeAlbum;
import org.rookit.api.dm.album.TypeRelease;
import org.rookit.api.dm.album.factory.AlbumFactory;
import org.rookit.api.dm.album.key.AlbumKey;
import org.rookit.api.dm.album.key.ImmutableAlbumKey;
import org.rookit.api.dm.artist.Artist;
import org.rookit.dm.AbstractDataModelTest;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("javadoc")
public class AlbumFactoryTest extends AbstractDataModelTest<AlbumFactory> {

    @Test
    public void testCreateAlbumSingle() {
        final TypeAlbum artist = TypeAlbum.ARTIST;
        final String title = "Album title1";
        final TypeRelease release = TypeRelease.DELUXE;
        final Set<Artist> artists = FACTORY.artists().createRandomSet();
        final AlbumKey albumKey = ImmutableAlbumKey.builder()
                .albumType(artist)
                .title(title)
                .type(release)
                .addAllArtists(artists)
                .build();
        final Album album = this.testResource.create(albumKey);

        assertThat(album.type())
                .as("Unexpected album release")
                .isEqualTo(artist);
        assertThat(album.title())
                .as("Unexpected album title")
                .isEqualTo(title);
        assertThat(album.release())
                .as("Unexpected album release release")
                .isEqualTo(release);
        assertThat(album.artists())
                .as("Unexpected album artists")
                .isEqualTo(artists);
    }

    @Test
    public void testCreateVariousArtistsAlbum() {
        final TypeAlbum type = TypeAlbum.VA;
        final String title = "Album title1";
        final TypeRelease release = TypeRelease.DELUXE;
        final Set<Artist> artists = FACTORY.artists().createRandomSet();
        final AlbumKey albumKey = ImmutableAlbumKey.builder()
                .albumType(type)
                .title(title)
                .addAllArtists(artists)
                .type(release)
                .build();
        final Album album = this.testResource.create(albumKey);

        assertThat(album.type()).as("Unexpected album release").isEqualTo(type);
        assertThat(album.title()).as("Unexpected album title").isEqualTo(title);
        assertThat(album.release()).as("Unexpected album release release").isEqualTo(release);
        assertThat(album.artists()).as("Unexpected album artists").isEmpty();
    }

    @Test
    public void testCreateSingleArtistAlbum() {
        final String albumTitle = "Album Title";
        final Set<Artist> artists = FACTORY.artists().createRandomSet();

        for (final TypeRelease release : TypeRelease.values()) {
            final AlbumKey albumKey = ImmutableAlbumKey.builder()
                    .title(albumTitle)
                    .type(release)
                    .addAllArtists(artists)
                    .build();
            final Album album = this.testResource.createSingleArtistAlbum(albumKey);
            assertThat(album.type())
                    .isEqualTo(TypeAlbum.ARTIST);

            assertThat(album.title())
                    .isEqualTo(albumTitle);

            assertThat(album.release())
                    .isEqualTo(release);

            assertThat(album.artists())
                    .isEqualTo(artists);

            assertThat(album.fullTitle())
                    .isEqualTo(release.getFormattedName(albumTitle));
        }
    }

    @Test
    public final void testType() {
        final TypeRelease expectedReleaseType = TypeRelease.BESTOF;
        final AlbumKey key = ImmutableAlbumKey.builder()
                .title("An Album")
                .type(expectedReleaseType)
                .build();
        final Album singleArtistAlbum = this.testResource.createSingleArtistAlbum(key);
        assertThat(singleArtistAlbum.release())
                .as("Type is not being properly assigned!")
                .isEqualTo(expectedReleaseType);
    }

    @Override
    public AlbumFactory createTestResource() {
        return INJECTOR.getInstance(AlbumFactory.class);
    }

}
