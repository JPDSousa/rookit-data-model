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

import static org.rookit.utils.print.PrintUtils.duration;
import static org.rookit.utils.print.PrintUtils.getIterableAsString;

import java.time.Duration;
import java.util.Optional;

import org.extendedStringBuilder.ExtStringBuilder;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.album.Album;
import org.rookit.api.dm.album.TrackSlot;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.track.Track;
import org.rookit.api.dm.track.VersionTrack;
import org.rookit.utils.print.TypeFormat;

import com.google.common.collect.Iterables;

@SuppressWarnings("javadoc")
public final class DMPrintUtils {
	
	public static String album(Album album){
		final ExtStringBuilder builder = ExtStringBuilder.create();

		builder.append("Album title: ").append(album.getTitle()).breakLine()
		.append("Type Release: ").append(album.getReleaseType()).breakLine()
		.append("Artists:").breakLine();
		for(Artist artist : album.getArtists()){
			builder.tab().append(artist.getName()).breakLine();
		}
		for(String disc : album.getDiscs()){
			builder.append("Disc: ").append(disc).breakLine();
			for(Integer number : album.getTrackNumbers(disc)){
				printAlbumTrack(builder, album, disc, number);
			}
		}
		builder.append("Artwork: ").appendIf(album.getCover() != null, "Yes", "No").breakLine();

		return builder.toString();
	}

	private static void printAlbumTrack(ExtStringBuilder builder, Album album, String discName, Integer number) {
		final TrackSlot trackSlot = album.getTrack(discName, number);
		final Optional<Track> trackOrNone = trackSlot.getTrack();
		if (trackOrNone.isPresent()) {
			final Track track = trackOrNone.get();
			builder.append(number).tab()
			.append(track.getTitle()).breakLine()
			.tab().append("Disc: ").append(trackSlot.getDisc()).breakLine()
			.tab().append("Number: ").append(trackSlot.getNumber()).breakLine()
			.tab().append("Main Artists: ").breakLine();
			for(Artist artist : track.getMainArtists()){
				builder.tab(2).append(artist.toString()).breakLine();
			}
			builder.tab().append("Features: ").breakLine();
			for(Artist artist : track.getFeatures()){
				builder.tab(2).append(artist.toString()).breakLine();
			}
			builder.tab().append("Type: ").append(track.getType()).breakLine();
			if(track.isVersionTrack()) {
				builder.tab().append("Extras: ");
				for(Artist artist : track.getAsVersionTrack().get().getVersionArtists()){
					builder.tab(2).append(artist.toString()).breakLine();
				}
			}
		}
	}

	public static String track(Track track){
		final ExtStringBuilder builder = ExtStringBuilder.create();
		final BiStream path = track.getPath();
		if(path != null && path.isEmpty()) {
			builder.append("Content: Yes\n");
		}
		builder.append("Type: ").append(track.getType()).breakLine()
		.appendIf(track.getIdAsString() != null, "Id: " + track.getIdAsString() + "\n")
		.append("Title: ").append(track.getTitle().getTitle()).breakLine()
		.append("Main Artists: ").append(getIterableAsString(track.getMainArtists(), TypeFormat.TAG)).breakLine()
		.appendIf(!Iterables.isEmpty(track.getFeatures()), "Features: " + getIterableAsString(track.getFeatures(), TypeFormat.TAG) + "\n")
		.appendIf(!Iterables.isEmpty(track.getProducers()), "Producers: " + getIterableAsString(track.getProducers(), TypeFormat.TAG) + "\n")
		.appendIf(!Iterables.isEmpty(track.getGenres()), "Genres: " + getIterableAsString(track.getGenres(), TypeFormat.TAG) + "\n")
		.appendIf(!track.getHiddenTrack().isPresent(), "HiddenTrack: " + track.getHiddenTrack().get() + "\n")
		.append("Plays: ").append(track.getPlays()).breakLine()
		.append("Duration: ").append(duration(track.getDuration().orElse(Duration.ZERO))).breakLine();
		if(track.isVersionTrack()) {
			final VersionTrack versionTrack = track.getAsVersionTrack().get();
			builder.append("Version Type: ").append(versionTrack.getVersionType()).breakLine()
			.append("Version Artists: ").append(getIterableAsString(versionTrack.getVersionArtists(), TypeFormat.TAG)).breakLine();
		}
		return builder
				.breakLine()
				.toString();
	}
	
	public static String artist(Artist artist) {
		final StringBuilder builder = new StringBuilder(artist.getName());
		builder.append("\n")
		.append("Aliases: ")
		.append(getIterableAsString(artist.getAliases(), TypeFormat.TAG)).append("\n");
		
		
		return builder.toString();
	}

	private DMPrintUtils(){}

}
