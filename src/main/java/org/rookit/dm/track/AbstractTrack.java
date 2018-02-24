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

import static org.rookit.api.dm.track.TrackFields.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.play.StaticPlaylist;
import org.rookit.api.dm.track.Track;
import org.rookit.api.dm.track.TypeTrack;
import org.rookit.api.dm.track.audio.TrackKey;
import org.rookit.api.dm.track.audio.TrackMode;
import org.rookit.api.storage.DBManager;
import org.rookit.dm.genre.AbstractGenreable;

import com.google.common.collect.Sets;
import java.util.Objects;
import javax.annotation.Generated;

abstract class AbstractTrack extends AbstractGenreable implements Track {
	
	private static final short UNINITIALIZED = -1;
	
	@Property(TYPE)
	private final TypeTrack type;
	
	@Embedded(PATH)
	private final BiStream path;
	
	@Property(LYRICS)
	private String lyrics;

	@Property(HIDDEN_TRACK)
	private String hiddenTrack;
	
	@Reference(value = FEATURES, idOnly = true)
	private Set<Artist> features;
	
	@Reference(value = PRODUCERS, idOnly = true)
	private Set<Artist> producers;
	
	@Property(EXPLICIT)
	private Boolean explicit;
	
	// Audio features
	@Property(BPM)
	private short bpm;
	
	@Property(KEY)
	private TrackKey trackKey;
	
	@Property(MODE)
	private TrackMode trackMode;
	
	@Property(INSTRUMENTAL)
	private Boolean isInstrumental;
	
	@Property(LIVE)
	private Boolean isLive;
	
	@Property(ACOUSTIC)
	private Boolean isAcoustic;
	
	@Property(DANCEABILITY)
	private double danceability;
	
	@Property(ENERGY)
	private double energy;
	
	@Property(VALENCE)
	private double valence;
		
	protected AbstractTrack(TypeTrack type, BiStream biStream){
		super();
		producers = Sets.newLinkedHashSetWithExpectedSize(3);
		features = Sets.newLinkedHashSetWithExpectedSize(3);
		path = biStream;
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
		return Collections.unmodifiableSet(features);
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
		return Collections.unmodifiableSet(producers);
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
		VALIDATOR.checkArgumentNotNull(explicit, "Explicit cannot be null");
		this.explicit = explicit;
		return null;
	}

	@Override
	public Boolean isExplicit() {
		return explicit;
	}

	@Override
	@Generated(value = "GuavaEclipsePlugin")
	public int hashCode() {
		return Objects.hash(super.hashCode(), type);
	}

	@Override
	@Generated(value = "GuavaEclipsePlugin")
	public boolean equals(Object object) {
		if (object instanceof AbstractTrack) {
			if (!super.equals(object))
				return false;
			AbstractTrack that = (AbstractTrack) object;
			return Objects.equals(this.type, that.type);
		}
		return false;
	}

	@Override
	public BiStream getPath() {
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

	@Override
	public StaticPlaylist freeze(DBManager db, int limit) {
		final StaticPlaylist playlist = db.getFactories().getPlaylistFactory()
				.createStaticPlaylist(getLongFullTitle().toString());
		playlist.add(this);
		return playlist;
	}
	
}
