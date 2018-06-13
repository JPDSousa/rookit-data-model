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
package org.rookit.dm.play;

import com.google.common.base.MoreObjects;
import com.google.inject.Inject;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.bistream.BiStreamFactory;
import org.rookit.api.dm.key.Key;
import org.rookit.api.dm.play.DynamicPlaylist;
import org.rookit.api.dm.play.key.PlaylistKey;
import org.rookit.dm.play.able.event.MutableEventStatsFactory;
import org.rookit.api.dm.play.factory.DynamicPlaylistFactory;
import org.rookit.dm.play.factory.PlaylistBiStream;
import org.rookit.dm.utils.DataModelValidator;
import org.rookit.utils.log.validator.Validator;

final class DynamicPlaylistFactoryImpl implements DynamicPlaylistFactory {

    private static final Validator VALIDATOR = DataModelValidator.getDefault();

    private final MutableEventStatsFactory eventStatsFactory;
    private final BiStreamFactory<Key> biStreamFactory;

    @Inject
    private DynamicPlaylistFactoryImpl(final MutableEventStatsFactory eventStatsFactory,
                                       @PlaylistBiStream final BiStreamFactory<Key> biStreamFactory) {
        this.eventStatsFactory = eventStatsFactory;
        this.biStreamFactory = biStreamFactory;
    }

    @Override
    public DynamicPlaylist create(final PlaylistKey key) {
        final BiStream biStream = this.biStreamFactory.createEmpty();
        return new TrackQueryDynamicPlaylistImpl(key.name(), key.trackQuery(), biStream, this.eventStatsFactory);
    }

    @Override
    public DynamicPlaylist createEmpty() {
        return VALIDATOR.handleException()
                .unsupportedOperation("Cannot create an empty dynamic list.");
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("eventStatsFactory", this.eventStatsFactory)
                .add("biStreamFactory", this.biStreamFactory)
                .toString();
    }
}
