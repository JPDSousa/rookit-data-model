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
package utils;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.collections4.IterableUtils;
import org.extendedStringBuilder.ExtStringBuilder;

import album.Album;
import artist.Artist;
import print.TypeFormat;
import track.Track;
import track.VersionTrack;

@SuppressWarnings("javadoc")
public final class PrintUtils {

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
		final Track track = album.getTrack(discName, number);
		builder.append(number).tab()
		.append(track.getTitle()).breakLine()
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
			for(Artist artist : track.getAsVersionTrack().getVersionArtists()){
				builder.tab(2).append(artist.toString()).breakLine();
			}
		}
	}

	public static String track(Track track){
		final ExtStringBuilder builder = ExtStringBuilder.create();

		builder.append("Content: ").append(track.getPath().getId()).breakLine()
		.append("Type: ").append(track.getType()).breakLine()
		.append("Id: ").append(track.getIdAsString()).breakLine()
		.append("Title: ").append(track.getTitle().getTitle()).breakLine()
		.append("Main Artists: ").append(getIterableAsString(track.getMainArtists(), TypeFormat.TAG)).breakLine()
		.append("Features: ").append(getIterableAsString(track.getFeatures(), TypeFormat.TAG)).breakLine()
		.append("Producers: ").append(getIterableAsString(track.getProducers(), TypeFormat.TAG)).breakLine()
		.append("Genres: ").append(getIterableAsString(track.getGenres(), TypeFormat.TAG)).breakLine()
		.append("HiddenTrack: ").append(track.getHiddenTrack()).breakLine()
		.append("Plays: ").append(track.getPlays()).breakLine();
		if(track.isVersionTrack()) {
			final VersionTrack versionTrack = track.getAsVersionTrack();
			builder.append("\nVersion Type: ").append(versionTrack.getVersionType())
			.append("\nVersion Artists: ").append(getIterableAsString(versionTrack.getVersionArtists(), TypeFormat.TAG));
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

	public static <T> String getIterableAsString(final Iterable<T> elements, final String separator, final String emptyMessage) {
		if(IterableUtils.isEmpty(elements)){
			return emptyMessage;
		}
		return StreamSupport.stream(elements.spliterator(), false).map(Object::toString).collect(Collectors.joining(separator));
	}

	public static <T> String getIterableAsString(final Iterable<T> elements, final TypeFormat format, final String emptyMessage){
		return getIterableAsString(elements, format.getSeparator(), emptyMessage);
	}

	public static <T> String getIterableAsString(final Iterable<T> elements, final char separator, final String emptyMessage){
		return getIterableAsString(elements, Character.toString(separator), emptyMessage);
	}

	public static <T> String getIterableAsString(final Iterable<T> elements, final String separator) {
		return StreamSupport.stream(elements.spliterator(), false).map(Object::toString).collect(Collectors.joining(separator));
	}

	public static <T> String getIterableAsString(final Iterable<T> elements, final TypeFormat format){
		return getIterableAsString(elements, format.getSeparator());
	}

	public static <T> String getIterableAsString(final Iterable<T> elements, final char separator){
		return getIterableAsString(elements, Character.toString(separator));
	}

	private PrintUtils(){}

}
