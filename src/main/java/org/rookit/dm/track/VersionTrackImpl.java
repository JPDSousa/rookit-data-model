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
import org.rookit.api.dm.track.Track;
import org.rookit.api.dm.track.TypeTrack;
import org.rookit.api.dm.track.TypeVersion;
import org.rookit.api.dm.track.VersionTrack;
import org.rookit.api.dm.track.artist.TrackArtists;
import org.rookit.api.dm.track.artist.VersionTrackArtists;
import org.rookit.api.dm.track.audio.AudioContent;
import org.rookit.api.dm.track.title.TrackTitle;
import org.rookit.dm.play.able.event.MutableEventStatsFactory;
import org.rookit.dm.track.artists.MutableTrackArtists;
import org.rookit.dm.track.artists.MutableVersionTrackArtists;
import org.rookit.dm.track.lyrics.MutableLyrics;

import java.util.Optional;

@SuppressWarnings("javadoc")
final class VersionTrackImpl extends AbstractTrack implements VersionTrack {

    private final MutableVersionTrackArtists versionArtists;

    private final String versionToken;// e.g. club remix

    private final Track original;

    private final TypeVersion versionType;

    VersionTrackImpl(final Track original,
                     final TypeVersion versionType,
                     final MutableVersionTrackArtists versionArtists,
                     final String versionToken,
                     final AudioContent audioContent,
                     final MutableLyrics lyrics,
                     final MutableEventStatsFactory eventStatsFactory) {
        super(audioContent, lyrics, eventStatsFactory);
        this.versionToken = versionToken;
        this.versionArtists = versionArtists;
        this.original = original;
        this.versionType = versionType;
    }

    @Override
    public Optional<VersionTrack> asVersionTrack() {
        return Optional.of(this);
    }

    @Override
    public TrackArtists artists() {
        return this.versionArtists;
    }

    @Override
    public Track getOriginal() {
        return this.original;
    }

    @Override
    public TrackTitle title() {
        return this.original.title();
    }

    @Override
    public TypeTrack type() {
        return TypeTrack.VERSION;
    }

    @Override
    public VersionTrackArtists getVersionArtists() {
        return this.versionArtists;
    }

    @Override
    public String getVersionToken() {
        return this.versionToken;
    }

    @Override
    public TypeVersion getVersionType() {
        return this.versionType;
    }

    @Override
    public boolean isVersionTrack() {
        return true;
    }

    @Override
    protected MutableTrackArtists mutableArtists() {
        return this.versionArtists;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("versionArtists", this.versionArtists)
                .add("versionToken", this.versionToken)
                .add("original", this.original)
                .add("versionType", this.versionType)
                .toString();
    }
}
