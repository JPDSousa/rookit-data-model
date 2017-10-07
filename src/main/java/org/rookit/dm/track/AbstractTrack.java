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

import java.util.LinkedHashSet;
import java.util.Set;

import org.rookit.dm.artist.Artist;
import org.rookit.dm.genre.Genre;
import org.rookit.dm.play.AbstractPlayable;
import org.smof.annnotations.SmofArray;
import org.smof.annnotations.SmofBoolean;
import org.smof.annnotations.SmofNumber;
import org.smof.annnotations.SmofObject;
import org.smof.annnotations.SmofString;
import org.smof.gridfs.SmofGridRef;
import org.smof.gridfs.SmofGridRefFactory;
import org.smof.parsers.SmofType;

abstract class AbstractTrack extends AbstractPlayable implements Track {
	
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
	
	@SmofArray(name = GENRES, type = SmofType.OBJECT)
	private Set<Genre> genres;
	
	@SmofArray(name = PRODUCERS, type = SmofType.OBJECT)
	private Set<Artist> producers;
	
	@SmofBoolean(name = EXPLICIT)
	private boolean explicit;
		
	protected AbstractTrack(TypeTrack type){
		super();
		producers = new LinkedHashSet<>();
		genres = new LinkedHashSet<>();
		path = SmofGridRefFactory.newEmptyRef();
		hiddenTrack = "";
		this.type = type;
	}

	@Override
	public Iterable<Genre> getAllGenres() {
		return getGenres();
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
		VALIDATOR.checkArgumentNotNull(genres, "The genre set cannot be null");
		this.genres = genres;
	}

	@Override
	public TypeTrack getType() {
		return type;
	}

	@Override
	public void setHiddenTrack(String hiddenTrack) {
		VALIDATOR.checkArgumentNotNull(hiddenTrack, "The title \"" + hiddenTrack + "\" is not valid for a track");
		this.hiddenTrack = hiddenTrack;
	}

	@Override
	public String getHiddenTrack() {
		return hiddenTrack;
	}

	@Override
	public void setProducers(Set<Artist> producers) {
		VALIDATOR.checkArgumentNotNull(producers, "The set of producers cannot be null");
		this.producers = producers;
	}

	@Override
	public Iterable<Artist> getProducers() {
		return producers;
	}

	@Override
	public void addProducer(Artist producer) {
		VALIDATOR.checkArgumentNotNull(producer, "Cannot add a null producer");
		producers.add(producer);
	}

	@Override
	public void setBPM(short bpm) {
		VALIDATOR.checkArgumentBetween(bpm, 0, MAX_BPM, 
				"The bpm cannot be negative. Use 0 to erase bpm data", 
				"The bpm value (" + bpm + ") cannot be bigger than " + MAX_BPM);
		this.bpm = bpm;
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
	public void setLyrics(String lyrics) {
		VALIDATOR.checkArgumentNotNull(lyrics, "The lyrics string cannot be null");
		this.lyrics = lyrics;
	}
	
	@Override
	public String toString() {
		return getLongFullTitle().toString();
	}

	@Override
	public void setExplicit(boolean explicit) {
		this.explicit = explicit;
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
	public void setDuration(long duration) {
		if(path != null) {
			path.putMetadataEntry(DURATION, duration);
		}
		else {
			super.setDuration(duration);
		}
	}

	@Override
	public long getDuration() {
		if(path != null) {
			return path.getMetadata().getLong(DURATION);
		}
		return super.getDuration();
	}
	
}
