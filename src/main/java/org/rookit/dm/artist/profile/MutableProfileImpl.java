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
package org.rookit.dm.artist.profile;

import com.google.common.base.MoreObjects;
import com.neovisionaries.i18n.CountryCode;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.artist.external.ExternalIdentifiers;
import org.rookit.api.dm.artist.name.ArtistName;
import org.rookit.api.dm.artist.timeline.Timeline;
import org.rookit.dm.artist.external.MutableExternalIdentifiers;
import org.rookit.dm.artist.name.MutableArtistName;
import org.rookit.dm.artist.timeline.MutableTimeline;
import org.rookit.dm.utils.DataModelValidator;
import org.rookit.utils.log.validator.Validator;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Optional;

final class MutableProfileImpl implements MutableProfile {

    private static final Validator VALIDATOR = DataModelValidator.getDefault();

    /**
     * Artist Name
     */
    private final MutableArtistName name;

    /**
     * Artist origin (location)
     */
    @Nullable
    private CountryCode origin;

    private final MutableTimeline timeLine;

    private final MutableExternalIdentifiers externalIdentifiers;

    private final BiStream picture;

    MutableProfileImpl(final MutableArtistName name,
                       final MutableTimeline timeLine,
                       final MutableExternalIdentifiers externalIdentifiers,
                       final BiStream picture) {
        this.name = name;
        this.timeLine = timeLine;
        this.externalIdentifiers = externalIdentifiers;
        this.picture = picture;
    }

    @Override
    public ArtistName name() {
        return this.name;
    }

    @Override
    public Timeline timeline() {
        return this.timeLine;
    }

    @Override
    public ExternalIdentifiers externalIdentifiers() {
        return this.externalIdentifiers;
    }

    @Override
    public Optional<CountryCode> origin() {
        return Optional.ofNullable(this.origin);
    }

    @Override
    public BiStream picture() {
        return this.picture;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", this.name)
                .add("origin", Optional.ofNullable(this.origin))
                .add("timeLine", this.timeLine)
                .add("externalIdentifiers", this.externalIdentifiers)
                .add("picture", this.picture)
                .toString();
    }

    @Override
    public void addAlias(final String alias) {
        this.name.addAlias(alias);
    }

    @Override
    public void clearAliases() {
        this.name.clearAliases();
    }

    @Override
    public void removeAlias(final String alias) {
        this.name.removeAlias(alias);
    }

    @Override
    public void setBeginDate(final LocalDate beginDate) {
        this.timeLine.setBegin(beginDate);
    }

    @Override
    public void setEndDate(final LocalDate endDate) {
        this.timeLine.setEnd(endDate);
    }

    @Override
    public void setIpi(final String ipi) {
        this.externalIdentifiers.setIpi(ipi);
    }

    @Override
    public void setOrigin(final CountryCode origin) {
        VALIDATOR.checkArgument().isNotNull(origin, "origin");
        this.origin = origin;
    }
}
