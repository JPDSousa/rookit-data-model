package org.rookit.dm.album;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.rookit.api.dm.album.Album;
import org.rookit.api.dm.album.slot.TrackSlot;
import org.rookit.api.dm.track.Track;
import org.rookit.test.ObjectTest;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

public interface TrackRelatedTest<T extends Album> extends ObjectTest<T> {

    default Collection<Track> getTrackCollection() {
        return Stream.generate(() -> mock(Track.class))
                .limit(5L)
                .collect(Collectors.toList());
    }

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @TestFactory
    default Collection<DynamicTest> testInvalidDiscAddTrack() {
        final T testResource = getTestResource();
        return testBlankStringArgument(disc -> testResource.addTrack(mock(Track.class), 2, disc));
    }

    @Test
    default void testAddNumberlessTrack() {
        final T testResource = getTestResource();
        final Collection<Track> tracks = getTrackCollection();
        final String disc = "disc";

        for (final Track track : tracks) {
            testResource.addTrack(track, 0, disc);
        }
        final Collection<Track> actual = testResource.tracks().asCollection();
        assertThat(actual)
                .as("Numberless tracks are not being correctly added")
                .containsAll(tracks);
    }

    @Test
    default void testAddTrackLast() {
        final T testResource = getTestResource();
        final Collection<Track> tracks = getTrackCollection();
        final String disc = "disc";

        for (final Track track : tracks) {
            testResource.addTrackLast(track, disc);
        }
        final Collection<Track> actual = testResource.tracks().asCollection();
        assertThat(actual)
                .as("Tracks are not being properly added to last position!")
                .containsAll(tracks);
    }

    @Test
    default void testNullTrackAddTrack() {
        final T testResource = getTestResource();
        assertThatThrownBy(() -> testResource.addTrack(null, 2, "disc"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    default void testTracksByDisc() {
        final T testResource = getTestResource();
        final String discName = "disc";
        final List<Track> tracks = ImmutableList.copyOf(getTrackCollection());

        for (final Track track : tracks) {
            testResource.addTrackLast(track, discName);
        }
        testResource.addTrackLast(mock(Track.class), "other disc");
        final Collection<Track> actual = testResource.tracks().asSlots().asCollection(discName)
                .stream()
                .map(TrackSlot::track)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        assertThat(actual).as("Tracks are not benig properly assigned").isEqualTo(tracks);
    }

    @Test
    default void testTracksCount() {
        final T testResource = getTestResource();
        final Collection<Track> tracksCD1 = getTrackCollection();
        final Collection<Track> tracksCD2 = getTrackCollection();

        for (final Track track : tracksCD1) {
            testResource.addTrackLast(track, "cd1");
        }
        for (final Track track : tracksCD2) {
            testResource.addTrackLast(track, "cd2");
        }
        assertThat(testResource.tracks().size())
                .as("Track counter should count all the album's tracks!")
                .isGreaterThanOrEqualTo(tracksCD1.size() + tracksCD2.size());
    }

    @Test
    default void testTracksNoArguments() {
        final T testResource = getTestResource();
        final Collection<Track> tracks = getTrackCollection();
        final String disc = "disc";

        for (final Track track : tracks) {
            testResource.addTrackLast(track, disc);
        }
        final Collection<Track> actual = testResource.tracks().asCollection();
        assertThat(actual)
                .as("Tracks are not being properly assigned!")
                .containsAll(tracks);
    }

}
