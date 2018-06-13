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
import org.rookit.api.dm.track.artist.TrackArtists;
import org.rookit.api.dm.track.title.TrackTitle;

final class TrackTitleImpl implements TrackTitle {

    private final String title;
    private final TrackArtists artists;
    private final MutableTitleFactory factory;

    TrackTitleImpl(final String title,
                   final TrackArtists artists,
                   final MutableTitleFactory factory) {
        this.title = title;
        this.artists = artists;
        this.factory = factory;
    }

    @Override
    public MutableTitle singleTitle() {
        return this.factory.create(this.title);
    }

    @Override
    public MutableTitle longFullTitle() {
        return fullTitle()
                .appendArtists(this.artists.mainArtists());
    }

    @Override
    public MutableTitle fullTitle() {
        return singleTitle()
                .appendFeats(this.artists.features());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("title", this.title)
                .add("artists", this.artists)
                .add("factory", this.factory)
                .toString();
    }
}
