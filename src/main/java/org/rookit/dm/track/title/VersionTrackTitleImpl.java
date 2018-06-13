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
package org.rookit.dm.track.title;

import com.google.common.base.MoreObjects;
import org.rookit.api.dm.track.TypeVersion;
import org.rookit.api.dm.track.artist.VersionTrackArtists;
import org.rookit.api.dm.track.title.Title;
import org.rookit.api.dm.track.title.TrackTitle;
import org.rookit.utils.print.PrintUtils;
import org.rookit.utils.print.TypeFormat;

final class VersionTrackTitleImpl implements TrackTitle {

    private final TrackTitle original;
    private final VersionTrackArtists artists;
    private final TypeVersion typeVersion;

    VersionTrackTitleImpl(final TrackTitle original,
                          final VersionTrackArtists artists,
                          final TypeVersion typeVersion) {
        this.original = original;
        this.artists = artists;
        this.typeVersion = typeVersion;
    }

    @Override
    public Title singleTitle() {
        return this.original.singleTitle();
    }

    @Override
    public Title longFullTitle() {
        return new VersionTitleDecorator(this.original.longFullTitle(), getExtras());
    }

    @Override
    public Title fullTitle() {
        return new VersionTitleDecorator(this.original.fullTitle(), getExtras());
    }

    private String getExtras() {
        return PrintUtils.getIterableAsString(
                this.artists.versionArtists(),
                TypeFormat.TITLE) + " " + this.typeVersion.getName();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("original", this.original)
                .add("artists", this.artists)
                .add("typeVersion", this.typeVersion)
                .toString();
    }
}
