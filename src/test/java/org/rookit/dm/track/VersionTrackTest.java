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

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.ImmutableSet;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.track.Track;
import org.rookit.api.dm.track.TypeVersion;
import org.rookit.api.dm.track.VersionTrack;
import org.rookit.api.dm.track.factory.TrackFactory;
import org.rookit.dm.genre.AbstractGenreableTest;
import org.rookit.dm.test.mixin.TrackMixin;
import org.rookit.test.SerializationTest;

@SuppressWarnings("javadoc")
public class VersionTrackTest extends AbstractGenreableTest<VersionTrack>
        implements TrackMixin, SerializationTest<VersionTrack> {

    private static final TrackFactory TRACK_FACTORY = INJECTOR.getInstance(TrackFactory.class);

    private Track original;
    private TypeVersion version;

    @Override
    public VersionTrack createTestResource() {
        this.version = FACTORY.typeVersions().createRandom();
        this.original = FACTORY.originalTracks().createRandom();
        return createVersionTrack(
                this.original,
                this.version,
                FACTORY.artists().createRandomSet(),
                "versionToken");
    }

    @Override
    public Class<VersionTrack> getTestResourceType() {
        return VersionTrack.class;
    }

    @Override
    public TrackFactory getTrackFactory() {
        return TRACK_FACTORY;
    }

    @Override
    @Test
    public final void testEquals() {
        super.testEquals();
        final Optional<String> id = this.testResource.getId();
        final TypeVersion version = FACTORY.typeVersions().createRandomUnique(this.version);
        final Set<Artist> someArtists = FACTORY.artists().createRandomSet();
        final String versionToken = "someVersionToken";
        final String anotherVersionToken = "anotherVersionToken";

        final VersionTrack differentVersion = createVersionTrack(this.original,
                version,
                someArtists,
                versionToken);
        id.ifPresent(differentVersion::setId);

        final VersionTrack differentArtists = createVersionTrack(this.original,
                this.version, FACTORY.artists().createRandomUniqueSet(someArtists),
                versionToken);
        id.ifPresent(differentArtists::setId);

        final VersionTrack differentOriginal = createVersionTrack(
                FACTORY.originalTracks().createRandomUnique(this.original),
                this.version,
                someArtists,
                versionToken);
        id.ifPresent(differentOriginal::setId);

        final VersionTrack differentToken = createVersionTrack(
                this.original,
                this.version,
                someArtists,
                anotherVersionToken);
        id.ifPresent(differentToken::setId);

        assertThat(this.testResource)
                .isEqualTo(this.testResource)
                .isNotEqualTo(FACTORY.originalTracks().createRandom())
                .isNotEqualTo(this.original)
                .isNotEqualTo(differentVersion)
                .isNotEqualTo(differentToken)
                .isNotEqualTo(differentOriginal)
                .isNotEqualTo(differentArtists);
    }

    @Test
    public final void testFullTitle() {
        // TODO requires further testing
        assertThat(this.testResource.fullTitle()).isNotNull();
    }

    @Test
    public final void testIsVersionTrack() {
        assertThat(this.testResource.isVersionTrack()).isTrue();
    }

    @Test
    public final void testLongFullTitle() {
        // TODO requires further testing
        assertThat(this.testResource.longFullTitle()).isNotNull();
    }

    @Test
    public final void testVersionTrack() {
        assertThat(this.testResource.asVersionTrack())
                .isNotEmpty()
                .get()
                .isEqualTo(this.testResource);
    }

    @Test
    public final void testVersionType() {
        final Track orTrack = FACTORY.originalTracks().createRandom();
        final TypeVersion version = TypeVersion.ACOUSTIC;
        final VersionTrack track = createVersionTrack(orTrack, version,
                ImmutableSet.of(), "someVersionToken");
        assertThat(track.getVersionType()).isEqualTo(version);
    }
}
