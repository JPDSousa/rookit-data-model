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
import java.util.Collections;
import java.util.Set;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Reference;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.artist.TypeArtist;
import org.rookit.api.dm.play.StaticPlaylist;
import org.rookit.api.storage.DBManager;
import org.rookit.api.storage.queries.TrackQuery;
import org.rookit.dm.genre.AbstractGenreable;
import org.rookit.dm.utils.DataModelValidator;
import org.rookit.utils.VoidUtils;

import com.google.common.collect.Sets;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.Generated;

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
	private final Set<String> aliases;
	
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
	protected AbstractArtist(final TypeArtist type, final String artistName, final BiStream picture) {
		this.name = artistName;
		this.related = Collections.synchronizedSet(Sets.newLinkedHashSet());
		this.aliases = Collections.synchronizedSet(Sets.newLinkedHashSetWithExpectedSize(5));
		this.type = type;
		this.picture = picture;
	}

	@Override
	public final TypeArtist getType() {
		return this.type;
	}
	
	@Override
	public final String getName() {
		return this.name;
	}

	@Override
	public Collection<Artist> getRelatedArtists() {
		return Collections.unmodifiableCollection(this.related);
	}

	@Override
	public Void addRelatedArtist(final Artist artist) {
		VALIDATOR.checkArgument().isNotNull(artist, "related artist");
		this.related.add(artist);
		return VoidUtils.returnVoid();
	}

	@Override
	public Optional<String> getOrigin() {
		return Optional.ofNullable(this.origin);
	}

	@Override
	public Void setOrigin(final String origin) {
		VALIDATOR.checkArgument().isNotEmpty(origin, "origin");
		this.origin = origin;
		return VoidUtils.returnVoid();
	}

	@Override
	@Generated(value = "GuavaEclipsePlugin")
	public int hashCode() {
		return Objects.hash(super.hashCode(), name, isni, type);
	}

	@Override
	@Generated(value = "GuavaEclipsePlugin")
	public boolean equals(Object object) {
		if (object instanceof AbstractArtist) {
			if (!super.equals(object))
				return false;
			AbstractArtist that = (AbstractArtist) object;
			return Objects.equals(this.name, that.name) && Objects.equals(this.isni, that.isni)
					&& Objects.equals(this.type, that.type);
		}
		return false;
	}

	@Override
	public String toString() {
		return getName();
	}

	@Override
	public Collection<String> getAliases() {
		return Collections.unmodifiableCollection(this.aliases);
	}

	@Override
	public Void addAlias(String alias) {
		VALIDATOR.checkArgument().isNotEmpty(alias, "alias");
		this.aliases.add(alias);
		return VoidUtils.returnVoid();
	}

	@Override
	public Void setAliases(final Collection<String> aliases) {
		VALIDATOR.checkArgument().isNotNull(aliases, "aliases");
		clearAliases();
		addAliases(aliases);
		return VoidUtils.returnVoid();
	}

	@Override
	public Optional<LocalDate> getBeginDate() {
		return Optional.ofNullable(beginDate);
	}

	@Override
	public Void setBeginDate(final LocalDate beginDate) {
		VALIDATOR.checkArgument().isNotNull(beginDate, "beginDate");
		this.beginDate = beginDate;
		return VoidUtils.returnVoid();
	}

	@Override
	public Optional<LocalDate> getEndDate() {
		return Optional.ofNullable(this.endDate);
	}

	@Override
	public Void setEndDate(final LocalDate endDate) {
		VALIDATOR.checkArgument().isNotNull(endDate, "endDate");
		this.endDate = endDate;
		return VoidUtils.returnVoid();
	}

	@Override
	public Optional<String> getIPI() {
		return Optional.ofNullable(this.ipi);
	}

	@Override
	public Void setIPI(final String ipi) {
		VALIDATOR.checkArgument().isNotNull(ipi, "ipi");
		this.ipi = ipi;
		return VoidUtils.returnVoid();
	}

	@Override
	public Optional<String> getISNI() {
		return Optional.ofNullable(this.isni);
	}

	@Override
	public Void setISNI(final String isni) {
		VALIDATOR.checkArgument().isNotNull(isni, "isni");
		this.isni = isni;
		return VoidUtils.returnVoid();
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
	public Void setPicture(final byte[] picture) {
		try {
			this.picture.toOutput().write(picture);
			return VoidUtils.returnVoid();
		} catch (IOException e) {
			return VALIDATOR.handleIOException(e);
		}
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
		
		query.stream().limit(limit).forEach(playlist::addTrack);
		return playlist;
	}

	@Override
	public Void addAliases(final Collection<String> aliases) {
		VALIDATOR.checkArgument().isNotNull(aliases, "aliases");
		this.aliases.addAll(aliases);
		return VoidUtils.returnVoid();
	}

	@Override
	public Void removeAlias(final String alias) {
		VALIDATOR.checkArgument().isNotEmpty(alias, "alias");
		this.aliases.remove(alias);
		return VoidUtils.returnVoid();
	}

	@Override
	public Void removeAliases(final Collection<String> aliases) {
		VALIDATOR.checkArgument().isNotNull(aliases, "aliases");
		this.aliases.removeAll(aliases);
		return VoidUtils.returnVoid();
	}

	@Override
	public Void clearAliases() {
		this.aliases.clear();
		return VoidUtils.returnVoid();
	}
	
}
