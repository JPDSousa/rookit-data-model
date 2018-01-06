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

import java.util.Collection;
import java.util.Set;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;
import org.rookit.dm.artist.Artist;
import org.rookit.dm.utils.PrintUtils;
import org.rookit.utils.print.TypeFormat;

import com.google.common.collect.Sets;

@Entity("Track")
@SuppressWarnings("javadoc")
public final class VersionTrack extends AbstractTrack {

	@Reference(idOnly = true)
	private final Set<Artist> versionArtists;

	private String versionToken;//e.g. club remix

	@Reference(idOnly = true)
	private final Track original;

	private final TypeVersion versionType;
	
	private VersionTrack() {
		super(TypeTrack.VERSION);
		this.versionArtists = null;
		this.original = null;
		this.versionType = null;
	}

	VersionTrack(Track original, TypeVersion versionType) {
		super(TypeTrack.VERSION);
		versionArtists = Sets.newLinkedHashSet();
		this.original = original;
		this.versionType = versionType;
		setVersionToken("");
	}
	
	public Track getOriginal() {
		return original;
	}

	public TypeVersion getVersionType() {
		return versionType;
	}

	public Collection<Artist> getVersionArtists() {
		return versionArtists;
	}

	public void addVersionArtist(Artist extraArtist){
		VALIDATOR.checkArgumentNotNull(extraArtist, "Cannot add a null artist");
		versionArtists.add(extraArtist);
	}

	public void setVersionArtists(Set<Artist> artists) {
		VALIDATOR.checkArgumentNotNull(artists, "Artist set cannot be null");
		versionArtists.clear();
		versionArtists.addAll(artists);
	}

	public void setVersionToken(String versionToken){
		VALIDATOR.checkArgumentNotNull(versionToken, "The version token cannot be null");
		this.versionToken = versionToken;
	}

	public String getVersionToken(){
		return versionToken;
	}

	@Override
	public TrackTitle getFullTitle() {
		return original.getFullTitle().appendExtras(getExtras());
	}

	private String getExtras() {
		final StringBuilder builder = new StringBuilder(PrintUtils.getIterableAsString(versionArtists, TypeFormat.TITLE, Artist.UNKNOWN_ARTISTS));
		builder.append(" ").append(getVersionType().getName());
		return builder.toString();
	}

	@Override
	public VersionTrack getAsVersionTrack() {
		return this;
	}

	@Override
	public boolean isVersionTrack() {
		return true;
	}

	@Override
	public TrackTitle getLongFullTitle() {
		return original.getLongFullTitle().appendExtras(getExtras());
	}

	@Override
	public TrackTitle getTitle() {
		return original.getTitle();
	}

	@Override
	public Collection<Artist> getMainArtists() {
		return original.getMainArtists();
	}

	@Override
	public Void setMainArtists(Set<Artist> artists) {
		return original.setMainArtists(artists);
	}

	@Override
	public Void addMainArtist(Artist artist) {
		return original.addMainArtist(artist);
	}

	@Override
	public Collection<Artist> getFeatures() {
		return original.getFeatures();
	}

	@Override
	public Void setFeatures(Set<Artist> features) {
		return original.setFeatures(features);
	}

	@Override
	public Void addFeature(Artist artist) {
		return original.addFeature(artist);
	}

	@Override
	public Void setTitle(String title) {
		return original.setTitle(title);
	}

	@Override
	public Void setTitle(TrackTitle title) {
		return original.setTitle(title);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + versionArtists.hashCode();
		result = prime * result + original.hashCode();
		result = prime * result + versionToken.hashCode();
		result = prime * result + versionType.hashCode();
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
		VersionTrack other = (VersionTrack) obj;
		if (!versionArtists.equals(other.versionArtists)) {
			return false;
		}
		if (!original.equals(other.original)) {
			return false;
		}
		if (!versionToken.equals(other.versionToken)) {
			return false;
		}
		if (versionType != other.versionType) {
			return false;
		}
		return true;
	}



}
