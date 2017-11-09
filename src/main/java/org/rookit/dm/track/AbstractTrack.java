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

import static org.rookit.dm.track.DatabaseFields.*;

import java.time.Duration;
import java.util.Collection;
import java.util.Set;

import org.rookit.dm.artist.Artist;
import org.rookit.dm.genre.AbstractGenreable;
import org.smof.annnotations.SmofArray;
import org.smof.annnotations.SmofBoolean;
import org.smof.annnotations.SmofNumber;
import org.smof.annnotations.SmofObject;
import org.smof.annnotations.SmofString;
import org.smof.gridfs.SmofGridRef;
import org.smof.gridfs.SmofGridRefFactory;
import org.smof.parsers.SmofType;

import com.google.common.collect.Sets;

abstract class AbstractTrack extends AbstractGenreable implements Track {
	
	@SmofString(name = TYPE)
	private final TypeTrack type;
	
	@SmofObject(name = PATH, required = true, preInsert = false, bucketName = AUDIO)
	private final SmofGridRef path;
	
	@SmofNumber(name = BPM)
	private short bpm;
	
	@SmofString(name = LYRICS)
	private String lyrics;

	@SmofString(name = HIDDEN_TRACK)
	private String hiddenTrack;
	
	@SmofArray(name = PRODUCERS, type = SmofType.OBJECT)
	private Set<Artist> producers;
	
	@SmofBoolean(name = EXPLICIT)
	private boolean explicit;
		
	protected AbstractTrack(TypeTrack type){
		super();
		producers = Sets.newLinkedHashSetWithExpectedSize(3);
		path = SmofGridRefFactory.newEmptyRef();
		hiddenTrack = "";
		this.type = type;
	}

	@Override
	public TypeTrack getType() {
		return type;
	}

	@Override
	public Void setHiddenTrack(String hiddenTrack) {
		VALIDATOR.checkArgumentNotNull(hiddenTrack, "The title \"" + hiddenTrack + "\" is not valid for a track");
		this.hiddenTrack = hiddenTrack;
		return null;
	}

	@Override
	public String getHiddenTrack() {
		return hiddenTrack;
	}

	@Override
	public Void setProducers(Set<Artist> producers) {
		VALIDATOR.checkArgumentNotNull(producers, "The set of producers cannot be null");
		this.producers = producers;
		return null;
	}

	@Override
	public Collection<Artist> getProducers() {
		return producers;
	}

	@Override
	public Void addProducer(Artist producer) {
		VALIDATOR.checkArgumentNotNull(producer, "Cannot add a null producer");
		producers.add(producer);
		return null;
	}

	@Override
	public Void setBPM(short bpm) {
		VALIDATOR.checkArgumentBetween(bpm, 0, MAX_BPM, 
				"The bpm cannot be negative. Use 0 to erase bpm data", 
				"The bpm value (" + bpm + ") cannot be bigger than " + MAX_BPM);
		this.bpm = bpm;
		return null;
	}

	@Override
	public short getBPM() {
		return bpm;
	}

	@Override
	public String getLyrics() {
		return lyrics;
	}

	@Override
	public Void setLyrics(String lyrics) {
		VALIDATOR.checkArgumentNotNull(lyrics, "The lyrics string cannot be null");
		this.lyrics = lyrics;
		return null;
	}
	
	@Override
	public String toString() {
		return getLongFullTitle().toString();
	}

	@Override
	public Void setExplicit(boolean explicit) {
		this.explicit = explicit;
		return null;
	}

	@Override
	public boolean isExplicit() {
		return explicit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
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
		AbstractTrack other = (AbstractTrack) obj;
		if (type != other.type) {
			return false;
		}
		return true;
	}

	@Override
	public SmofGridRef getPath() {
		return path;
	}

	@Override
	public Void setDuration(Duration duration) {
		if(path != null) {
			path.putMetadataEntry(DURATION, duration);
		}
		else {
			super.setDuration(duration);
		}
		return null;
	}

	@Override
	public Duration getDuration() {
		if(path != null) {
			return path.getMetadata().get(DURATION, Duration.ZERO);
		}
		return super.getDuration();
	}

	@Override
	public int compareTo(Track o) {
		final int title = getTitle().toString().compareTo(o.getTitle().toString());
		return title == 0 ? getIdAsString().compareTo(o.getIdAsString()) : title;
	}
	
}
