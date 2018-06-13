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

package org.rookit.dm.track;

import com.google.common.base.MoreObjects;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.track.Track;
import org.rookit.api.dm.track.audio.AudioContent;
import org.rookit.api.dm.track.lyrics.Lyrics;
import org.rookit.dm.genre.AbstractGenreable;
import org.rookit.dm.play.able.event.MutableEventStatsFactory;
import org.rookit.dm.track.artists.MutableTrackArtists;
import org.rookit.dm.track.lyrics.MutableLyrics;
import org.rookit.utils.VoidUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

abstract class AbstractTrack extends AbstractGenreable implements Track {

    private final AudioContent content;
    private final MutableLyrics lyrics;

    protected AbstractTrack(final AudioContent content,
                            final MutableLyrics lyrics,
                            final MutableEventStatsFactory eventStatsFactory) {
        super(eventStatsFactory);
        this.content = content;
        this.lyrics = lyrics;
    }

    @Override
    public AudioContent audio() {
        return this.content;
    }

    @Override
    public Lyrics lyrics() {
        return this.lyrics;
    }
    
    protected abstract MutableTrackArtists mutableArtists();

    @Override
    public Void addFeature(final Artist artist) {
        VALIDATOR.checkArgument().isNotNull(artist, "artist");
        mutableArtists().addFeature(artist);
        return VoidUtils.returnVoid();
    }

    @Override
    public Void addProducer(final Artist producer) {
        VALIDATOR.checkArgument().isNotNull(producer, "producer");
        mutableArtists().addProducer(producer);
        return VoidUtils.returnVoid();
    }

    @Override
    public Void clearFeatures() {
        mutableArtists().clearFeatures();
        return VoidUtils.returnVoid();
    }

    @Override
    public Void clearProducers() {
        mutableArtists().clearProducers();
        return VoidUtils.returnVoid();
    }

    @Override
    public Void removeFeature(final Artist artist) {
        VALIDATOR.checkArgument().isNotNull(artist, "artist");
        mutableArtists().removeFeature(artist);
        return VoidUtils.returnVoid();
    }

    @Override
    public Void removeProducer(final Artist producer) {
        VALIDATOR.checkArgument().isNotNull(producer, "producer");
        mutableArtists().removeProducer(producer);
        return VoidUtils.returnVoid();
    }

    @Override
    public Void setAudioContent(final byte[] audioContent) {
        VALIDATOR.checkArgument().isNotNull(audioContent, "audio");
        final BiStream content = audio().content();
        content.clear();
        try (final OutputStream output = content.writeTo()) {
            output.write(audioContent);
        } catch (final IOException e) {
            return VALIDATOR.handleException().inputOutputException(e);
        }

        return VoidUtils.returnVoid();
    }

    @Override
    public Void setExplicit(final boolean explicit) {
        this.lyrics.setExplicit(explicit);
        return VoidUtils.returnVoid();
    }

    @Override
    public Void setFeatures(final Collection<Artist> features) {
        VALIDATOR.checkArgument().isNotNull(features, "features");
        mutableArtists().setFeatures(features);
        return VoidUtils.returnVoid();
    }

    @Override
    public Void setLyrics(final String lyrics) {
        VALIDATOR.checkArgument().isNotEmpty(lyrics, "lyrics");
        this.lyrics.setLyrics(lyrics);
        return VoidUtils.returnVoid();
    }

    @Override
    public Void setProducers(final Collection<Artist> producers) {
        VALIDATOR.checkArgument().isNotNull(producers, "producers");
        mutableArtists().setProducers(producers);
        return VoidUtils.returnVoid();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("content", this.content)
                .add("lyrics", this.lyrics)
                .add("artists", mutableArtists())
                .toString();
    }
}
