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

package org.rookit.dm.genre;

import com.google.common.base.MoreObjects;
import com.google.inject.Inject;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.genre.factory.GenreFactory;
import org.rookit.api.dm.genre.key.GenreKey;
import org.rookit.dm.genre.config.GenreConfig;
import org.rookit.dm.utils.DataModelValidator;
import org.rookit.utils.log.validator.Validator;

@SuppressWarnings("javadoc")
final class GenreFactoryImpl implements GenreFactory {

    private static final Validator VALIDATOR = DataModelValidator.getDefault();

    private final GenreConfig config;

    @Inject
    private GenreFactoryImpl(final GenreConfig config) {
        this.config = config;
    }

    @Override
    public Genre create(final GenreKey key) {
        VALIDATOR.checkArgument().isNotNull(key, "key");

        return createGenre(key.getName());
    }

    @Override
    public Genre createEmpty() {
        return VALIDATOR.handleException().unsupportedOperation("Cannot create an empty genre");
    }

    @Override
    public Genre createGenre(final String name) {
        VALIDATOR.checkArgument().isNotEmpty(name, "official");
        return new GenreImpl(name);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("config", this.config)
                .toString();
    }
}
