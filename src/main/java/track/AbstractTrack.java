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

import static track.DatabaseFields.*;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import org.smof.annnotations.SmofArray;
import org.smof.annnotations.SmofBoolean;
import org.smof.annnotations.SmofDate;
import org.smof.annnotations.SmofNumber;
import org.smof.annnotations.SmofObject;
import org.smof.annnotations.SmofString;
import org.smof.element.AbstractElement;
import org.smof.gridfs.SmofGridRef;
import org.smof.gridfs.SmofGridRefFactory;
import org.smof.parsers.SmofType;

import artist.Artist;
import genre.Genre;
import utils.CoreValidator;

abstract class AbstractTrack extends AbstractElement implements Track {

	protected static final CoreValidator VALIDATOR = CoreValidator.getDefault();
	
	@SmofString(name = TYPE)
	private final TypeTrack type;
	
	@SmofObject(name = PATH, required = true)
	private final SmofGridRef path;
	
	@SmofNumber(name = BPM)
	private short bpm;
	
	@SmofNumber(name = PLAYS)
	private long plays;
	
	@SmofString(name = LYRICS)
	private String lyrics;

	@SmofString(name = HIDDEN_TRACK)
	private String hiddenTrack;
	
	@SmofArray(name = GENRES, type = SmofType.OBJECT)
	private Set<Genre> genres;
	
	@SmofArray(name = PRODUCERS, type = SmofType.OBJECT)
	private Set<Artist> producers;
	
	@SmofNumber(name = DURATION)
	private long duration;
	
	@SmofBoolean(name = EXPLICIT)
	private boolean explicit;

	@SmofDate(name = STORAGE_DATE_TIME)
	private LocalDateTime storageDateTime;
		
	protected AbstractTrack(TypeTrack type){
		producers = new LinkedHashSet<>();
		genres = new LinkedHashSet<>();
		path = SmofGridRefFactory.newEmptyRef();
		hiddenTrack = "";
		this.type = type;
		duration = UNDEF_DURATION;
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
	public long getPlays() {
		return plays;
	}

	@Override
	public void play() {
		plays++;
	}

	@Override
	public void setPlays(long plays) {
		VALIDATOR.checkArgumentPositive(plays, "Plays cannot be negative");
		this.plays = plays;
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
	public void setStorageDateTime(LocalDateTime dateTime) {
		VALIDATOR.checkArgumentNotNull(dateTime, "The storage time cannot be null");
		this.storageDateTime = dateTime;
	}

	@Override
	public LocalDateTime getStorageDateTime() {
		return storageDateTime;
	}
	
	@Override
	public String toString() {
		return getLongFullTitle().toString();
	}

	@Override
	public void setDuration(long duration) {
		this.duration = duration;
	}

	@Override
	public long getDuration() {
		return duration;
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
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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

}
