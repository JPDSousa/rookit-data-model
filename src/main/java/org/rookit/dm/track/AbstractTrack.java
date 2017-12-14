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

import java.util.Collection;
import java.util.Set;

import org.rookit.dm.artist.Artist;
import org.rookit.dm.genre.AbstractGenreable;
import org.rookit.dm.track.audio.TrackKey;
import org.rookit.dm.track.audio.TrackMode;
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
	
	private static final short UNINITIALIZED = -1;
	
	@SmofString(name = TYPE)
	private final TypeTrack type;
	
	@SmofObject(name = PATH, required = true, preInsert = false)
	private final SmofGridRef path;
	
	@SmofString(name = LYRICS)
	private String lyrics;

	@SmofString(name = HIDDEN_TRACK)
	private String hiddenTrack;
	
	@SmofArray(name = FEATURES, type = SmofType.OBJECT)
	private Set<Artist> features;
	
	@SmofArray(name = PRODUCERS, type = SmofType.OBJECT)
	private Set<Artist> producers;
	
	@SmofBoolean(name = EXPLICIT)
	private Boolean explicit;
	
	// Audio features
	@SmofNumber(name = BPM)
	private short bpm;
	
	@SmofString(name = KEY)
	private TrackKey trackKey;
	
	@SmofString(name = MODE)
	private TrackMode trackMode;
	
	@SmofBoolean(name = INSTRUMENTAL)
	private Boolean isInstrumental;
	
	@SmofBoolean(name = LIVE)
	private Boolean isLive;
	
	@SmofBoolean(name = ACOUSTIC)
	private Boolean isAcoustic;
	
	@SmofNumber(name = DANCEABILITY)
	private double danceability;
	
	@SmofNumber(name = ENERGY)
	private double energy;
	
	@SmofNumber(name = VALENCE)
	private double valence;
		
	protected AbstractTrack(TypeTrack type){
		super();
		producers = Sets.newLinkedHashSetWithExpectedSize(3);
		features = Sets.newLinkedHashSetWithExpectedSize(3);
		path = SmofGridRefFactory.newEmptyRef();
		hiddenTrack = "";
		this.type = type;
		// Audio features
		bpm = UNINITIALIZED;
		danceability = UNINITIALIZED;
		energy = UNINITIALIZED;
		valence = UNINITIALIZED;
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
	public Collection<Artist> getFeatures() {
		return features;
	}

	@Override
	public Void setFeatures(Set<Artist> features) {
		VALIDATOR.checkNotIntersecting(features, getMainArtists(), "main artists");
		VALIDATOR.checkNotIntersecting(features, getProducers(), "producers");
		this.features = features;
		return null;
	}
	
	@Override
	public Void addFeature(Artist artist) {
		VALIDATOR.checkArgumentNotNull(artist, "Cannot add a null feature");
		VALIDATOR.checkArgumentNotContains(artist, getMainArtists(), "Cannot add a main artist as feature");
		VALIDATOR.checkArgumentNotContains(artist, getProducers(), "Cannot add a producer as feature");
		features.add(artist);
		return null;
	}

	@Override
	public Void setProducers(Set<Artist> producers) {
		VALIDATOR.checkNotIntersecting(producers, getMainArtists(), "main artists");
		VALIDATOR.checkNotIntersecting(producers, getFeatures(), "features");
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
		VALIDATOR.checkArgumentNotContains(producer, getMainArtists(), "Cannot add a main artist as producer");
		VALIDATOR.checkArgumentNotContains(producer, getFeatures(), "Cannot add a feature as producer");
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
	public Void setExplicit(Boolean explicit) {
		this.explicit = explicit;
		return null;
	}

	@Override
	public Boolean isExplicit() {
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
	public int compareTo(Track o) {
		final int title = getTitle().toString().compareTo(o.getTitle().toString());
		return title == 0 ? getIdAsString().compareTo(o.getIdAsString()) : title;
	}

	@Override
	public Void setTrackKey(TrackKey trackKey) {
		this.trackKey = trackKey;
		return null;
	}

	@Override
	public Void setTrackMode(TrackMode trackMode) {
		this.trackMode = trackMode;
		return null;
	}

	@Override
	public Void setLive(Boolean isLive) {
		this.isLive = isLive;
		return null;
	}

	@Override
	public Void setInstrumental(Boolean isInstrumental) {
		this.isInstrumental = isInstrumental;
		return null;
	}

	@Override
	public Void setAcoustic(Boolean isAcoustic) {
		this.isAcoustic = isAcoustic;
		return null;
	}

	@Override
	public Void setDanceability(double danceability) {
		this.danceability = danceability;
		return null;
	}

	@Override
	public Void setEnergy(double energy) {
		this.energy = energy;
		return null;
	}

	@Override
	public Void setValence(double valence) {
		this.valence = valence;
		return null;
	}

	@Override
	public TrackKey getTrackKey() {
		return trackKey;
	}

	@Override
	public TrackMode getTrackMode() {
		return trackMode;
	}

	@Override
	public Boolean isInstrumental() {
		return isInstrumental;
	}

	@Override
	public Boolean isLive() {
		return isLive;
	}

	@Override
	public Boolean isAcoustic() {
		return isAcoustic;
	}

	@Override
	public double getDanceability() {
		return danceability;
	}

	@Override
	public double getEnergy() {
		return energy;
	}

	@Override
	public double getValence() {
		return valence;
	}
	
	
	
}
