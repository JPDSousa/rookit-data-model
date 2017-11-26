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

import static org.rookit.dm.album.DatabaseFields.*;

import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

import org.rookit.dm.artist.Artist;
import org.rookit.dm.genre.Genreable;
import org.rookit.dm.play.Playable;
import org.rookit.dm.track.Track;
import org.smof.annnotations.ForceInspection;
import org.smof.annnotations.SmofIndex;
import org.smof.annnotations.SmofIndexField;
import org.smof.annnotations.SmofIndexes;
import org.smof.gridfs.SmofGridRef;
import org.smof.index.IndexType;

/**
 * Represents an album of tracks.
 * 
 * An album keeps a list of tracks released through that album, where every track
 * has a number and a disc associated. No two tracks have the same disc and number,
 * and all tracks must have a disc and number associated.
 * <p>A disc is an abstraction of a subset of tracks, all belonging to the same compact
 * physical disc. A disc is unique in each album, thus there can be no two discs with
 * the same name on the same album. Although, is it perfectly normal to have two discs
 * with the same name. 
 * <p>In order to create a valid album, it must have a valid album, a valid release type and
 * a valid set of artists (see {@link AlbumFactory#createSingleArtistAlbum(String, TypeRelease, Set)} 
 * for further reference). 
 * This class has an associated factory {@link AlbumFactory}, which can provide
 * helpful methods to easily create functional instances of this interface.
 * 
 * @see AlbumFactory
 * @since 1
 *
 */
@SmofIndexes({
	@SmofIndex(fields={
			@SmofIndexField(name = TITLE, type = IndexType.ASCENDING),
			@SmofIndexField(name = RELEASE_TYPE, type = IndexType.DESCENDING),
			@SmofIndexField(name = ARTISTS, type = IndexType.ASCENDING)},
			unique = true),
	@SmofIndex(fields={@SmofIndexField(name = RELEASE_DATE, type = IndexType.DESCENDING)}),
	@SmofIndex(fields={@SmofIndexField(name = TITLE, type = IndexType.TEXT)})
})
@ForceInspection({SingleArtistAlbum.class, VariousArtistAlbum.class})
public interface Album extends Genreable, Playable, Comparable<Album>, AlbumSetter<Void> {

	/**
	 * Standard nomenclature for a disc.
	 */
	String CD = "CD";

	/**
	 * Standard date format, used when for parsing and formatting release dates
	 * related to albums
	 */
	String DATE_FORMAT = "dd-MM-yyyy";
	/**
	 * Alternative formats to the data format, commonly used on unparsed tracks
	 */
	String[] DATE_FORMAT_ALT = {"yyyy"};

	/**
	 * This value represents the name of the disc when the album has only
	 * one (nameless) disc.
	 */
	String DEFAULT_DISC = "1";

	/**
	 * Value for a track without number
	 */
	Integer NUMBERLESS = 0;

	/**
	 * Returns the full name of the album, which by default is {@code title (type_release)}.
	 * 
	 * @return a full descriptive name of the album
	 * @see TypeRelease#getFormattedName(String)
	 */
	String getFullTitle();

	/**
	 * Returns the title of the album.
	 * @return title of this album.
	 */
	String getTitle();

	/**
	 * Returns the artists that are authors of the album.
	 * <p>
	 * Do not confuse the set returned as a set of all artists involved
	 * in the album. In order to get such result, one should iterate over
	 * all tracks on the album and get the artists of each track.
	 * </p>
	 * <p>
	 * To add artists to an album only as authors, use the method {@link #addArtist(Artist)}
	 * </p>
	 * @return set of authors of this album
	 */
	Collection<Artist> getArtists();

	/**
	 * Returns the list of tracks with all the tracks from all
	 * the discs.
	 * 
	 * @return a list of tracks with all the tracks of the album
	 */
	Collection<Track> getTracks();

	/**
	 * Returns the set of tracks from the disc passed as parameter.
	 *  
	 * @param cd disc name
	 * @return the list of tracks from the disc passed as parameter
	 */
	Collection<Track> getTracks(String cd);
	
	String getTrackDisc(Track track);

	Collection<Integer> getTrackNumbers(String cd);
	
	Track getTrack(String discName, Integer number);

	/**
	 * Returns the number of tracks in the entire album. This method will
	 * return the sum of the number of track on each of the discs of this 
	 * album.
	 * <p>In order to get the number of tracks from a specific disc use {@link #getTracksCount(String)}
	 * 
	 * @return number of tracks in the entire album.
	 */
	int getTracksCount();
	/**
	 * Returns the number of tracks in a specific disc passed as parameter.
	 * If the disc does not exist on the album, an exception {@link IllegalArgumentException}
	 * will be thrown.
	 * 
	 * @param cd name of the disc
	 * @return number of tracks on the disc passed as parameter
	 */
	int getTracksCount(String cd);

	/**
	 * Returns the date of the release of this album.
	 * 
	 * @return release date of the album.
	 */
	LocalDate getReleaseDate();

	/**
	 * Returns the total duration of the disc in seconds. The length
	 * of the disc should be the sum of the length of all the tracks
	 * in the disc.
	 * <p>If the disc passed as parameter does not exist in the album,
	 * {@link IllegalArgumentException} will the thrown.
	 * @param cd name of the disc
	 * @return the total length of the disc in seconds.
	 */
	Duration getDuration(String cd);

	/**
	 * Return a set of discs with the discs on the album.
	 * @return a set of the album discs.
	 */
	Collection<String> getDiscs();
	/**
	 * Return the number of discs in the album.
	 * @return number of discs in the album.
	 */
	int getDiscCount();

	/**
	 * Returns the type of release of the album.
	 * 
	 * @return album's type release
	 */
	TypeRelease getReleaseType();

	/**
	 * Returns the cover art of the album as a byte array.
	 * 
	 * <p>Use {@link Files#write(java.nio.file.Path, byte[], java.nio.file.OpenOption...)} in order
	 * to write the returned bytes to a file (ex: jpeg).
	 * 
	 * @return a byte array representative of the cover image.
	 */
	SmofGridRef getCover();

	/**
	 * Searches for the track passed as parameter in the album's tracks.
	 * 
	 * @param track  track to search
	 * @return <code>true</code> if the track exists in the album and <code>false</code>
	 * otherwise.
	 */
	boolean contains(Track track);
	
	/**
	 * Searches for the track number passed as parameter in the disc (also passed as parameter).
	 * 
	 * @param disc disc in which the track number will be searched. 
	 * @param track track number to search
	 * @return <code>true</code> if both the disc and the track exist and <code>false</code> if
	 * one of the elements does not exist.
	 */
	boolean contains(String disc, Integer track);

	/**
	 * Returns the album type, as a {@link TypeAlbum} enumeration.
	 * 
	 * @return album type
	 */
	TypeAlbum getAlbumType();

	Integer getTrackNumber(Track track);
	
	@Override
	boolean equals(Object obj);
	
	@Override
	int hashCode();
}
