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
import org.rookit.utils.VoidUtils;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;
import java.util.Objects;
import javax.annotation.Generated;
import com.google.common.base.MoreObjects;

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
		
	protected AbstractTrack(final TypeTrack type, final BiStream biStream){
		super();
		producers = Sets.newLinkedHashSetWithExpectedSize(3);
		features = Sets.newLinkedHashSetWithExpectedSize(3);
		path = biStream;
		
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
	public Void setHiddenTrack(final String hiddenTrack) {
		VALIDATOR.checkArgumentNotNull(hiddenTrack, "The title \"" + hiddenTrack + "\" is not valid for a track");
		this.hiddenTrack = hiddenTrack;
		return null;
	}

	@Override
	public Optional<String> getHiddenTrack() {
		return Optional.fromNullable(this.hiddenTrack);
	}
	
	@Override
	public Collection<Artist> getFeatures() {
		return Collections.unmodifiableSet(features);
	}

	@Override
	public Void setFeatures(final Set<Artist> features) {
		VALIDATOR.checkNotIntersecting(features, getMainArtists(), "main artists");
		VALIDATOR.checkNotIntersecting(features, getProducers(), "producers");
		this.features = features;
		return VoidUtils.returnVoid();
	}
	
	@Override
	public Void addFeature(final Artist artist) {
		VALIDATOR.checkArgumentNotNull(artist, "Cannot add a null feature");
		VALIDATOR.checkArgumentNotContains(artist, getMainArtists(), "Cannot add a main artist as feature");
		VALIDATOR.checkArgumentNotContains(artist, getProducers(), "Cannot add a producer as feature");
		features.add(artist);
		return VoidUtils.returnVoid();
	}

	@Override
	public Void setProducers(final Set<Artist> producers) {
		VALIDATOR.checkNotIntersecting(producers, getMainArtists(), "main artists");
		VALIDATOR.checkNotIntersecting(producers, getFeatures(), "features");
		this.producers = producers;
		return VoidUtils.returnVoid();
	}

	@Override
	public Collection<Artist> getProducers() {
		return Collections.unmodifiableSet(producers);
	}

	@Override
	public Void addProducer(final Artist producer) {
		VALIDATOR.checkArgumentNotNull(producer, "Cannot add a null producer");
		VALIDATOR.checkArgumentNotContains(producer, getMainArtists(), "Cannot add a main artist as producer");
		VALIDATOR.checkArgumentNotContains(producer, getFeatures(), "Cannot add a feature as producer");
		producers.add(producer);
		return VoidUtils.returnVoid();
	}

	@Override
	public Void setBPM(final short bpm) {
		VALIDATOR.checkArgumentBetween(bpm, RANGE_BPM, "bpm");
		this.bpm = bpm;
		return VoidUtils.returnVoid();
	}

	@Override
	public Optional<Short> getBPM() {
		return bpm != UNINITIALIZED ? Optional.of(this.bpm) : Optional.absent();
	}

	@Override
	public Optional<String> getLyrics() {
		return Optional.fromNullable(this.lyrics);
	}

	@Override
	public Void setLyrics(final String lyrics) {
		VALIDATOR.checkArgumentStringNotEmpty(lyrics, "The lyrics string cannot be null or empty");
		this.lyrics = lyrics;
		return VoidUtils.returnVoid();
	}
	
	@Override
	@Generated(value = "GuavaEclipsePlugin")
	public String toString() {
		return MoreObjects.toStringHelper(this).add("super", super.toString()).add("type", type).add("path", path)
				.add("lyrics", lyrics).add("hiddenTrack", hiddenTrack).add("features", features)
				.add("producers", producers).add("explicit", explicit).add("bpm", bpm).add("trackKey", trackKey)
				.add("trackMode", trackMode).add("isInstrumental", isInstrumental).add("isLive", isLive)
				.add("isAcoustic", isAcoustic).add("danceability", danceability).add("energy", energy)
				.add("valence", valence).toString();
	}

	@Override
	public Void setExplicit(final boolean explicit) {
		this.explicit = explicit;
		return VoidUtils.returnVoid();
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
	public int compareTo(final Track o) {
		final int title = getTitle().toString().compareTo(o.getTitle().toString());
		return title == 0 ? getIdAsString().compareTo(o.getIdAsString()) : title;
	}

	@Override
	public Void setTrackKey(final TrackKey trackKey) {
		VALIDATOR.checkArgumentNotNull(trackKey, "Track key cannot be null");
		this.trackKey = trackKey;
		return VoidUtils.returnVoid();
	}

	@Override
	public Void setTrackMode(final TrackMode trackMode) {
		VALIDATOR.checkArgumentNotNull(trackMode, "Track mode cannot be null");
		this.trackMode = trackMode;
		return VoidUtils.returnVoid();
	}

	@Override
	public Void setLive(final boolean isLive) {
		this.isLive = isLive;
		return VoidUtils.returnVoid();
	}

	@Override
	public Void setInstrumental(final boolean isInstrumental) {
		this.isInstrumental = isInstrumental;
		return VoidUtils.returnVoid();
	}

	@Override
	public Void setAcoustic(final boolean isAcoustic) {
		this.isAcoustic = isAcoustic;
		return VoidUtils.returnVoid();
	}

	@Override
	public Void setDanceability(final double danceability) {
		VALIDATOR.checkArgumentBetween(danceability, RANGE_DANCEABILITY, "danceability");
		this.danceability = danceability;
		return VoidUtils.returnVoid();
	}

	@Override
	public Void setEnergy(final double energy) {
		VALIDATOR.checkArgumentBetween(energy, RANGE_ENERGY, "energy");
		this.energy = energy;
		return VoidUtils.returnVoid();
	}

	@Override
	public Void setValence(final double valence) {
		VALIDATOR.checkArgumentBetween(valence, RANGE_VALENCE, "valence");
		this.valence = valence;
		return VoidUtils.returnVoid();
	}

	@Override
	public Optional<TrackKey> getTrackKey() {
		return Optional.fromNullable(this.trackKey);
	}

	@Override
	public Optional<TrackMode> getTrackMode() {
		return Optional.fromNullable(this.trackMode);
	}

	@Override
	public Optional<Boolean> isInstrumental() {
		return Optional.fromNullable(this.isInstrumental);
	}

	@Override
	public Optional<Boolean> isLive() {
		return Optional.fromNullable(this.isLive);
	}

	@Override
	public Optional<Boolean> isAcoustic() {
		return Optional.fromNullable(this.isAcoustic);
	}

	@Override
	public Optional<Double> getDanceability() {
		return danceability != UNINITIALIZED ? Optional.of(this.danceability) 
				: Optional.absent();
	}

	@Override
	public Optional<Double> getEnergy() {
		return energy != UNINITIALIZED ? Optional.of(this.energy)
				: Optional.absent();
	}

	@Override
	public Optional<Double> getValence() {
		return valence != UNINITIALIZED ? Optional.of(this.valence)
				: Optional.absent();
	}

	@Override
	public StaticPlaylist freeze(final DBManager db, final int limit) {
		final StaticPlaylist playlist = db.getFactories().getPlaylistFactory()
				.createStaticPlaylist(getLongFullTitle().toString());
		playlist.add(this);
		return playlist;
	}
	
}
