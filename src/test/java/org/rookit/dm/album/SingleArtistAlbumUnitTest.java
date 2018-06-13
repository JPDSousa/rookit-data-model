package org.rookit.dm.album;

import com.google.common.collect.ImmutableSet;
import org.junit.jupiter.api.Test;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.artist.Artist;
import org.rookit.dm.album.release.MutableRelease;
import org.rookit.dm.album.tracks.MutableAlbumTracks;
import org.rookit.test.AbstractUnitTest;
import org.rookit.test.categories.UnitTest;

import java.time.LocalDate;
import java.util.Collection;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@UnitTest
public class SingleArtistAlbumUnitTest extends AbstractUnitTest<SingleArtistAlbum>
        implements AlbumTest<SingleArtistAlbum> {

    private final MutableRelease release = mock(MutableRelease.class);
    private final Collection<Artist> artists = ImmutableSet.of(mock(Artist.class), mock(Artist.class));
    private final BiStream biStream = mock(BiStream.class);
    private final MutableAlbumTracks albumTracks = mock(MutableAlbumTracks.class);

    @Override
    public SingleArtistAlbum createTestResource() {
        return new SingleArtistAlbum(
                "Single Artist Album",
                this.release,
                this.artists,
                this.biStream,
                this.albumTracks
        );
    }

    @Test
    public final void testReleaseDate() {
        final LocalDate date = LocalDate.now();
        this.testResource.setReleaseDate(date);

        verify(this.release).setReleaseDate(date);
    }

    @Test
    public final void testRelocate() {
        final String previousDiscName = "cd1";
        final int previousNumber = 1;
        final int nextNumber = previousNumber + 1;
        final String nextDiscName = "cd2";

        this.testResource.relocate(previousDiscName, previousNumber, nextDiscName, nextNumber);

        verify(this.albumTracks).relocate(previousDiscName, previousNumber, nextDiscName, nextNumber);
    }

}
