package org.rookit.dm.album;

import org.junit.jupiter.api.Test;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.track.Track;
import org.rookit.dm.album.release.MutableRelease;
import org.rookit.dm.album.tracks.MutableAlbumTracks;
import org.rookit.test.AbstractUnitTest;
import org.rookit.test.categories.UnitTest;

import java.time.LocalDate;
import java.util.Collection;

import static org.mockito.Mockito.*;

@UnitTest
public class VariousArtistsAlbumUnitTest extends AbstractUnitTest<VariousArtistsAlbum>
        implements AlbumTest<VariousArtistsAlbum> {

    private final MutableRelease release = mock(MutableRelease.class);
    private final BiStream biStream = mock(BiStream.class);
    private final MutableAlbumTracks albumTracks = mock(MutableAlbumTracks.class);

    @Override
    public VariousArtistsAlbum createTestResource() {
        return new VariousArtistsAlbum("A Title",
                this.release,
                this.biStream,
                this.albumTracks);
    }

    @Test
    public final void testReleaseDate() {
        final LocalDate date = LocalDate.now();
        this.testResource.setReleaseDate(date);

        verify(this.release, times(1)).setReleaseDate(date);
    }

    @Test
    public final void testArtistsUsesTracks() {
        final Collection<Track> tracks = getTrackCollection();

        for (final Track track : tracks) {
            this.testResource.addTrackLast(track, "someDisc");
        }

        this.testResource.artists();
        for (final Track track : tracks) {
            verify(track, times(1)).mainArtists();
        }
    }
}
