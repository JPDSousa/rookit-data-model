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
package org.rookit.dm.track.title;

import com.google.common.base.MoreObjects;
import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;
import org.rookit.api.dm.key.Key;
import org.rookit.api.dm.track.TypeVersion;
import org.rookit.api.dm.track.artist.TrackArtists;
import org.rookit.api.dm.track.artist.VersionTrackArtists;
import org.rookit.api.dm.track.title.TrackTitle;
import org.rookit.api.dm.track.title.TrackTitleFactory;
import org.rookit.dm.utils.DataModelValidator;
import org.rookit.utils.log.validator.Validator;

final class TrackTitleFactoryImpl implements TrackTitleFactory {

    private static final Validator VALIDATOR = DataModelValidator.getDefault();
    private static final Logger logger = VALIDATOR.getLogger(TrackTitleFactoryImpl.class);

    private final MutableTitleFactory titleFactory;

    @Inject
    private TrackTitleFactoryImpl(final MutableTitleFactory titleFactory) {
        this.titleFactory = titleFactory;
    }

    @Override
    public TrackTitle create(final String title, final TrackArtists artists) {
        return new TrackTitleImpl(title, artists, this.titleFactory);
    }

    @Override
    public TrackTitle createVersionTrackTitle(final TrackTitle original,
                                              final VersionTrackArtists artists,
                                              final TypeVersion typeVersion) {
        return new VersionTrackTitleImpl(original, artists, typeVersion);
    }

    @Override
    public TrackTitle create(final Key key) {
        logger.info("Creation by key is not supported. Creating empty instead");
        return createEmpty();
    }

    @Override
    public TrackTitle createEmpty() {
        return VALIDATOR.handleException().runtimeException("Cannot create an empty Track Title.");
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("titleFactory", this.titleFactory)
                .toString();
    }
}
