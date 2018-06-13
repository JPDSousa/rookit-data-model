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
package org.rookit.dm.artist;

import com.google.common.base.MoreObjects;
import com.google.inject.Inject;
import org.rookit.api.dm.artist.Musician;
import org.rookit.api.dm.artist.TypeGender;
import org.rookit.api.dm.artist.key.ArtistKey;
import org.rookit.dm.artist.config.ArtistConfig;
import org.rookit.api.dm.artist.factory.MusicianFactory;
import org.rookit.dm.artist.profile.MutableProfile;
import org.rookit.dm.artist.profile.MutableProfileFactory;
import org.rookit.dm.play.able.event.MutableEventStatsFactory;
import org.rookit.dm.utils.DataModelValidator;
import org.rookit.utils.log.validator.Validator;

final class MusicianFactoryImpl implements MusicianFactory {

    private static final Validator VALIDATOR = DataModelValidator.getDefault();

    private final MutableProfileFactory profileFactory;
    private final MutableEventStatsFactory eventStatsFactory;
    private final ArtistConfig config;

    @Inject
    private MusicianFactoryImpl(final MutableProfileFactory profileFactory,
                                final MutableEventStatsFactory eventStatsFactory,
                                final ArtistConfig config) {
        this.profileFactory = profileFactory;
        this.eventStatsFactory = eventStatsFactory;
        this.config = config;
    }

    @Override
    public Musician create(final String musicianName,
                           final String isni,
                           final TypeGender genderType,
                           final String fullName) {
        final MutableProfile profile = this.profileFactory.create(musicianName, isni);
        return new MusicianImpl(profile, genderType, fullName, this.eventStatsFactory);
    }

    @Override
    public Musician create(final String musicianName, final String isni) {
        return create(musicianName, isni, this.config.defaultGender(), musicianName);
    }

    @Override
    public Musician create(final ArtistKey key) {
        VALIDATOR.checkArgument().isNotNull(key, "key");

        return create(key.name(), key.isni(), key.gender(), key.fullName());
    }

    @Override
    public Musician createEmpty() {
        final MutableProfile profile = this.profileFactory.createEmpty();
        return new MusicianImpl(profile,
                this.config.defaultGender(),
                profile.name().official(),
                this.eventStatsFactory);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("profileFactory", this.profileFactory)
                .add("eventStatsFactory", this.eventStatsFactory)
                .add("config", this.config)
                .toString();
    }
}
