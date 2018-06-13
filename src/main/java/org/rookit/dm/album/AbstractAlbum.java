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

package org.rookit.dm.album;

import com.google.common.base.MoreObjects;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.album.Album;
import org.rookit.api.dm.album.release.Release;
import org.rookit.api.dm.album.slot.TrackSlot;
import org.rookit.api.dm.album.tracks.AlbumTracks;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.track.Track;
import org.rookit.dm.album.release.MutableRelease;
import org.rookit.dm.album.tracks.MutableAlbumTracks;
import org.rookit.dm.genre.AbstractGenreable;
import org.rookit.dm.play.able.event.MutableEventStatsFactory;
import org.rookit.dm.utils.DataModelValidator;
import org.rookit.utils.VoidUtils;
import org.rookit.utils.log.validator.Validator;

import java.io.IOException;
import java.io.OutputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;


/**
 * Abstract implementation of the {@link Album} interface. Extend this class in
 * order to create a custom album release.
 */
public abstract class AbstractAlbum extends AbstractGenreable implements Album {

    private static final Validator VALIDATOR = DataModelValidator.getDefault();

    /**
     * Title of the album
     */
    private final String title;

    private final MutableRelease release;

    /**
     * Smof GridFS Reference containing the image of the album
     */
    private final BiStream cover;

    private final MutableAlbumTracks tracks;

    /**
     * Default constructor for the object. All subclasses should use this
     * constructor in order to create a fully functional album.
     *
     * @param name title of the album
     * @param release release information about the album
     */
    protected AbstractAlbum(final String name,
                            final MutableRelease release,
                            final BiStream cover,
                            final MutableAlbumTracks tracks,
                            final MutableEventStatsFactory eventStatsFactory) {
        super(eventStatsFactory);
        this.title = name;
        this.release = release;
        this.cover = cover;
        this.tracks = tracks;
    }

    @Override
    public Void addTrack(final Track track, final int number, final String discName) {
        this.tracks.addTrack(track, number, discName);
        return VoidUtils.returnVoid();
    }

    @Override
    public final Void addTrackLast(final Track track, final String discName) {
        this.tracks.addTrackLast(track, discName);
        return VoidUtils.returnVoid();
    }

    @Override
    public Void addTrack(final TrackSlot slot) {
        slot.track().ifPresent(track -> addTrack(track, slot.number(), slot.discName()));
        return VoidUtils.returnVoid();
    }

    @Override
    public Void clearTracks() {
        this.tracks.clearTracks();
        return VoidUtils.returnVoid();
    }

    @SuppressWarnings("RedundantMethodOverride")
    @Override
    public Collection<Genre> allGenres() {
        return Album.super.allGenres();
    }

    @Override
    public BiStream cover() {
        return this.cover;
    }

    @SuppressWarnings("RedundantMethodOverride")
    @Override
    public Optional<Duration> duration() {
        return Album.super.duration();
    }

    @Override
    public final Release release() {
        return this.release;
    }

    @Override
    public String title() {
        return this.title;
    }

    @Override
    public final Void relocate(final String discName,
            final int number,
            final String newDiscName,
            final int newNumber) {
        this.tracks.relocate(discName, number, newDiscName, newNumber);
        return VoidUtils.returnVoid();
    }

    @Override
    public Void removeTrack(final int number, final String disc) {
        this.tracks.removeTrack(number, disc);
        return VoidUtils.returnVoid();
    }

    @Override
    public Void removeTrack(final Track track) {
        this.tracks.removeTrack(track);
        return VoidUtils.returnVoid();
    }

    @Override
    public final Void setCover(final byte[] bytes) {
        VALIDATOR.checkArgument().isNotNull(bytes, "bytes");
        try (final OutputStream output = this.cover.writeTo()) {
            output.write(bytes);
            return VoidUtils.returnVoid();
        } catch (final IOException e) {
            return VALIDATOR.handleException().inputOutputException(e);
        }
    }

    @Override
    public final Void setDuration(final Duration duration) {
        return VALIDATOR.handleException().unsupportedOperation("Cannot set duration for albums");
    }

    @Override
    public final Void setReleaseDate(final LocalDate date) {
        VALIDATOR.checkArgument().isNotNull(date, "year");
        this.release.setReleaseDate(date);
        return VoidUtils.returnVoid();
    }

    @Override
    public AlbumTracks tracks() {
        return this.tracks;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("title", this.title)
                .add("release", this.release)
                .add("cover", this.cover)
                .add("tracks", this.tracks)
                .toString();
    }
}
