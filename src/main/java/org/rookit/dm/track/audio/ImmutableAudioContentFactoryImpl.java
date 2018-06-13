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
package org.rookit.dm.track.audio;

import com.google.common.base.MoreObjects;
import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;
import org.rookit.api.bistream.BiStreamFactory;
import org.rookit.api.dm.key.Key;
import org.rookit.api.dm.track.audio.AudioContent;
import org.rookit.api.dm.track.audio.AudioContentFactory;
import org.rookit.api.dm.track.audio.AudioFeatureFactory;
import org.rookit.api.dm.track.audio.ImmutableAudioContent;
import org.rookit.dm.track.factory.TrackBiStream;
import org.rookit.dm.utils.DataModelValidator;
import org.rookit.utils.log.validator.Validator;

final class ImmutableAudioContentFactoryImpl implements AudioContentFactory {

    private static final Validator VALIDATOR = DataModelValidator.getDefault();
    private static final Logger logger = VALIDATOR.getLogger(ImmutableAudioContentFactoryImpl.class);

    private final BiStreamFactory<Key> biStreamFactory;
    private final AudioFeatureFactory audioFeatureFactory;

    @Inject
    private ImmutableAudioContentFactoryImpl(@TrackBiStream final BiStreamFactory<Key> biStreamFactory,
                                             final AudioFeatureFactory audioFeatureFactory) {
        this.biStreamFactory = biStreamFactory;
        this.audioFeatureFactory = audioFeatureFactory;
    }

    @Override
    public AudioContent create(final Key key) {
        logger.info("Creation by key is not supported. Creating empty instead.");
        return createEmpty();
    }

    @Override
    public AudioContent createEmpty() {
        return ImmutableAudioContent.builder()
                .content(this.biStreamFactory.createEmpty())
                .features(this.audioFeatureFactory.createEmpty())
                .build();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("biStreamFactory", this.biStreamFactory)
                .add("audioFeatureFactory", this.audioFeatureFactory)
                .toString();
    }
}
