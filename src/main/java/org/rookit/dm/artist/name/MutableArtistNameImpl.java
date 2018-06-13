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
import com.google.common.base.Objects;
import com.google.common.collect.Sets;
import org.rookit.api.dm.artist.name.ArtistName;
import org.rookit.dm.utils.DataModelValidator;
import org.rookit.utils.log.validator.Validator;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.Collection;
import java.util.Collections;

@NotThreadSafe
final class MutableArtistNameImpl implements MutableArtistName {

    private static final Validator VALIDATOR = DataModelValidator.getDefault();

    private final String name;
    private final Collection<String> aliases;

    MutableArtistNameImpl(final String name) {
        this.name = name;
        this.aliases = Sets.newLinkedHashSet();
    }

    @Override
    public void addAlias(final String alias) {
        VALIDATOR.checkArgument().isNotEmpty(alias, "alias");
        this.aliases.add(alias);
    }

    @Override
    public void clearAliases() {
        this.aliases.clear();
    }

    @Override
    public void removeAlias(final String alias) {
        VALIDATOR.checkArgument().isNotEmpty(alias, "alias");
        this.aliases.remove(alias);
    }

    @Override
    public String official() {
        return this.name;
    }

    @Override
    public Collection<String> aliases() {
        return Collections.unmodifiableCollection(this.aliases);
    }

    @Override
    public int compareTo(final ArtistName o) {
        return official().compareTo(o.official());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", this.name)
                .add("aliases", this.aliases)
                .toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof MutableArtistNameImpl)) return false;
        return compareTo((ArtistName) o) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.name);
    }
}
