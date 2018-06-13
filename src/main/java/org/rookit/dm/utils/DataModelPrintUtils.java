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

package org.rookit.dm.utils;

import com.google.common.collect.Iterables;
import org.extendedStringBuilder.ExtStringBuilder;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.album.Album;
import org.rookit.api.dm.album.disc.Disc;
import org.rookit.api.dm.album.slot.TrackSlot;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.track.Track;
import org.rookit.api.dm.track.VersionTrack;
import org.rookit.utils.print.TypeFormat;

import java.time.Duration;
import java.util.Optional;

import static org.rookit.utils.print.PrintUtils.duration;
import static org.rookit.utils.print.PrintUtils.getIterableAsString;

@SuppressWarnings("javadoc")
// TODO refactor me please!!!
public final class DataModelPrintUtils {

    public static String album(final Album album) {
        final ExtStringBuilder builder = ExtStringBuilder.create();

        builder.append("Album title: ")
                .append(album.title())
                .breakLine()
                .append("Type fromRelease: ")
                .append(album.release())
                .breakLine()
                .append("Artists:")
                .breakLine();
        for (final Artist artist : album.artists()) {
            builder.tab().append(artist.name()).breakLine();
        }
        for (final Disc disc : album.tracks().getDiscs()) {
            builder.append("Disc: ").append(disc).breakLine();
            for (final int trackNumber : disc.asTrackMap().keySet()) {
                printAlbumTrack(builder, album, disc.name(), trackNumber);
            }
        }
        builder.append("Artwork: ").appendIf(album.cover() != null, "Yes", "No").breakLine();

        return builder.toString();
    }

    public static String artist(final Artist artist) {
        final StringBuilder builder = new StringBuilder(artist.name());
        builder.append('\n')
                .append("Aliases: ")
                .append(getIterableAsString(artist.aliases(), TypeFormat.TAG))
                .append('\n');

        return builder.toString();
    }

    public static String track(final Track track) {
        final ExtStringBuilder builder = ExtStringBuilder.create();
        final BiStream path = track.audio();
        if (path != null && path.isEmpty()) {
            builder.append("Content: Yes\n");
        }
        builder.append("Type: ")
                .append(track.type())
                .breakLine()
                .appendIf(track.getId().isPresent(), "Id: " + track.getId().get() + '\n')
                .append("Title: ")
                .append(track.title().getTitle())
                .breakLine()
                .append("Main Artists: ")
                .append(getIterableAsString(track.mainArtists(), TypeFormat.TAG))
                .breakLine()
                .appendIf(!Iterables.isEmpty(track.features()),
                        "Features: " + getIterableAsString(track.features(), TypeFormat.TAG) + '\n')
                .appendIf(!Iterables.isEmpty(track.producers()),
                        "Producers: " + getIterableAsString(track.producers(), TypeFormat.TAG) + '\n')
                .appendIf(!Iterables.isEmpty(track.getGenres()),
                        "Genres: " + getIterableAsString(track.getGenres(), TypeFormat.TAG) + '\n')
                .append("Plays: ")
                .append(track.getPlays())
                .breakLine()
                .append("Duration: ")
                .append(duration(track.duration().orElse(Duration.ZERO)))
                .breakLine();
        if (track.isVersionTrack()) {
            final VersionTrack versionTrack = track.asVersionTrack().get();
            builder.append("Version Type: ")
                    .append(versionTrack.getVersionType())
                    .breakLine()
                    .append("Version Artists: ")
                    .append(getIterableAsString(versionTrack.getVersionArtists(), TypeFormat.TAG))
                    .breakLine();
        }
        return builder
                .breakLine()
                .toString();
    }

    private static void printAlbumTrack(final ExtStringBuilder builder,
            final Album album,
            final String discName,
            final int number) {
        final TrackSlot trackSlot = album.tracks().asSlots().getTrack(discName, number);
        final Optional<Track> trackOrNone = trackSlot.track();
        if (trackOrNone.isPresent()) {
            final Track track = trackOrNone.get();
            builder.append(number)
                    .tab()
                    .append(track.title())
                    .breakLine()
                    .tab()
                    .append("Disc: ")
                    .append(trackSlot.discName())
                    .breakLine()
                    .tab()
                    .append("Number: ")
                    .append(trackSlot.number())
                    .breakLine()
                    .tab()
                    .append("Main Artists: ")
                    .breakLine();
            for (final Artist artist : track.mainArtists()) {
                builder.tab(2).append(artist.toString()).breakLine();
            }
            builder.tab().append("Features: ").breakLine();
            for (final Artist artist : track.features()) {
                builder.tab(2).append(artist.toString()).breakLine();
            }
            builder.tab().append("Type: ").append(track.type()).breakLine();
            if (track.isVersionTrack()) {
                builder.tab().append("Extras: ");
                for (final Artist artist : track.asVersionTrack().get().getVersionArtists()) {
                    builder.tab(2).append(artist.toString()).breakLine();
                }
            }
        }
    }

    private DataModelPrintUtils() {
    }

}
