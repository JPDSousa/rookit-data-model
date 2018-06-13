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

import org.apache.commons.lang3.StringUtils;
import org.rookit.api.dm.artist.Artist;
import org.rookit.utils.print.PrintUtils;
import org.rookit.utils.print.TypeFormat;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

@SuppressWarnings("javadoc")
final class MutableTitleImpl implements MutableTitle {

    private final String title;

    private String artists;
    private String extras;
    private String feats;

    private String tokens;
    private String hiddenTrack;

    MutableTitleImpl(final String title) {
        checkArgument(Objects.nonNull(title));
        this.title = title;
    }

    @Override
    public MutableTitle appendArtists(final Collection<Artist> artists) {
        checkArgument(Objects.nonNull(artists));
        this.artists = PrintUtils.getIterableAsString(artists, TypeFormat.TITLE);
        return this;
    }

    @Override
    public MutableTitle appendExtras(final String extras) {
        checkArgument(Objects.nonNull(extras));
        this.extras = extras;
        return this;
    }

    @Override
    public MutableTitle appendFeats(final Collection<Artist> artists) {
        checkArgument(Objects.nonNull(artists));
        this.feats = PrintUtils.getIterableAsString(artists, TypeFormat.TITLE);
        return this;
    }

    @Override
    public MutableTitle appendHiddenTrack(final String hiddenTrack) {
        checkArgument(Objects.nonNull(hiddenTrack));
        this.hiddenTrack = hiddenTrack;
        return this;
    }

    @Override
    public Optional<String> artists() {
        return Optional.ofNullable(this.artists);
    }

    @Override
    public Optional<String> extras() {
        return Optional.ofNullable(this.extras);
    }

    @Override
    public Optional<String> features() {
        return Optional.ofNullable(this.feats);
    }

    @Override
    public Optional<String> hiddenTrack() {
        return Optional.ofNullable(this.hiddenTrack);
    }

    @Override
    public String title() {
        return this.title;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(32);
        // artists
        if (StringUtils.isNotBlank(this.artists)) {
            builder.append(this.artists);
            builder.append(" - ");
        }
        // title
        builder.append(this.title);
        // hidden track
        if ((this.hiddenTrack != null) && !this.hiddenTrack.isEmpty()) {
            builder.append(" (+ ").append(this.hiddenTrack).append(')');
        }
        // feats
        if ((this.feats != null) && !this.feats.isEmpty()) {
            builder.append(" (feat. ");
            builder.append(this.feats);
            builder.append(')');
        }
        // add tokens
        if ((this.tokens != null) && !this.tokens.isEmpty()) {
            builder.append(" (").append(this.tokens).append(')');
        }
        // extras
        if ((this.extras != null) && !this.extras.isEmpty()) {
            builder.append(" (");
            builder.append(this.extras);
            builder.append(')');
        }

        return builder.toString();
    }

}
