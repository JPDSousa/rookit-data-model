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
import org.rookit.api.dm.track.Track;
import org.rookit.utils.resource.Resources;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SuppressWarnings("javadoc")
public interface AlbumTest<T extends Album> extends TrackRelatedTest<T> {

    @Test
    default void testAllGenres() {
        final T testResource = getTestResource();

        final Collection<Track> tracks = getTrackCollection();
        for (final Track track : tracks) {
            testResource.addTrackLast(track, "disc");
        }

        testResource.allGenres();
        // TODO verify that #allGenres() also fetches self genres
        for (final Track track : tracks) {
            verify(track, times(1)).getGenres();
        }
    }

    @SuppressWarnings("resource")
    @Test
    default void testCover() throws IOException {
        final T testResource = getTestResource();
        final Path birds = Resources.RESOURCES_TEST.resolve("birds.jpg");
        final byte[] cover = Files.readAllBytes(birds);

        final OutputStream outputStream = mock(OutputStream.class);
        when(testResource.cover().writeTo()).thenReturn(outputStream);
        testResource.setCover(cover);
        verify(testResource.cover(), times(1)).writeTo();
        verify(outputStream, times(1)).write(cover);
    }

    @Test
    default void testDuration() {
        final T testResource = getTestResource();

        final Collection<Track> tracks = getTrackCollection();
        for (final Track track : tracks) {
            testResource.addTrackLast(track, "disc");
        }
        testResource.duration();
        for (final Track track : tracks) {
            verify(track, times(1)).duration();
        }
    }

    @Test
    default void testFullTitle() {
        final T testResource = getTestResource();
        assertThat(testResource.fullTitle())
                .as("The full title cannot be null")
                .isNotNull();

        assertThat(testResource.fullTitle())
                .as("The full title cannot be empty")
                .isNotEmpty();
    }

}
