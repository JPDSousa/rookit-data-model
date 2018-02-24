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

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Reference;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.artist.TypeArtist;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.play.StaticPlaylist;
import org.rookit.api.storage.DBManager;
import org.rookit.api.storage.queries.TrackQuery;
import org.rookit.dm.genre.AbstractGenreable;
import org.rookit.dm.utils.DataModelValidator;

import com.google.common.collect.Sets;

/**
 * Abstract implementation of the {@link Artist} interface. Extend this class
 * in order to create a custom artist type.
 */
public abstract class AbstractArtist extends AbstractGenreable implements Artist {

	protected static final DataModelValidator VALIDATOR = DataModelValidator.getDefault();
	
	/**
	 * Artist Name
	 */
	private final String name;

	/**
	 * Set of Related artists
	 */
	@Reference(idOnly = true)
	private final Set<Artist> related;

	/**
	 * Artist origin (location)
	 */
	private String origin;
	
	@Embedded
	private Set<String> aliases;
	
	private LocalDate beginDate;
	
	private LocalDate endDate;
	
	private String ipi;
	
	private String isni;
	
	private final TypeArtist type;
	
	@Embedded
	private final BiStream picture;
		
	/**
	 * Abstract constructor. Use this constructor to
	 * Initialize all the class fields.
	 * 
	 * @param artistName artist name
	 */
	protected AbstractArtist(TypeArtist type, String artistName, BiStream picture) {
		this.name = artistName;
		this.related = Sets.newLinkedHashSet();
		this.origin = "";
		this.aliases = Sets.newLinkedHashSetWithExpectedSize(5);
		this.type = type;
		this.isni = "";
		this.ipi = "";
		this.picture = picture;
	}

	@Override
	public final TypeArtist getType() {
		return type;
	}
	
	@Override
	public final String getName() {
		return name;
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
		result = prime * result + name.hashCode();
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
		if (!name.equalsIgnoreCase(other.name)) {
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
		return getGenres();
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
	public BiStream getPicture() {
		return picture;
	}

	@Override
	public Void setPicture(byte[] picture) {
		try {
			this.picture.toOutput().write(picture);
		} catch (IOException e) {
			VALIDATOR.handleIOException(e);
		}
		return null;
	}

	@Override
	public StaticPlaylist freeze(final DBManager db, final int limit) {
		final StaticPlaylist playlist = db.getFactories()
				.getPlaylistFactory()
				.createStaticPlaylist(getName());
		final TrackQuery query = db.getTracks();
		query.withMainArtist(this);
		
		// TODO search for tracks with this artist as feature, producer, etc..
		// TODO order by mainArtist > feature > producer > (etc..)
		
		query.stream().limit(limit).forEach(playlist::add);
		return playlist;
	}
	
}
