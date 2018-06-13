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
package org.rookit.dm.artist.name;

import com.google.common.base.MoreObjects;
import com.google.inject.Inject;
import org.dozer.Mapper;
import org.rookit.api.dm.artist.name.ArtistName;
import org.rookit.api.dm.artist.name.ArtistNameFactory;
import org.rookit.api.dm.artist.name.ArtistNameKey;

final class MutableArtistNameFactoryImpl implements MutableArtistNameFactory {

    private final ArtistNameFactory factory;
    private final Mapper mapper;

    @Inject
    private MutableArtistNameFactoryImpl(final ArtistNameFactory factory, final Mapper mapper) {
        this.factory = factory;
        this.mapper = mapper;
    }

    private MutableArtistName fromArtistName(final ArtistName artistName) {
        if (artistName instanceof MutableArtistName) {
            return (MutableArtistName) artistName;
        }
        final MutableArtistName mutableArtistName = new MutableArtistNameImpl(artistName.official());
        this.mapper.map(artistName, mutableArtistName);
        return mutableArtistName;
    }

    @Override
    public MutableArtistName create(final String official) {
        return fromArtistName(this.factory.create(official));
    }

    @Override
    public MutableArtistName create(final ArtistNameKey key) {
        return fromArtistName(this.factory.create(key));
    }

    @Override
    public MutableArtistName createEmpty() {
        return fromArtistName(this.factory.createEmpty());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("factory", this.factory)
                .add("mapper", this.mapper)
                .toString();
    }
}
