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

import static org.rookit.api.dm.album.AlbumFields.*;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.album.Album;
import org.rookit.api.dm.album.AlbumFactory;
import org.rookit.api.dm.album.TypeAlbum;
import org.rookit.api.dm.album.TypeRelease;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.artist.factory.ArtistFactory;
import org.rookit.api.dm.factory.RookitFactory;
import org.rookit.dm.album.factory.AlbumBiStream;
import org.rookit.dm.factory.AbstractRookitFactory;

import com.google.inject.Inject;

/**
 * Class that provides methods for creating {@link Album} objects. This class implements
 * the factory pattern.
 *  
 * @author Joao Sousa (jpd.sousa@campus.fct.unl.pt)
 *
 */
public class AlbumFactoryImpl extends AbstractRookitFactory<Album> implements AlbumFactory {

	private final ArtistFactory artistFactory; 
	private TypeRelease defaultTypeRelease;

	@Inject
	AlbumFactoryImpl(
			@AlbumBiStream final RookitFactory<BiStream> biStreamFactory, 
			final ArtistFactory artistFactory){
		super(Optional.ofNullable(biStreamFactory));
		this.artistFactory = artistFactory;
		defaultTypeRelease = TypeRelease.STUDIO;
	}

	@Override
	public Album createSingleArtistAlbum(String title, TypeRelease type, Set<Artist> artists) {
		VALIDATOR.checkArgumentStringNotEmpty(title, "A title must be specified");
		VALIDATOR.checkArgumentNotNull(type, "The type release cannot be null");
		VALIDATOR.checkArgumentNonEmptyCollection(artists, "The album must specify a non-empty set of values");
		final BiStream biStream = createEmptyBiStream();
		return new SingleArtistAlbum(title, type, artists, biStream);
	}

	@Override
	public Album createSingleArtistAlbum(String albumTag, String albumArtistsTag) {
		Album album = null;
		album = createSingleArtistAlbum(albumTag, artistFactory.getArtistsFromTag(albumArtistsTag));
		return album;
	}

	@Override
	public Album createSingleArtistAlbum(String albumTitle, Set<Artist> artists) {
		final Pair<TypeRelease, String> release = TypeRelease.parseAlbumName(albumTitle, defaultTypeRelease);
		return createSingleArtistAlbum(release.getRight(), release.getLeft(), artists);
	}

	@Override
	public Album createAlbum(TypeAlbum albumType, String title, TypeRelease type, Set<Artist> artists) {
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

	@Override
	public Album createVAAlbum(String title, TypeRelease type) {
		VALIDATOR.checkArgumentStringNotEmpty(title, "A title must be specified");
		VALIDATOR.checkArgumentNotNull(type, "The type release cannot be null");
		final BiStream biStream = createEmptyBiStream();
		return new VariousArtistAlbum(title, type, biStream);
	}

	@Override
	public Album createEmpty() {
		VALIDATOR.invalidOperation("Cannot create an empty album");
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Album create(Map<String, Object> data) {
		final Object albumType = data.get(TYPE);
		final Object title = data.get(TITLE);
		final Object type = data.get(RELEASE_TYPE);
		final Object artists = data.get(ARTISTS);

		if(albumType != null && albumType instanceof TypeAlbum
				&& title != null && title instanceof String
				&& type != null && type instanceof TypeRelease
				&& artists != null && artists instanceof Set) {
			return createAlbum((TypeAlbum) albumType, (String) title, 
					(TypeRelease) type, (Set<Artist>) artists);
		}
		VALIDATOR.runtimeException("Invalid arguments: " + data);
		return null;
	}

}
