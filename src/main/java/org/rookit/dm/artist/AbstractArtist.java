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

package org.rookit.dm.artist;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Sets;
import com.neovisionaries.i18n.CountryCode;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.artist.profile.Profile;
import org.rookit.dm.artist.profile.MutableProfile;
import org.rookit.dm.genre.AbstractGenreable;
import org.rookit.dm.play.able.event.MutableEventStatsFactory;
import org.rookit.dm.utils.DataModelValidator;
import org.rookit.utils.VoidUtils;
import org.rookit.utils.log.validator.Validator;

import javax.annotation.concurrent.NotThreadSafe;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * Abstract implementation of the {@link Artist} interface. Extend this class in
 * order to create a custom artist.
 */
@NotThreadSafe
abstract class AbstractArtist extends AbstractGenreable implements Artist {

    protected static final Validator VALIDATOR = DataModelValidator.getDefault();

    /**
     * Set of Related artists
     */
    private final Set<Artist> related;

    private final MutableProfile profile;

    /**
     * Abstract constructor. Use this constructor to Initialize all the class
     * fields.
     *
     */
    AbstractArtist(final MutableProfile profile,
                   final MutableEventStatsFactory eventStatsFactory) {
        super(eventStatsFactory);
        this.profile = profile;
        this.related = Sets.newLinkedHashSet();
    }

    @Override
    public Void addAlias(final String alias) {
        VALIDATOR.checkArgument().isNotEmpty(alias, "alias");
        this.profile.addAlias(alias);
        return VoidUtils.returnVoid();
    }

    @Override
    public Void addRelatedArtist(final Artist artist) {
        VALIDATOR.checkArgument().isNotNull(artist, "related artist");
        this.related.add(artist);
        return VoidUtils.returnVoid();
    }

    @Override
    public Void clearAliases() {
        this.profile.clearAliases();
        return VoidUtils.returnVoid();
    }

    @Override
    public Profile profile() {
        return this.profile;
    }

    @Override
    public Collection<Artist> relatedArtists() {
        return Collections.unmodifiableCollection(this.related);
    }

    @Override
    public Void removeAlias(final String alias) {
        VALIDATOR.checkArgument().isNotEmpty(alias, "alias");
        this.profile.removeAlias(alias);
        return VoidUtils.returnVoid();
    }

    @Override
    public Void setAliases(final Collection<String> aliases) {
        VALIDATOR.checkArgument().isNotNull(aliases, "aliases");
        clearAliases();
        aliases.forEach(this::addAlias);
        return VoidUtils.returnVoid();
    }

    @Override
    public Void setBeginDate(final LocalDate beginDate) {
        VALIDATOR.checkArgument().isNotNull(beginDate, "beginDate");
        this.profile.setBeginDate(beginDate);
        return VoidUtils.returnVoid();
    }

    @Override
    public Void setEndDate(final LocalDate endDate) {
        VALIDATOR.checkArgument().isNotNull(endDate, "endDate");
        this.profile.setEndDate(endDate);
        return VoidUtils.returnVoid();
    }

    @Override
    public Void setIPI(final String ipi) {
        VALIDATOR.checkArgument().isNotNull(ipi, "ipi");
        this.profile.setIpi(ipi);
        return VoidUtils.returnVoid();
    }

    @Override
    public Void setOrigin(final CountryCode origin) {
        VALIDATOR.checkArgument().isNotNull(origin, "origin");
        this.profile.setOrigin(origin);
        return VoidUtils.returnVoid();
    }

    @Override
    public Void setPicture(final byte[] picture) {
        VALIDATOR.checkArgument().isNotNull(picture, "picture");
        try (final OutputStream output = this.profile.picture().writeTo()) {
            output.write(picture);
            return VoidUtils.returnVoid();
        } catch (final IOException e) {
            return VALIDATOR.handleException().inputOutputException(e);
        }
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("related", this.related)
                .add("profile", this.profile)
                .toString();
    }
}
