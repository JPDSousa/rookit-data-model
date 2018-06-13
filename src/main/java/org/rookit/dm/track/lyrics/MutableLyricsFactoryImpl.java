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
package org.rookit.dm.track.lyrics;

import com.google.common.base.MoreObjects;
import com.google.inject.Inject;
import org.dozer.Mapper;
import org.rookit.api.dm.key.Key;
import org.rookit.api.dm.track.lyrics.Lyrics;
import org.rookit.api.dm.track.lyrics.LyricsFactory;

final class MutableLyricsFactoryImpl implements MutableLyricsFactory {

    private final LyricsFactory factory;
    private final Mapper mapper;

    @Inject
    private MutableLyricsFactoryImpl(final LyricsFactory factory, final Mapper mapper) {
        this.factory = factory;
        this.mapper = mapper;
    }

    private MutableLyrics fromLyrics(final Lyrics lyrics) {
        if (lyrics instanceof MutableLyrics) {
            return (MutableLyrics) lyrics;
        }
        final MutableLyrics mutableLyrics = new MutableLyricsImpl();
        this.mapper.map(lyrics, mutableLyrics);
        return mutableLyrics;
    }

    @Override
    public MutableLyrics create(final Key key) {
        return fromLyrics(this.factory.create(key));
    }

    @Override
    public MutableLyrics createEmpty() {
        return fromLyrics(this.factory.createEmpty());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("factory", this.factory)
                .add("mapper", this.mapper)
                .toString();
    }
}
