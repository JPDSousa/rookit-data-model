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

import org.rookit.utils.print.TypeFormat;
import org.smof.annnotations.SmofArray;
import org.smof.annnotations.SmofObject;
import org.smof.annnotations.SmofString;
import org.smof.parsers.SmofType;

import artist.Artist;
import utils.PrintUtils;

import static track.DatabaseFields.*;

@SuppressWarnings("javadoc")
public final class VersionTrack extends AbstractTrack {
	
	@SmofArray(name = VERSION_ARTISTS, type = SmofType.OBJECT)
	private final Set<Artist> extraArtists;
	
	@SmofString(name = VERSION_TOKEN)
	private String versionToken;//e.g. club remix
	
	@SmofObject(name = ORIGINAL)
	private final Track original;
	
	@SmofString(name = VERSION_TYPE)
	private final TypeVersion versionType;	
	
	VersionTrack(Track original, TypeVersion versionType) {
		super(TypeTrack.VERSION);
		extraArtists = new LinkedHashSet<>();
		this.original = original;
		this.versionType = versionType;
		setVersionToken("");
	}
	
	public TypeVersion getVersionType() {
		return versionType;
	}

	public Iterable<Artist> getVersionArtists() {
		return extraArtists;
	}

	public void addVersionArtist(Artist extraArtist){
		VALIDATOR.checkArgumentNotNull(extraArtist, "Cannot add a null artist");
		extraArtists.add(extraArtist);
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
		final StringBuilder builder = new StringBuilder(PrintUtils.getIterableAsString(extraArtists, TypeFormat.TITLE, Artist.UNKNOWN));
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
	public Iterable<Artist> getMainArtists() {
		return original.getMainArtists();
	}

	@Override
	public void setMainArtists(Set<Artist> artists) {
		original.setMainArtists(artists);
	}

	@Override
	public void addMainArtist(Artist artist) {
		original.addMainArtist(artist);
	}

	@Override
	public Iterable<Artist> getFeatures() {
		return original.getFeatures();
	}

	@Override
	public void setFeatures(Set<Artist> features) {
		original.setFeatures(features);
	}

	@Override
	public void addFeature(Artist artist) {
		original.addFeature(artist);
	}

	@Override
	public void setTitle(String title) {
		original.setTitle(title);
	}

	@Override
	public void setTitle(TrackTitle title) {
		original.setTitle(title);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((extraArtists == null) ? 0 : extraArtists.hashCode());
		result = prime * result + ((original == null) ? 0 : original.hashCode());
		result = prime * result + ((versionToken == null) ? 0 : versionToken.hashCode());
		result = prime * result + ((versionType == null) ? 0 : versionType.hashCode());
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
		if (extraArtists == null) {
			if (other.extraArtists != null) {
				return false;
			}
		} else if (!extraArtists.equals(other.extraArtists)) {
			return false;
		}
		if (original == null) {
			if (other.original != null) {
				return false;
			}
		} else if (!original.equals(other.original)) {
			return false;
		}
		if (versionToken == null) {
			if (other.versionToken != null) {
				return false;
			}
		} else if (!versionToken.equals(other.versionToken)) {
			return false;
		}
		if (versionType != other.versionType) {
			return false;
		}
		return true;
	}
	
	
	
}
