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

import com.google.inject.Inject;
import org.rookit.api.dm.artist.name.ArtistName;
import org.rookit.api.dm.artist.name.ArtistNameFactory;
import org.rookit.api.dm.artist.name.ArtistNameKey;
import org.rookit.dm.utils.DataModelValidator;
import org.rookit.utils.log.validator.Validator;

final class ArtistNameFactoryImpl implements ArtistNameFactory {

    private static final Validator VALIDATOR = DataModelValidator.getDefault();

    @Inject
    private ArtistNameFactoryImpl() { }

    @Override
    public ArtistName create(final String official) {
        VALIDATOR.checkArgument().isNotEmpty(official, "official");
        return new MutableArtistNameImpl(official);
    }

    @Override
    public ArtistName create(final ArtistNameKey key) {
        return create(key.officialName());
    }

    @Override
    public ArtistName createEmpty() {
        return VALIDATOR.handleException()
                .runtimeException("Cannot create an empty ArtistName as no default name is set.");
    }

}
