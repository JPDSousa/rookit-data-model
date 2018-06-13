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
package org.rookit.dm.track.artists;

import com.google.common.base.MoreObjects;
import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.key.Key;
import org.rookit.api.dm.track.artist.TrackArtists;
import org.rookit.api.dm.track.artist.VersionTrackArtists;
import org.rookit.api.dm.track.artist.VersionTrackArtistsFactory;
import org.rookit.dm.utils.DataModelValidator;
import org.rookit.utils.log.validator.Validator;

final class VersionTrackArtistsFactoryImpl implements VersionTrackArtistsFactory {

    private static final Validator VALIDATOR = DataModelValidator.getDefault();
    private static final Logger logger = VALIDATOR.getLogger(VersionTrackArtistsFactoryImpl.class);

    private final MutableTrackArtistsFactory factory;

    @Inject
    private VersionTrackArtistsFactoryImpl(final MutableTrackArtistsFactory factory) {
        this.factory = factory;
    }

    @Override
    public VersionTrackArtists create(final TrackArtists originalArtists, final Iterable<Artist> versionArtists) {
        return new MutableVersionTrackArtistsImpl(originalArtists, versionArtists);
    }

    @Override
    public VersionTrackArtists create(final Key key) {
        logger.info("Creation by key is not supported. Creating empty instead.");
        return createEmpty();
    }

    @Override
    public VersionTrackArtists createEmpty() {
        return VALIDATOR.handleException()
                .unsupportedOperation("Cannot create an empty %s, as a non empty set of version artists is required.",
                        getClass());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("factory", this.factory)
                .toString();
    }
}
