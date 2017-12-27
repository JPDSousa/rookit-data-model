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

import com.google.common.collect.Sets;

@Entity("Track")
final class OriginalTrack extends AbstractTrack {

	private String title;
	
	@Reference(idOnly = true)
	private Set<Artist> mainArtists;

	OriginalTrack(String title) {
		this();
		setTitle(title);
	}
	
	private OriginalTrack() {
		super(TypeTrack.ORIGINAL);
		mainArtists = Sets.newLinkedHashSetWithExpectedSize(3);
	}

	@Override
	public TrackTitle getLongFullTitle() {
		return getFullTitle().appendArtists(getMainArtists());
	}

	@Override
	public TrackTitle getFullTitle() {
		return getTitle().appendFeats(getFeatures());
	}

	@Override
	public Collection<Artist> getMainArtists() {
		return mainArtists;
	}

	@Override
	public Void setMainArtists(Set<Artist> mainArtists) {
		VALIDATOR.checkArgumentNonEmptyCollection(mainArtists, "The main artists set cannot be neither null or empty");
		VALIDATOR.checkNotIntersecting(mainArtists, getFeatures(), "features");
		VALIDATOR.checkNotIntersecting(mainArtists, getProducers(), "producers");
		this.mainArtists = mainArtists;
		return null;
	}

	@Override
	public Void addMainArtist(Artist artist) {
		VALIDATOR.checkArgumentNotNull(artist, "Cannot add a null artist");
		VALIDATOR.checkArgumentNotContains(artist, getProducers(), "Cannot add a producer as main artist");
		VALIDATOR.checkArgumentNotContains(artist, getFeatures(), "Cannot add a feature as main artist");
		mainArtists.add(artist);
		return null;
	}

	@Override
	public VersionTrack getAsVersionTrack() {
		VALIDATOR.invalidOperation(getFullTitle() + " is not a version track");
		//dead code, validator always throws
		return null;
	}

	@Override
	public boolean isVersionTrack() {
		return false;
	}
	
	@Override
	public TrackTitle getTitle() {
		return new TrackTitle(title).appendHiddenTrack(getHiddenTrack());
	}

	@Override
	public Void setTitle(TrackTitle title) {
		VALIDATOR.checkArgumentNotNull(title, "Title cannot be null");
		setTitle(title.getTitle());
		if(title.getHiddenTrack() != null){
			setHiddenTrack(title.getHiddenTrack());
		}
		return null;
	}
	
	@Override
	public Void setTitle(String title) {
		VALIDATOR.checkArgumentStringNotEmpty(title, "Title cannot be null");
		this.title = title;
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((mainArtists == null) ? 0 : mainArtists.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		OriginalTrack other = (OriginalTrack) obj;
		if (mainArtists == null) {
			if (other.mainArtists != null) {
				return false;
			}
		} else if (!mainArtists.equals(other.mainArtists)) {
			return false;
		}
		if (title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!title.equalsIgnoreCase(other.title)) {
			return false;
		}
		return true;
	}
}
