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

import com.google.common.base.MoreObjects;
import com.google.inject.Inject;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.track.Track;
import org.rookit.api.dm.track.TypeTrack;
import org.rookit.api.dm.track.audio.AudioContent;
import org.rookit.api.dm.track.audio.AudioContentFactory;
import org.rookit.api.dm.track.factory.TrackFactory;
import org.rookit.api.dm.track.factory.VersionTrackFactory;
import org.rookit.api.dm.track.key.TrackKey;
import org.rookit.api.dm.track.title.TrackTitle;
import org.rookit.api.dm.track.title.TrackTitleFactory;
import org.rookit.dm.play.able.event.MutableEventStatsFactory;
import org.rookit.dm.track.artists.MutableTrackArtists;
import org.rookit.dm.track.artists.MutableTrackArtistsFactory;
import org.rookit.dm.track.lyrics.MutableLyrics;
import org.rookit.dm.track.lyrics.MutableLyricsFactory;
import org.rookit.dm.utils.DataModelValidator;
import org.rookit.utils.log.validator.Validator;

@SuppressWarnings("javadoc")
// TODO to reduce over coupulation, create an OriginalTrackFactory, that couples all the required dependencies
// TODO to create an original track
final class TrackFactoryImpl implements TrackFactory {

    private static final Validator VALIDATOR = DataModelValidator.getDefault();

    private final MutableTrackArtistsFactory artistsFactory;
    private final TrackTitleFactory titleFactory;
    private final AudioContentFactory audioContentFactory;
    private final MutableLyricsFactory lyricsFactory;
    private final MutableEventStatsFactory eventStatsFactory;
    private final VersionTrackFactory versionTrackFactory;
    
    @Inject
    private TrackFactoryImpl(final MutableTrackArtistsFactory artistsFactory,
                             final TrackTitleFactory titleFactory,
                             final AudioContentFactory audioContentFactory,
                             final MutableLyricsFactory lyricsFactory,
                             final MutableEventStatsFactory eventStatsFactory,
                             final VersionTrackFactory versionTrackFactory) {
        this.artistsFactory = artistsFactory;
        this.titleFactory = titleFactory;
        this.audioContentFactory = audioContentFactory;
        this.lyricsFactory = lyricsFactory;
        this.eventStatsFactory = eventStatsFactory;
        this.versionTrackFactory = versionTrackFactory;
    }

    @Override
    public Track create(final TrackKey key) {
        VALIDATOR.checkArgument().isNotNull(key, "key");

        final TypeTrack trackType = key.type();
        switch (trackType) {
            case ORIGINAL :
                return create(key.title(), key.mainArtists());
            case VERSION :
                return this.versionTrackFactory.create(key);
        }
        return VALIDATOR.handleException().runtimeException("Invalid track release: " + trackType);
    }

    @Override
    public Track createEmpty() {
        return VALIDATOR.handleException()
                .unsupportedOperation("Cannot create an empty track");
    }

    private Track create(final String title, final Iterable<Artist> mainArtists) {
        final MutableTrackArtists artists = this.artistsFactory.create(mainArtists);
        final TrackTitle trackTitle = this.titleFactory.create(title, artists);
        final AudioContent audioContent = this.audioContentFactory.createEmpty();
        final MutableLyrics lyrics = this.lyricsFactory.createEmpty();

        return new OriginalTrackImpl(trackTitle, audioContent, lyrics, artists, this.eventStatsFactory);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("artistsFactory", this.artistsFactory)
                .add("titleFactory", this.titleFactory)
                .add("audioContentFactory", this.audioContentFactory)
                .add("lyricsFactory", this.lyricsFactory)
                .add("eventStatsFactory", this.eventStatsFactory)
                .add("versionTrackFactory", this.versionTrackFactory)
                .toString();
    }
}
