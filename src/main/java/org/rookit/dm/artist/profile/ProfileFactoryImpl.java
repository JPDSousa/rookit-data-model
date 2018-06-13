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
import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;
import org.rookit.api.bistream.BiStreamFactory;
import org.rookit.api.dm.artist.profile.Profile;
import org.rookit.api.dm.artist.profile.ProfileFactory;
import org.rookit.api.dm.key.Key;
import org.rookit.dm.artist.external.MutableExternalIdentifiersFactory;
import org.rookit.dm.artist.factory.ArtistBiStream;
import org.rookit.dm.artist.name.MutableArtistNameFactory;
import org.rookit.dm.artist.timeline.MutableTimelineFactory;
import org.rookit.dm.utils.DataModelValidator;
import org.rookit.utils.log.validator.Validator;

final class ProfileFactoryImpl implements ProfileFactory {

    private static final Validator VALIDATOR = DataModelValidator.getDefault();
    private static final Logger logger = VALIDATOR.getLogger(ProfileFactoryImpl.class);

    private final MutableArtistNameFactory artistNameFactory;
    private final MutableTimelineFactory timelineFactory;
    private final MutableExternalIdentifiersFactory identifiersFactory;
    private final BiStreamFactory<Key> biStreamFactory;

    @Inject
    private ProfileFactoryImpl(final MutableArtistNameFactory artistNameFactory,
                               final MutableTimelineFactory timelineFactory,
                               final MutableExternalIdentifiersFactory identifiersFactory,
                               @ArtistBiStream final BiStreamFactory<Key> biStreamFactory) {
        this.artistNameFactory = artistNameFactory;
        this.timelineFactory = timelineFactory;
        this.identifiersFactory = identifiersFactory;
        this.biStreamFactory = biStreamFactory;
    }

    @Override
    public Profile create(final Key key) {
        logger.info("Creation by key not available. Creating empty instead.");
        return createEmpty();
    }

    @Override
    public Profile createEmpty() {
        return new MutableProfileImpl(
                this.artistNameFactory.createEmpty(),
                this.timelineFactory.createEmpty(),
                this.identifiersFactory.createEmpty(),
                this.biStreamFactory.createEmpty());
    }

    @Override
    public Profile create(final String name, final String isni) {
        return new MutableProfileImpl(
                this.artistNameFactory.create(name),
                this.timelineFactory.createEmpty(),
                this.identifiersFactory.create(isni),
                this.biStreamFactory.createEmpty());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("artistNameFactory", this.artistNameFactory)
                .add("timelineFactory", this.timelineFactory)
                .add("identifiersFactory", this.identifiersFactory)
                .add("biStreamFactory", this.biStreamFactory)
                .toString();
    }
}
