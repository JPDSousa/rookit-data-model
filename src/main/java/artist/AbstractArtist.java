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
package artist;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.smof.annnotations.SmofArray;
import org.smof.annnotations.SmofString;
import org.smof.element.AbstractElement;
import org.smof.parsers.SmofType;

import album.Album;
import genre.Genre;
import track.Track;
import utils.CoreValidator;

import static artist.DatabaseFields.*;

/**
 * Abstract implementation of the {@link Artist} interface. Extend this class
 * in order to create a custom artist type.
 */
public abstract class AbstractArtist extends AbstractElement implements ExtendedArtist {

	protected static final CoreValidator VALIDATOR = CoreValidator.getDefault();
	
	/**
	 * Artist Name
	 */
	@SmofString(name = NAME)
	private final String artistName;

	/**
	 * Set of Related artists
	 */
	@SmofArray(name = RELATED, type = SmofType.OBJECT)
	private final Set<Artist> related;
	/**
	 * Albums in which this artist this marked as author
	 */
	private final Set<Album> albuns;
	/**
	 * Set of genres that describe this artists (not necessarily
	 * the ones associated to the artist's albums or tracks)
	 */
	@SmofArray(name = GENRES, type = SmofType.OBJECT)
	private Set<Genre> genres;

	/**
	 * Artist origin (location)
	 */
	@SmofString(name = ORIGIN)
	private String origin;
	
	@SmofArray(name = ALIASES, type = SmofType.STRING)
	private Set<String> aliases;
		
	/**
	 * Abstract constructor. Use this constructor to
	 * Initialize all the class fields.
	 * 
	 * @param artistName artist name
	 */
	protected AbstractArtist(String artistName) {
		VALIDATOR.checkArgumentStringNotEmpty(artistName, "Must specify an artist name");
		this.artistName = artistName;
		this.related = new LinkedHashSet<>();
		this.albuns = new LinkedHashSet<>();
		this.genres = new LinkedHashSet<>();
		this.origin = "";
		this.aliases = new LinkedHashSet<>();
	}

	@Override
	public final String getName() {
		return artistName;
	}

	@Override
	public Iterable<Artist> getRelatedArtists() {
		return related;
	}

	@Override
	public void addRelatedArtist(Artist artist) {
		VALIDATOR.checkArgumentNotNull(artist, "The related artist cannot be null");
		related.add(artist);
	}

	@Override
	public Iterable<Album> getAlbuns() {
		return albuns;
	}

	@Override
	public Iterable<Track> getTracks() {
		return albuns.stream().flatMap(a -> StreamSupport.stream(a.getTracks().spliterator(), false)).collect(Collectors.toSet());
	}

	@Override
	public String getOrigin() {
		return origin;
	}

	@Override
	public void setOrigin(String origin) {
		VALIDATOR.checkArgumentStringNotEmpty(origin, "Must specify an origin");
		this.origin = origin;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((artistName == null) ? 0 : artistName.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		final Artist other = (Artist) obj;
		if (artistName == null) {
			if (other.getName() != null)
				return false;
		} else if (!artistName.equalsIgnoreCase(other.getName()))
			return false;

		return true;
	}

	@Override
	public Iterable<Genre> getGenres() {
		return genres;
	}

	@Override
	public void addGenre(Genre genre) {
		VALIDATOR.checkArgumentNotNull(genre, "Cannot add a null genre");
		genres.add(genre);
	}

	@Override
	public void setGenres(Set<Genre> genres) {
		VALIDATOR.checkArgumentNotNull(genres, "Cannot set a null set of genres");
		this.genres = genres;
	}

	@Override
	public Iterable<Genre> getAllGenres() {
		final Set<Genre> genres = new LinkedHashSet<>();
		genres.addAll(this.genres);

		albuns.forEach(a -> a.getAllGenres().forEach(g -> genres.add(g)));

		return genres;
	}

	@Override
	public String toString() {
		return getName();
	}

	@Override
	public Iterable<String> getAliases() {
		return aliases;
	}

	@Override
	public void addAlias(String alias) {
		VALIDATOR.checkArgumentStringNotEmpty(alias, "Must specify an alias");
		this.aliases.add(alias);
	}

	@Override
	public void setAliases(Set<String> aliases) {
		VALIDATOR.checkArgumentNotNull(aliases, "Cannot set a null set of aliases.");
		this.aliases = aliases;
	}

}
