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
package org.rookit.dm.artist;

import static org.rookit.dm.artist.DatabaseFields.*;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.rookit.dm.album.Album;
import org.rookit.dm.genre.AbstractGenreable;
import org.rookit.dm.genre.Genre;
import org.rookit.dm.track.Track;
import org.rookit.dm.utils.DataModelValidator;
import org.smof.annnotations.SmofArray;
import org.smof.annnotations.SmofDate;
import org.smof.annnotations.SmofObject;
import org.smof.annnotations.SmofString;
import org.smof.gridfs.SmofGridRef;
import org.smof.gridfs.SmofGridRefFactory;
import org.smof.parsers.SmofType;

import com.google.common.collect.Sets;

/**
 * Abstract implementation of the {@link Artist} interface. Extend this class
 * in order to create a custom artist type.
 */
public abstract class AbstractArtist extends AbstractGenreable implements ExtendedArtist {

	protected static final DataModelValidator VALIDATOR = DataModelValidator.getDefault();
	
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
	 * Artist origin (location)
	 */
	@SmofString(name = ORIGIN)
	private String origin;
	
	@SmofArray(name = ALIASES, type = SmofType.STRING)
	private Set<String> aliases;
	
	@SmofDate(name = BEGIN_DATE)
	private LocalDate beginDate;
	
	@SmofDate(name = END_DATE)
	private LocalDate endDate;
	
	@SmofString(name = IPI)
	private String ipi;
	
	@SmofString(name = ISNI)
	private String isni;
	
	@SmofString(name = TYPE)
	private final TypeArtist type;
	
	@SmofObject(name = PICTURE, bucketName = PICTURE_BUCKET, preInsert = false)
	private final SmofGridRef picture;
		
	/**
	 * Abstract constructor. Use this constructor to
	 * Initialize all the class fields.
	 * 
	 * @param artistName artist name
	 */
	protected AbstractArtist(TypeArtist type, String artistName) {
		VALIDATOR.checkArgumentStringNotEmpty(artistName, "Must specify an artist name");
		this.artistName = artistName;
		this.related = Sets.newLinkedHashSet();
		this.albuns = Sets.newLinkedHashSet();
		this.origin = "";
		this.aliases = Sets.newLinkedHashSetWithExpectedSize(5);
		this.type = type;
		this.isni = "";
		this.ipi = "";
		this.picture = SmofGridRefFactory.newEmptyRef();
	}

	@Override
	public final TypeArtist getType() {
		return type;
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
	public Void addRelatedArtist(Artist artist) {
		VALIDATOR.checkArgumentNotNull(artist, "The related artist cannot be null");
		related.add(artist);
		return null;
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
	public Void setOrigin(String origin) {
		VALIDATOR.checkArgumentStringNotEmpty(origin, "Must specify an origin");
		this.origin = origin;
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + artistName.hashCode();
		result = prime * result + isni.hashCode();
		result = prime * result + type.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		AbstractArtist other = (AbstractArtist) obj;
		if (!artistName.equalsIgnoreCase(other.artistName)) {
			return false;
		}
		if (!isni.equals(other.isni)) {
			return false;
		}
		if (type != other.type) {
			return false;
		}
		return true;
	}

	@Override
	public Collection<Genre> getAllGenres() {
		final Set<Genre> genres = new LinkedHashSet<>();
		getGenres().forEach(genres::add);

		albuns.forEach(a -> a.getAllGenres().forEach(g -> genres.add(g)));

		return genres;
	}

	@Override
	public String toString() {
		return getName();
	}

	@Override
	public Collection<String> getAliases() {
		return aliases;
	}

	@Override
	public Void addAlias(String alias) {
		VALIDATOR.checkArgumentStringNotEmpty(alias, "Must specify an alias");
		this.aliases.add(alias);
		return null;
	}

	@Override
	public Void setAliases(Set<String> aliases) {
		VALIDATOR.checkArgumentNotNull(aliases, "Cannot set a null set of aliases.");
		this.aliases = aliases;
		return null;
	}

	@Override
	public LocalDate getBeginDate() {
		return beginDate;
	}

	@Override
	public Void setBeginDate(LocalDate beginDate) {
		this.beginDate = beginDate;
		return null;
	}

	@Override
	public LocalDate getEndDate() {
		return endDate;
	}

	@Override
	public Void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
		return null;
	}

	@Override
	public String getIPI() {
		return ipi;
	}

	@Override
	public Void setIPI(String ipi) {
		VALIDATOR.checkArgumentNotNull(ipi, "IPI cannot be null");
		this.ipi = ipi;
		return null;
	}

	@Override
	public String getISNI() {
		return isni;
	}

	@Override
	public Void setISNI(String isni) {
		VALIDATOR.checkArgumentNotNull(isni, "ISNI cannot be null");
		this.isni = isni;
		return null;
	}

	@Override
	public int compareTo(Artist o) {
		final int name = getName().compareTo(o.getName());
		return name == 0 ? getIdAsString().compareTo(o.getIdAsString()) : name;
	}

	@Override
	public SmofGridRef getPicture() {
		return picture;
	}

	@Override
	public Void setPicture(byte[] picture) {
		this.picture.attachByteArray(new ByteArrayInputStream(picture));
		return null;
	}
	
}
