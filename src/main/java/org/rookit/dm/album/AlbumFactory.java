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

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;
import org.rookit.dm.artist.Artist;
import org.rookit.dm.artist.ArtistFactory;
import org.rookit.dm.utils.DataModelValidator;
import org.smof.annnotations.SmofBuilder;
import org.smof.annnotations.SmofParam;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;

/**
 * Class that provides methods for creating {@link Album} objects. This class implements
 * the factory pattern.
 *  
 * @author Joao
 *
 */
public class AlbumFactory implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final DataModelValidator VALIDATOR = DataModelValidator.getDefault();
	private static AlbumFactory factory;

	/**
	 * Returns the default album factory.
	 * 
	 * @return default album factory
	 */
	public static AlbumFactory getDefault(){
		if(factory == null){
			factory = new AlbumFactory();
		}
		return factory;
	}

	private TypeRelease defaultTypeRelease;

	private AlbumFactory(){
		defaultTypeRelease = TypeRelease.STUDIO;
	}

	/**
	 * Creates a new single artist album with the parameters provided.
	 *  
	 * @param title album title
	 * @param type release type
	 * @param artists artists responsible for album release
	 * @return a new album, created with the parameters passed.
	 */
	public Album createSingleArtistAlbum(String title, TypeRelease type, Set<Artist> artists) {
		VALIDATOR.checkArgumentStringNotEmpty(title, "A title must be specified");
		VALIDATOR.checkArgumentNotNull(type, "The type release cannot be null");
		VALIDATOR.checkArgumentNonEmptyCollection(artists, "The album must specify a non-empty set of values");
		return new SingleArtistAlbum(title, type, artists);
	}

	/**
	 * Creates a new single artist with the parameters provided. This method should
	 * be used to create a new album from tag data.
	 * 
	 * @param albumTag album tag, taken from {@link ID3v1#getAlbum()}
	 * @param albumArtistsTag album artists tag, taken from {@link ID3v2#getAlbumArtist()}
	 * @return a new album, created with the parameters passed.
	 */
	public Album createSingleArtistAlbum(String albumTag, String albumArtistsTag) {
		final ArtistFactory factory = ArtistFactory.getDefault();
		Album album = null;
		album = createSingleArtistAlbum(albumTag, factory.getArtistsFromTag(albumArtistsTag));
		return album;
	}

	/**
	 * Creates a new single artist with the parameters provided.
	 * 
	 * @param albumTitle title of the album.
	 * @param artists set of artists (album artists).
	 * @return a new album, created with the parameters passed.
	 */
	public Album createSingleArtistAlbum(String albumTitle, Set<Artist> artists) {
		final Pair<TypeRelease, String> release = TypeRelease.parseAlbumName(albumTitle, defaultTypeRelease);
		return createSingleArtistAlbum(release.getRight(), release.getLeft(), artists);
	}

	/**
	 * Creates a new album with the specified type. The album will then be set with the specified
	 * title, release type and artists.
	 * 
	 * If any of these last three parameters is irrelevant to the album (e.g. VA album has no main artists)
	 * the correspondent parameter can be set to null.
	 * 
	 * @param albumType album type.
	 * @param title album title.
	 * @param type release type.
	 * @param artists album artists (only the creators).
	 * @return a newly created Album object, with the parameters passed by parameter.
	 */
	@SmofBuilder
	public Album createAlbum(
			@SmofParam(name = TYPE) TypeAlbum albumType, 
			@SmofParam(name = TITLE) String title, 
			@SmofParam(name = RELEASE_TYPE) TypeRelease type, 
			@SmofParam(name = ARTISTS) Set<Artist> artists) {
		Album album = null;

		switch(albumType){
		case ARTIST:
			album = createSingleArtistAlbum(title, type, artists);
			break;
		case VA:
			album = createVAAlbum(title,type);
			break;
		}

		return album;
	}

	/**
	 * Creates a new various artists album.
	 * 
	 * @param title album title.
	 * @param type release type.
	 * @return a newly created {@link Album} object.
	 */
	public Album createVAAlbum(String title, TypeRelease type) {
		VALIDATOR.checkArgumentStringNotEmpty(title, "A title must be specified");
		VALIDATOR.checkArgumentNotNull(type, "The type release cannot be null");
		return new VariousArtistAlbum(title, type);
	}

	/**
	 * Returns the default type release, used when parsing a type release from a string.
	 * 
	 * @return the default type release
	 */
	public TypeRelease getDefaultTypeRelease() {
		return defaultTypeRelease;
	}

	/**
	 * Sets the default type release, returned when no type release can be parsed
	 * from an album title. The value set can be null, in which case no type release
	 * (e.g. null) will be returned in case of an unparseable album title.
	 * 
	 * @param defaultTypeRelease default type release.
	 */
	public void setDefaultTypeRelease(TypeRelease defaultTypeRelease) {
		VALIDATOR.checkArgumentNotNull(defaultTypeRelease, "The type release cannot be null");
		this.defaultTypeRelease = defaultTypeRelease;
	}

}
