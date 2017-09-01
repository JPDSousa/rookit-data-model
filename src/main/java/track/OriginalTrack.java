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
package track;

import java.util.LinkedHashSet;
import java.util.Set;

import org.smof.annnotations.SmofArray;
import org.smof.annnotations.SmofString;
import org.smof.parsers.SmofType;

import artist.Artist;
import exceptions.InvalidOperationException;

import static track.DatabaseFields.*;

final class OriginalTrack extends AbstractTrack {

	@SmofString(name = TITLE, required = true)
	private String title;
	
	@SmofArray(name = MAIN_ARTISTS, type = SmofType.OBJECT, required = true)
	private Set<Artist> mainArtists;
	
	@SmofArray(name = FEATURES, type = SmofType.OBJECT)
	private Set<Artist> features;

	OriginalTrack(String title) {
		this();
		setTitle(title);
	}
	
	private OriginalTrack() {
		super(TypeTrack.ORIGINAL);
		mainArtists = new LinkedHashSet<>();
		features = new LinkedHashSet<>();
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
	public Set<Artist> getMainArtists() {
		return mainArtists;
	}

	@Override
	public void setMainArtists(Set<Artist> mainArtists) {
		VALIDATOR.checkArgumentNonEmptyCollection(mainArtists, "The main artists set cannot be neither null or empty");
		this.mainArtists = mainArtists;
	}

	@Override
	public Set<Artist> getFeatures() {
		return features;
	}

	@Override
	public void setFeatures(Set<Artist> features) {
		VALIDATOR.checkValidFeatures(features, mainArtists);
		this.features = features;
	}

	@Override
	public void addMainArtist(Artist artist) {
		VALIDATOR.checkArgumentNotNull(artist, "Cannot add a null artist");
		mainArtists.add(artist);
	}

	@Override
	public void addFeature(Artist artist) {
		VALIDATOR.checkArgumentNotContains(artist, mainArtists, "Cannot add a main artist as feature");
		features.add(artist);
	}

	@Override
	public VersionTrack getAsVersionTrack() {
		throw new InvalidOperationException(getFullTitle() + " is not a version track");
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
	public void setTitle(TrackTitle title) {
		VALIDATOR.checkArgumentNotNull(title, "Title cannot be null");
		setTitle(title.getTitle());
		if(title.getHiddenTrack() != null){
			setHiddenTrack(title.getHiddenTrack());
		}
	}
	
	@Override
	public void setTitle(String title) {
		VALIDATOR.checkArgumentStringNotEmpty(title, "Title cannot be null");
		this.title = title;
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
		} else if (!title.equals(other.title)) {
			return false;
		}
		return true;
	}
}