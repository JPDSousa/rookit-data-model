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

package org.rookit.dm.artist.factory;

import com.google.common.base.MoreObjects;
import com.google.inject.Inject;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.artist.TypeArtist;
import org.rookit.api.dm.artist.factory.ArtistFactory;
import org.rookit.api.dm.artist.factory.GroupArtistFactory;
import org.rookit.api.dm.artist.factory.MusicianFactory;
import org.rookit.api.dm.artist.key.ArtistKey;
import org.rookit.dm.utils.DataModelValidator;
import org.rookit.utils.log.validator.Validator;

/**
 * Default implementation for {@link ArtistFactory}. This class implements the
 * factory pattern.
 *
 * @author Joao Sousa (jpd.sousa@campus.fct.unl.pt)
 *
 */
final class ArtistFactoryImpl implements ArtistFactory {

    private static final Validator VALIDATOR = DataModelValidator.getDefault();

    private final MusicianFactory musicianFactory;
    private final GroupArtistFactory groupArtistFactory;

    @Inject
    ArtistFactoryImpl(final MusicianFactory musicianFactory,
                      final GroupArtistFactory groupArtistFactory) {
        this.musicianFactory = musicianFactory;
        this.groupArtistFactory = groupArtistFactory;
    }

    @Override
    public Artist create(final ArtistKey key) {
        VALIDATOR.checkArgument().isNotNull(key, "key");

        final TypeArtist artistType = key.type();
        switch (artistType) {
            case GROUP :
                return this.groupArtistFactory.create(key);
            case MUSICIAN :
                return this.musicianFactory.create(key);
        }
        return VALIDATOR.handleException().runtimeException("Invalid release: " + artistType);
    }

    @Override
    public Artist createEmpty() {
        return VALIDATOR.handleException().unsupportedOperation("Cannot create an empty artist");
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("musicianFactory", this.musicianFactory)
                .add("groupArtistFactory", this.groupArtistFactory)
                .toString();
    }
}
