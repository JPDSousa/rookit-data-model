package org.rookit.dm.album.disc;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import org.junit.jupiter.api.Test;
import org.rookit.api.dm.album.Album;
import org.rookit.api.dm.album.slot.TrackSlotFactory;
import org.rookit.api.dm.track.Track;
import org.rookit.test.AbstractUnitTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class DiscUnitTest extends AbstractUnitTest<MutableDiscImpl> {

    private static final String DISC_NAME = "Disc Name";

    private final TrackSlotFactory trackSlotFactory = mock(TrackSlotFactory.class);

    @Override
    public MutableDiscImpl createTestResource() {
        return new MutableDiscImpl(DISC_NAME, this.trackSlotFactory);
    }

    @Test
    public final void testDiscName() {
        assertThat(this.testResource.name())
                .as("The disc official")
                .isEqualTo(DISC_NAME);
    }

    @Test
    public final void testStoreTrack() {
        final Track track = mock(Track.class);
        final int trackNumber = 2;

        this.testResource.putIfAbsent(track, trackNumber);
        assertThat(this.testResource.track(trackNumber))
                .as("The inserted track")
                .isEqualTo(track);
    }

    @Test
    public final void testPutIfAbsentOnPresentTrack() {
        final Track track1 = mock(Track.class);
        final Track track2 = mock(Track.class);
        final int trackNumber = 2;

        this.testResource.putIfAbsent(track1, trackNumber);
        this.testResource.putIfAbsent(track2, trackNumber);

        assertThat(this.testResource.track(trackNumber))
                .as("The track inserted in the first place")
                .isEqualTo(track1);
    }

    @Test
    public final void testAsTrackMap() {
        final Track track1 = mock(Track.class);
        final Track track2 = mock(Track.class);
        final int trackNumber1 = 1;
        final int trackNumber2 = 2;
        final Int2ObjectMap<Track> expected = new Int2ObjectArrayMap<>();
        expected.put(trackNumber1, track1);
        expected.put(trackNumber2, track2);

        this.testResource.putIfAbsent(track1, trackNumber1);
        this.testResource.putIfAbsent(track2, trackNumber2);

        assertThat(this.testResource.asTrackMap())
                .as("The track inserted in the first place")
                .isEqualTo(expected);
    }

    @Test
    public final void testGetTracksWithSlots() {
        final Album album = mock(Album.class);
        final Track track = mock(Track.class);

        this.testResource.putIfAbsent(track, 2);
        this.testResource.asTrackSlotsCollection();
    }
}