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

package org.rookit.dm.track;

import com.google.common.collect.Sets;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.track.Track;
import org.rookit.api.dm.track.TypeTrack;
import org.rookit.api.dm.track.TypeVersion;
import org.rookit.api.dm.track.factory.TrackFactory;
import org.rookit.dm.genre.AbstractGenreableTest;
import org.rookit.dm.test.mixin.TrackMixin;
import org.rookit.test.AbstractUnitTest;
import org.rookit.test.CollectionOps;
import org.rookit.test.SerializationTest;
import org.rookit.utils.optional.OptionalShort;

import java.util.Collection;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings("javadoc")
public class TrackTest extends AbstractGenreableTest<Track> implements TrackMixin, SerializationTest<Track> {

    private static final TrackFactory TRACK_FACTORY = INJECTOR.getInstance(TrackFactory.class);

    @Override
    public Track createTestResource() {
        return FACTORY.originalTracks().createRandom();
    }

    @Override
    public TrackFactory getTrackFactory() {
        return TRACK_FACTORY;
    }

    @Test
    public final void testAddAlreadyFeatureAsProducer() {
        final Artist artist = FACTORY.groupArtists().createRandom();
        this.testResource.addFeature(artist);
        // TODO assert that exception throws here
        assertThatThrownBy(() -> this.testResource.addProducer(artist))
                .as("Adding a producer that is already defined as a feature is not allowed.")
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public final void testAddAlreadyMainArtistAsFeature() {
        final String errorMsg = "Assumption broken: this test requires at least one mainArtists artist";
        final Artist artist = this.testResource.mainArtists()
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(errorMsg));

        assertThatThrownBy(() -> this.testResource.addFeature(artist))
                .as("Adding a feature that is already an artist")
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public final void testAddAlreadyMainArtistAsProducer() {
        final String errorMsg = "Assumption broken: this test requires at least one mainArtists artist";
        final Artist artist = this.testResource.mainArtists()
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(errorMsg));

        assertThatThrownBy(() -> this.testResource.addFeature(artist))
                .as("Adding a producer that is already an artist")
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public final void testAddAlreadyProducerAsFeature() {
        final Artist artist = FACTORY.groupArtists().createRandom();
        this.testResource.addProducer(artist);
        assertThatThrownBy(() -> this.testResource.addFeature(artist))
                .as("Adding a producer that is already defined as a feature is not allowed.")
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public final void testAllGenres() {
        final Set<Genre> genres = FACTORY.genres().createRandomSet();
        this.testResource.setGenres(genres);
        assertThat(this.testResource.allGenres())
                .containsExactlyInAnyOrderElementsOf(genres);
    }

    @Test
    public final void testBpm() {
        // TODO move to audio features UT
        final short bpm = 140;
        this.testResource.audioFeatures().setBpm(bpm);
        final OptionalShort actualBpmOrNone = this.testResource.audioFeatures().getBpm();
        assertThat(actualBpmOrNone.isPresent())
                .isTrue();
        assertThat(actualBpmOrNone.getAsShort())
                .isEqualTo(bpm);
    }

    @Test
    public final void testCompareTo() {
        testCompareTo(this.testResource);
        testCompareTo(FACTORY.originalTracks().createRandom());
    }

    @Test
    public final void testEmptyLyrics() {
        assertThatThrownBy(() -> this.testResource.setLyrics(""))
                .as("Invalid lyrics")
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("empty");
    }

    @Override
    @Test
    public final void testEquals() {
        super.testEquals();

        final Track track1 = FACTORY.originalTracks().createRandom();
        final Track track2 = FACTORY.versionTracks().createRandom();

        assertThat(track1)
                .isEqualTo(track1)
                .isNotEqualTo(FACTORY.groupArtists().createRandom())
                .isNotEqualTo(track2);
    }

    @Test
    public final void testEqualsById() {
        final String title = "some title";
        final Set<Artist> mainArtists = FACTORY.artists().createRandomSet();
        final Set<Artist> features = Sets.newLinkedHashSet();
        final Set<Genre> genres = FACTORY.genres().createRandomSet();
        final Track track1 = createOriginalTrack(title, mainArtists);
        final Track track2 = createOriginalTrack(title, mainArtists);
        track1.setFeatures(features);
        track2.setFeatures(features);
        track1.setGenres(genres);
        track2.setGenres(genres);
        final ObjectId id = new ObjectId();

        track1.setId(id.toHexString());
        track2.setId(id.toHexString());
        assertThat(track2).isEqualTo(track1);
    }

    @Test
    public final void testEqualsByType() {
        final Collection<Artist> mainArtists = FACTORY.artists().createRandomSet();
        final Track track1 = createOriginalTrack("some random title", mainArtists);
        final Track track2 = createVersionTrack(track1,
                TypeVersion.ACOUSTIC,
                FACTORY.artists().createRandomSet(),
                "versionToken");
        assertThat(track1.equals(track2)).isFalse();
    }

    @Test
    public final void testEqualsOriginalTrack() {
        final String title = "some random string";
        final Set<Artist> mainArtists = FACTORY.artists().createRandomSet();
        final Track track1 = createOriginalTrack(title, mainArtists);
        final Track track2 = createOriginalTrack(title, mainArtists);

        track1.getId().ifPresent(track2::setId);
        assertThat(track2)
                .isEqualTo(track1);

        track1.setFeatures(FACTORY.artists().createRandomSet());
        assertThat(track2)
                .isEqualTo(track1);

        track2.setFeatures(FACTORY.artists().createRandomSet());
        assertThat(track2)
                .isEqualTo(track1);
    }

    @Test
    public final void testEqualsVersionTrack() {
        final Collection<Artist> mainArtists = FACTORY.artists().createRandomSet();
        final Track original = createOriginalTrack("some random string", mainArtists);
        final TypeVersion version = TypeVersion.LIVE;
        final Collection<Artist> versionArtists = FACTORY.artists().createRandomSet();
        final Track track1 = createVersionTrack(original, version, versionArtists);
        final Track track2 = createVersionTrack(original, version, versionArtists);
        track2.getId().ifPresent(track1::setId);
        assertThat(track2).isEqualTo(track1);
    }

    @Test
    public final void testExplicit() {
        this.testResource.setExplicit(true);
        assertThat(this.testResource.isExplicit().isPresent() && this.testResource.isExplicit().getAsBoolean())
                .isTrue();
        this.testResource.setExplicit(false);
        assertThat(this.testResource.isExplicit().isPresent() && this.testResource.isExplicit().getAsBoolean())
                .isFalse();
    }

    @SuppressWarnings("synthetic-access")
    @Test
    public final void testFeatures() {
        AbstractUnitTest.testCollectionOps(new CollectionOps<Artist>() {

            @Override
            public void add(final Artist item) {
                TrackTest.this.testResource.addFeature(item);
            }

            @Override
            public void addAll(final Collection<Artist> items) {
                TrackTest.this.testResource.addFeatures(items);
            }

            @Override
            public Collection<Artist> get() {
                return TrackTest.this.testResource.features();
            }

            @Override
            public void remove(final Artist item) {
                TrackTest.this.testResource.removeFeature(item);
            }

            @Override
            public void removeAll(final Collection<Artist> items) {
                TrackTest.this.testResource.removeFeatures(items);
            }

            @Override
            public void reset() {
                TrackTest.this.testResource.clearFeatures();
            }

            @Override
            public void set(final Collection<Artist> items) {
                TrackTest.this.testResource.setFeatures(items);
            }
        }, () -> FACTORY.groupArtists().createRandom());
    }

    @Test
    public final void testFullTitle() {
        assertThat(this.testResource.fullTitle())
                .isNotNull();
    }

    @SuppressWarnings("static-method")
    @Test
    public final void testGetAsVersionTrack() {
        final Track track = FACTORY.versionTracks().createRandom();
        assertThat(track.asVersionTrack())
                .get()
                .isEqualTo(track);
    }

    @SuppressWarnings("static-method")
    @Test
    public final void testIsVersionTrack() {
        final Track track = FACTORY.versionTracks().createRandom();
        assertThat(track.isVersionTrack()).isTrue();
    }

    @Test
    public final void testLongFullTitle() {
        assertThat(this.testResource.longFullTitle())
                .isNotNull();
    }

    @Test
    public final void testLyrics() {
        final String lyrics = "bla bla bla";
        this.testResource.setLyrics(lyrics);
        assertThat(this.testResource.lyrics())
                .isNotEmpty()
                .get()
                .isEqualTo(lyrics);
    }

    @Test
    public final void testNegativeBpm() {
        // TODO move to AudioFeatures UT
        assertThatThrownBy(() -> this.testResource.audioFeatures().setBpm((short) -100))
                .as("Setting a negative bpm should fail")
                .isInstanceOf(IllegalArgumentException.class);
    }

    @SuppressWarnings("static-method")
    @Test
    public final void testOriginalGetAsVersionTrack() {
        final Track track = FACTORY.originalTracks().createRandom();
        assertThat(track.asVersionTrack())
                .as("An original track fetched as version")
                .isEmpty();
    }

    @SuppressWarnings("static-method")
    @Test
    public final void testOriginalIsVersionTrack() {
        final Track track = FACTORY.originalTracks().createRandom();
        assertThat(track.isVersionTrack()).isFalse();
    }

    @SuppressWarnings("synthetic-access")
    @Test
    public final void testProducers() {
        AbstractUnitTest.testCollectionOps(new CollectionOps<Artist>() {

            @Override
            public void add(final Artist item) {
                TrackTest.this.testResource.addProducer(item);
            }

            @Override
            public void addAll(final Collection<Artist> items) {
                TrackTest.this.testResource.addProducers(items);
            }

            @Override
            public Collection<Artist> get() {
                return TrackTest.this.testResource.producers();
            }

            @Override
            public void remove(final Artist item) {
                TrackTest.this.testResource.removeProducer(item);
            }

            @Override
            public void removeAll(final Collection<Artist> items) {
                TrackTest.this.testResource.removeProducers(items);
            }

            @Override
            public void reset() {
                TrackTest.this.testResource.clearProducers();
            }

            @Override
            public void set(final Collection<Artist> items) {
                TrackTest.this.testResource.setProducers(items);
            }
        }, () -> FACTORY.groupArtists().createRandom());
    }

    @SuppressWarnings("static-method")
    @Test
    public final void testTrackType() {
        assertThat(FACTORY.originalTracks().createRandom().type())
                .isEqualTo(TypeTrack.ORIGINAL);
    }

    @Test
    public final void testZeroBpm() {
        // TODO move to AudioFeatures UT
        assertThatThrownBy(() -> this.testResource.audioFeatures().setBpm((short) 0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private void testCompareTo(final Track track) {
        assertThat(this.testResource.compareTo(track))
                .isEqualTo(this.testResource.title().toString().compareTo(track.title().toString()));
    }

    @Override
    public Class<Track> getTestResourceType() {
        return Track.class;
    }
}
