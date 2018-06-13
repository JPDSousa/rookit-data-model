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
import org.apache.commons.lang3.StringUtils;
import org.rookit.api.dm.genre.Genre;
import org.rookit.dm.play.able.AbstractPlayable;
import org.rookit.dm.utils.DataModelValidator;
import org.rookit.utils.VoidUtils;
import org.rookit.utils.log.validator.Validator;

import javax.annotation.Generated;
import java.util.Objects;
import java.util.Optional;

final class GenreImpl extends AbstractPlayable implements Genre {

    private static final String NO_DESCRIPTION = StringUtils.EMPTY;

    private static final Validator VALIDATOR = DataModelValidator.getDefault();

    private final String name;

    private String description;

    GenreImpl(final String name) {
        this.name = name;
        this.description = NO_DESCRIPTION;
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public boolean equals(final Object object) {
        if (object instanceof GenreImpl) {
            if (!super.equals(object)) {
                return false;
            }
            final GenreImpl that = (GenreImpl) object;
            return Objects.equals(this.name, that.name) && Objects.equals(this.description, that.description);
        }
        return false;
    }

    @Override
    public Optional<String> description() {
        return Optional.ofNullable(this.description)
                .filter(desc -> !StringUtils.equals(desc, NO_DESCRIPTION));
    }

    @Override
    public String name() {
        return this.name;
    }

    @SuppressWarnings("boxing")
    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.name, this.description);
    }

    @Override
    public Void resetDescription() {
        this.description = NO_DESCRIPTION;
        return VoidUtils.returnVoid();
    }

    @Override
    public Void setDescription(final String description) {
        VALIDATOR.checkArgument().isNotEmpty(description, "description");
        this.description = description;
        return VoidUtils.returnVoid();
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("super", super.toString())
                .add("official", this.name)
                .add("description", this.description)
                .toString();
    }

}
