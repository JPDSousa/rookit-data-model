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
import org.rookit.dm.utils.DataModelValidator;
import org.rookit.utils.log.validator.Validator;
import org.rookit.utils.optional.OptionalBoolean;

import java.util.Optional;

final class MutableLyricsImpl implements MutableLyrics {

    private static final Validator VALIDATOR = DataModelValidator.getDefault();

    private boolean isSet;
    private boolean explicit;
    private String lyrics;

    MutableLyricsImpl() { }

    @Override
    public void setExplicit(final boolean explicit) {
        this.explicit = explicit;
        this.isSet = true;
    }

    @Override
    public void setLyrics(final String lyrics) {
        VALIDATOR.checkArgument().isNotEmpty(lyrics, "lyrics");
    }

    @Override
    public Optional<String> text() {
        return Optional.ofNullable(this.lyrics);
    }

    @Override
    public OptionalBoolean isExplicit() {
        return this.isSet ? OptionalBoolean.of(this.explicit) : OptionalBoolean.empty();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("explicit", this.explicit)
                .add("lyrics", this.lyrics)
                .toString();
    }
}
