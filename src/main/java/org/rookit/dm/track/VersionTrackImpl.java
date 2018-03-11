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
package org.rookit.dm.track;

import static org.rookit.api.dm.track.TrackFields.ORIGINAL;
import static org.rookit.api.dm.track.TrackFields.VERSION_ARTISTS;
import static org.rookit.api.dm.track.TrackFields.VERSION_TOKEN;
import static org.rookit.api.dm.track.TrackFields.VERSION_TYPE;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Generated;

import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.track.Track;
import org.rookit.api.dm.track.TrackTitle;
import org.rookit.api.dm.track.TypeTrack;
import org.rookit.api.dm.track.TypeVersion;
import org.rookit.api.dm.track.VersionTrack;
import org.rookit.utils.VoidUtils;
import org.rookit.utils.print.PrintUtils;
import org.rookit.utils.print.TypeFormat;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Sets;

@Entity("Track")
@SuppressWarnings("javadoc")
public final class VersionTrackImpl extends AbstractTrack implements VersionTrack {

	private static final String NO_VERSION_TOKEN = StringUtils.EMPTY;
	
	@Reference(value = VERSION_ARTISTS, idOnly = true)
	private final Set<Artist> versionArtists;

	@Property(VERSION_TOKEN)
	private String versionToken;//e.g. club remix

	@Reference(value = ORIGINAL, idOnly = true)
	private final Track original;

	@Property(value = VERSION_TYPE)
	private final TypeVersion versionType;

	@SuppressWarnings("unused")
	@Deprecated
	private VersionTrackImpl() {
		this(null, null, null);
	}
	
	VersionTrackImpl(final Track original, final TypeVersion versionType, final BiStream path) {
		super(TypeTrack.VERSION, path);
		this.versionArtists = Collections.synchronizedSet(Sets.newLinkedHashSet());
		this.original = original;
		this.versionType = versionType;
		resetVersionToken();
	}
	
	@Override
	public Track getOriginal() {
		return this.original;
	}

	@Override
	public TypeVersion getVersionType() {
		return this.versionType;
	}

	@Override
	public Collection<Artist> getVersionArtists() {
		return Collections.unmodifiableCollection(this.versionArtists);
	}

	@Override
	public Void addVersionArtist(final Artist extraArtist){
		VALIDATOR.checkArgument().isNotNull(extraArtist, "versionArtist");
		this.versionArtists.add(extraArtist);
		return VoidUtils.returnVoid();
	}

	@Override
	public Void setVersionArtists(final Set<Artist> artists) {
		VALIDATOR.checkArgument().isNotNull(artists, "versionArtists");
		this.versionArtists.clear();
		this.versionArtists.addAll(artists);
		return VoidUtils.returnVoid();
	}

	@Override
	public Void setVersionToken(final String versionToken){
		VALIDATOR.checkArgument().isNotNull(versionToken, "versionToken");
		this.versionToken = versionToken;
		return VoidUtils.returnVoid();
	}

	@Override
	public Optional<String> getVersionToken(){
		return Optional.ofNullable(this.versionToken)
				.filter(token -> token != NO_VERSION_TOKEN);
	}

	@Override
	public TrackTitle getFullTitle() {
		return this.original.getFullTitle().appendExtras(getExtras());
	}

	private String getExtras() {
		final StringBuilder builder = new StringBuilder(PrintUtils.getIterableAsString(
				this.versionArtists, 
				TypeFormat.TITLE, 
				Artist.UNKNOWN_ARTISTS));
		builder.append(" ").append(getVersionType().getName());
		return builder.toString();
	}

	@Override
	public Optional<VersionTrack> getAsVersionTrack() {
		return Optional.of(this);
	}

	@Override
	public boolean isVersionTrack() {
		return true;
	}

	@Override
	public TrackTitle getLongFullTitle() {
		return this.original.getLongFullTitle().appendExtras(getExtras());
	}

	@Override
	public TrackTitle getTitle() {
		return this.original.getTitle();
	}

	@Override
	public Collection<Artist> getMainArtists() {
		return this.original.getMainArtists();
	}

	@Override
	public Void setMainArtists(final Collection<Artist> artists) {
		return this.original.setMainArtists(artists);
	}

	@Override
	public Void addMainArtist(final Artist artist) {
		return this.original.addMainArtist(artist);
	}

	@Override
	public Void setTitle(final String title) {
		return this.original.setTitle(title);
	}

	@Override
	public Void setTitle(final TrackTitle title) {
		return this.original.setTitle(title);
	}

	@Override
	@Generated(value = "GuavaEclipsePlugin")
	public int hashCode() {
		return Objects.hash(super.hashCode(), versionArtists, versionToken, original, versionType);
	}

	@Override
	@Generated(value = "GuavaEclipsePlugin")
	public boolean equals(Object object) {
		if (object instanceof VersionTrackImpl) {
			if (!super.equals(object))
				return false;
			VersionTrackImpl that = (VersionTrackImpl) object;
			return Objects.equals(this.versionArtists, that.versionArtists)
					&& Objects.equals(this.versionToken, that.versionToken)
					&& Objects.equals(this.original, that.original)
					&& Objects.equals(this.versionType, that.versionType);
		}
		return false;
	}

	@Override
	@Generated(value = "GuavaEclipsePlugin")
	public String toString() {
		return MoreObjects.toStringHelper(this).add("super", super.toString()).add("versionArtists", versionArtists)
				.add("versionToken", versionToken).add("original", original).add("versionType", versionType).toString();
	}

	@Override
	public Void addMainArtists(final Collection<Artist> artists) {
		return this.original.addMainArtists(artists);
	}

	@Override
	public Void removeMainArtist(final Artist artist) {
		return this.original.removeMainArtist(artist);
	}

	@Override
	public Void removeMainArtists(final Collection<Artist> artists) {
		return this.original.removeMainArtists(artists);
	}

	@Override
	public Void addVersionArtists(final Collection<Artist> extraArtists) {
		VALIDATOR.checkArgument().isNotNull(extraArtists, "extraArtists");
		this.versionArtists.addAll(extraArtists);
		return VoidUtils.returnVoid();
	}

	@Override
	public Void removeVersionArtist(final Artist artist) {
		VALIDATOR.checkArgument().isNotNull(artist, "artist");
		this.versionArtists.remove(artist);
		return VoidUtils.returnVoid();
	}

	@Override
	public Void removeVersionArtists(final Collection<Artist> artists) {
		VALIDATOR.checkArgument().isNotNull(artists, "artists");
		this.versionArtists.removeAll(artists);
		return VoidUtils.returnVoid();
	}

	@Override
	public Void clearVersionArtists() {
		this.versionArtists.clear();
		return VoidUtils.returnVoid();
	}

	@Override
	public Void resetVersionToken() {
		this.versionToken = NO_VERSION_TOKEN;
		return VoidUtils.returnVoid();
	}

	

}
