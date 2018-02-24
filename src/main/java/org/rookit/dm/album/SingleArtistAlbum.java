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

import java.util.Collections;
import java.util.Set;

import org.mongodb.morphia.annotations.Entity;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.album.TypeAlbum;
import org.rookit.api.dm.album.TypeRelease;
import org.rookit.api.dm.artist.Artist;

/**
 * This class represents a single artist album, which represents an album
 * that has a fixed and declared number of authors (e.g. non VA-albums).
 * 
 * @author Joao
 *
 */
// TODO is it possible to turn this into Album.class.getName() somehow??
@Entity(value="Album")
class SingleArtistAlbum extends AbstractAlbum {
	
	@SuppressWarnings("unused")
	@Deprecated
	private SingleArtistAlbum() {
		this(null, null, Collections.emptySet(), null);
	}
	
	/**
	 * The constructor has a package-view in order to forbid object creation through
	 * objects on other packages. In order to create a new object of this type, use the
	 * dedicated methods on the {@link AlbumFactoryImpl} class.
	 * 
	 * <p><b>Attention:</b> all the arguments must be previously validated through the {@link AlbumValidator}
	 * class. Non validated parameters are allowed, but it is highly inadvisable.
	 * 
	 * @param name name of the album
	 * @param type type of release
	 * @param artists album authors
	 */
	SingleArtistAlbum(
			final String name, 
			final TypeRelease type, 
			final Set<Artist> artists,
			final BiStream stream) {
		super(TypeAlbum.ARTIST, name, type, artists, stream);
	}

	@Override
	public String toString() {
		return getFullTitle();
	}
}
