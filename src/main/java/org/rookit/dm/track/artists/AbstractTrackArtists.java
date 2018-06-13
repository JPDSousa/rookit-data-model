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
import com.google.common.collect.Sets;
import org.rookit.api.dm.artist.Artist;
import org.rookit.dm.utils.DataModelValidator;
import org.rookit.utils.log.validator.Validator;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.Collection;
import java.util.Collections;

@NotThreadSafe
abstract class AbstractTrackArtists implements MutableTrackArtists {

    private static final Validator VALIDATOR = DataModelValidator.getDefault();

    private Collection<Artist> features;
    private Collection<Artist> producers;

    AbstractTrackArtists() {
        this.features = Sets.newLinkedHashSet();
        this.producers = Sets.newLinkedHashSet();
    }
    @Override
    public void addFeature(final Artist artist) {
        final String feature = "feature";
        VALIDATOR.checkArgument().isNotContainedIn(artist, mainArtists(), feature);
        VALIDATOR.checkArgument().isNotContainedIn(artist, producers(), feature);
        this.features.add(artist);
    }

    @Override
    public void removeFeature(final Artist artist) {
        VALIDATOR.checkArgument().isNotNull(artist, "artist");
        this.features.remove(artist);
    }

    @Override
    public void setFeatures(final Collection<Artist> artists) {
        VALIDATOR.checkArgument().isNotIntersecting(artists, mainArtists(), "features", "mainArtists");
        VALIDATOR.checkArgument().isNotIntersecting(artists, producers(), "features", "producers");
        this.features = Sets.newHashSet(artists);
    }

    @Override
    public void clearFeatures() {
        this.features.clear();
    }

    @Override
    public void addProducer(final Artist artist) {
        final String producer = "producer";
        VALIDATOR.checkArgument().isNotContainedIn(artist, mainArtists(), producer);
        VALIDATOR.checkArgument().isNotContainedIn(artist, features(), producer);
        this.producers.add(artist);
    }

    @Override
    public void removeProducer(final Artist artist) {
        VALIDATOR.checkArgument().isNotNull(artist, "artist");
        this.producers.remove(artist);
    }

    @Override
    public void setProducers(final Collection<Artist> artists) {
        VALIDATOR.checkArgument().isNotIntersecting(artists, mainArtists(), "producers", "mainArtists");
        VALIDATOR.checkArgument().isNotIntersecting(artists, features(), "producers", "features");
        this.producers = Sets.newHashSet(artists);
    }

    @Override
    public void clearProducers() {
        this.producers.clear();
    }

    @Override
    public Collection<Artist> features() {
        return Collections.unmodifiableCollection(this.features);
    }

    @Override
    public Collection<Artist> producers() {
        return Collections.unmodifiableCollection(this.producers);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("features", this.features)
                .add("producers", this.producers)
                .toString();
    }
}
