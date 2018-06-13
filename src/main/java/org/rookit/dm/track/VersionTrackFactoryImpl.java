/*******************************************************************************
 * Copyright (C) 2018 Joao Sousa
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
import org.rookit.api.dm.track.TypeVersion;
import org.rookit.api.dm.track.VersionTrack;
import org.rookit.api.dm.track.audio.AudioContent;
import org.rookit.api.dm.track.audio.AudioContentFactory;
import org.rookit.api.dm.track.factory.VersionTrackFactory;
import org.rookit.api.dm.track.key.TrackKey;
import org.rookit.dm.play.able.event.MutableEventStatsFactory;
import org.rookit.dm.track.artists.MutableVersionTrackArtists;
import org.rookit.dm.track.artists.MutableVersionTrackArtistsFactory;
import org.rookit.dm.track.lyrics.MutableLyrics;
import org.rookit.dm.track.lyrics.MutableLyricsFactory;
import org.rookit.dm.utils.DataModelValidator;
import org.rookit.utils.log.validator.Validator;

final class VersionTrackFactoryImpl implements VersionTrackFactory {

    private static final Validator VALIDATOR = DataModelValidator.getDefault();

    private final MutableVersionTrackArtistsFactory versionTrackArtistsFactory;
    private final AudioContentFactory audioContentFactory;
    private final MutableLyricsFactory lyricsFactory;
    private final MutableEventStatsFactory eventStatsFactory;

    @Inject
    private VersionTrackFactoryImpl(final MutableVersionTrackArtistsFactory artistsFactory,
                                    final AudioContentFactory audioContentFactory,
                                    final MutableLyricsFactory lyricsFactory,
                                    final MutableEventStatsFactory eventStatsFactory) {
        this.versionTrackArtistsFactory = artistsFactory;
        this.audioContentFactory = audioContentFactory;
        this.lyricsFactory = lyricsFactory;
        this.eventStatsFactory = eventStatsFactory;
    }

    @Override
    public VersionTrack create(final Track original,
                               final TypeVersion versionType,
                               final Iterable<Artist> versionArtists,
                               final String versionToken) {
        final MutableVersionTrackArtists artists = this.versionTrackArtistsFactory
                .create(original.artists(), versionArtists);
        final AudioContent audioContent = this.audioContentFactory.createEmpty();
        final MutableLyrics lyrics = this.lyricsFactory.createEmpty();

        return new VersionTrackImpl(original, versionType, artists, versionToken, audioContent, lyrics,
                this.eventStatsFactory);
    }

    @Override
    public VersionTrack create(final TrackKey key) {
        VALIDATOR.checkArgument().isNotNull(key, "key");

        return create(key.original(), key.getVersionType(), key.getVersionArtists(),
                key.versionToken());
    }

    @Override
    public VersionTrack createEmpty() {
        return VALIDATOR.handleException().unsupportedOperation("Cannot create an empty version track.");
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("versionTrackArtistsFactory", versionTrackArtistsFactory)
                .add("audioContentFactory", audioContentFactory)
                .add("lyricsFactory", lyricsFactory)
                .add("eventStatsFactory", eventStatsFactory)
                .toString();
    }
}
