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

import static org.rookit.api.dm.track.TrackFields.ACOUSTIC;
import static org.rookit.api.dm.track.TrackFields.BPM;
import static org.rookit.api.dm.track.TrackFields.DANCEABILITY;
import static org.rookit.api.dm.track.TrackFields.ENERGY;
import static org.rookit.api.dm.track.TrackFields.EXPLICIT;
import static org.rookit.api.dm.track.TrackFields.FEATURES;
import static org.rookit.api.dm.track.TrackFields.HIDDEN_TRACK;
import static org.rookit.api.dm.track.TrackFields.INSTRUMENTAL;
import static org.rookit.api.dm.track.TrackFields.KEY;
import static org.rookit.api.dm.track.TrackFields.LIVE;
import static org.rookit.api.dm.track.TrackFields.LYRICS;
import static org.rookit.api.dm.track.TrackFields.MODE;
import static org.rookit.api.dm.track.TrackFields.PATH;
import static org.rookit.api.dm.track.TrackFields.PRODUCERS;
import static org.rookit.api.dm.track.TrackFields.TYPE;
import static org.rookit.api.dm.track.TrackFields.VALENCE;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Generated;

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

import com.google.common.base.MoreObjects;
import com.google.common.collect.Sets;

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
		VALIDATOR.checkArgument().isNotEmpty(hiddenTrack, "hiddenTrack");
		this.hiddenTrack = hiddenTrack;
		return VoidUtils.returnVoid();
	}

	@Override
	public Optional<String> getHiddenTrack() {
		return Optional.ofNullable(this.hiddenTrack);
	}
	
	@Override
	public Collection<Artist> getFeatures() {
		return Collections.unmodifiableSet(this.features);
	}

	@Override
	public Void setFeatures(final Collection<Artist> features) {
		VALIDATOR.checkArgument().isNotIntersecting(features, getMainArtists(), "features", "mainArtists");
		VALIDATOR.checkArgument().isNotIntersecting(features, getProducers(), "features", "producers");
		this.features = Collections.synchronizedSet(Sets.newHashSet(features));
		return VoidUtils.returnVoid();
	}
	
	@Override
	public Void addFeature(final Artist artist) {
		VALIDATOR.checkArgument().isNotContainedIn(artist, getMainArtists(), "feature");
		VALIDATOR.checkArgument().isNotContainedIn(artist, getProducers(), "feature");
		this.features.add(artist);
		return VoidUtils.returnVoid();
	}

	@Override
	public Void setProducers(final Collection<Artist> producers) {
		VALIDATOR.checkArgument().isNotIntersecting(producers, getMainArtists(), "producers", "main artists");
		VALIDATOR.checkArgument().isNotIntersecting(producers, getFeatures(), "producers", "features");
		this.producers = Collections.synchronizedSet(Sets.newHashSet(producers));
		return VoidUtils.returnVoid();
	}

	@Override
	public Collection<Artist> getProducers() {
		return Collections.unmodifiableSet(this.producers);
	}

	@Override
	public Void addProducer(final Artist producer) {
		VALIDATOR.checkArgument().isNotNull(producer, "producer");
		VALIDATOR.checkArgument().isNotContainedIn(producer, getMainArtists(), "producer");
		VALIDATOR.checkArgument().isNotContainedIn(producer, getFeatures(), "producer");
		this.producers.add(producer);
		return VoidUtils.returnVoid();
	}

	@Override
	public Void setBPM(final short bpm) {
		VALIDATOR.checkArgument().isBetween(bpm, RANGE_BPM, "bpm");
		this.bpm = bpm;
		return VoidUtils.returnVoid();
	}

	@Override
	public Optional<Short> getBPM() {
		return Optional.of(this.bpm)
				.filter(bpm -> bpm != UNINITIALIZED);
	}

	@Override
	public Optional<String> getLyrics() {
		return Optional.ofNullable(this.lyrics);
	}

	@Override
	public Void setLyrics(final String lyrics) {
		VALIDATOR.checkArgument().isNotEmpty(lyrics, "lyrics");
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
		return this.explicit;
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
		return this.path;
	}

	@Override
	public int compareTo(final Track o) {
		final int title = getTitle().toString().compareTo(o.getTitle().toString());
		return title == 0 ? getIdAsString().compareTo(o.getIdAsString()) : title;
	}

	@Override
	public Void setTrackKey(final TrackKey trackKey) {
		VALIDATOR.checkArgument().isNotNull(trackKey, "trackKey");
		this.trackKey = trackKey;
		return VoidUtils.returnVoid();
	}

	@Override
	public Void setTrackMode(final TrackMode trackMode) {
		VALIDATOR.checkArgument().isNotNull(trackMode, "trackMode");
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
		VALIDATOR.checkArgument().isBetween(danceability, RANGE_DANCEABILITY, "danceability");
		this.danceability = danceability;
		return VoidUtils.returnVoid();
	}

	@Override
	public Void setEnergy(final double energy) {
		VALIDATOR.checkArgument().isBetween(energy, RANGE_ENERGY, "energy");
		this.energy = energy;
		return VoidUtils.returnVoid();
	}

	@Override
	public Void setValence(final double valence) {
		VALIDATOR.checkArgument().isBetween(valence, RANGE_VALENCE, "valence");
		this.valence = valence;
		return VoidUtils.returnVoid();
	}

	@Override
	public Optional<TrackKey> getTrackKey() {
		return Optional.ofNullable(this.trackKey);
	}

	@Override
	public Optional<TrackMode> getTrackMode() {
		return Optional.ofNullable(this.trackMode);
	}

	@Override
	public Optional<Boolean> isInstrumental() {
		return Optional.ofNullable(this.isInstrumental);
	}

	@Override
	public Optional<Boolean> isLive() {
		return Optional.ofNullable(this.isLive);
	}

	@Override
	public Optional<Boolean> isAcoustic() {
		return Optional.ofNullable(this.isAcoustic);
	}

	@Override
	public Optional<Double> getDanceability() {
		return Optional.of(this.danceability)
				.filter(danceability -> danceability != UNINITIALIZED);
	}

	@Override
	public Optional<Double> getEnergy() {
		return Optional.of(this.energy)
				.filter(energy -> energy != UNINITIALIZED);
	}

	@Override
	public Optional<Double> getValence() {
		return Optional.of(this.valence)
				.filter(valence -> valence != UNINITIALIZED);
	}

	@Override
	public StaticPlaylist freeze(final DBManager db, final int limit) {
		final StaticPlaylist playlist = db.getFactories().getPlaylistFactory()
				.createStaticPlaylist(getLongFullTitle().toString());
		playlist.addTrack(this);
		return playlist;
	}

	@Override
	public Void clearProducers() {
		this.producers.clear();
		return VoidUtils.returnVoid();
	}

	@Override
	public Void removeProducers(final Collection<Artist> producers) {
		VALIDATOR.checkArgument().isNotNull(producers, "producers");
		this.producers.removeAll(producers);
		return VoidUtils.returnVoid();
	}

	@Override
	public Void removeProducer(final Artist producer) {
		VALIDATOR.checkArgument().isNotNull(producer, "producer");
		this.producers.remove(producer);
		return VoidUtils.returnVoid();
	}

	@Override
	public Void addProducers(final Collection<Artist> producers) {
		VALIDATOR.checkArgument().isNotNull(producers, "producers");
		producers.forEach(this::addProducer);
		return VoidUtils.returnVoid();
	}

	@Override
	public Void resetHiddenTrack() {
		return null;
	}

	@Override
	public Void clearFeatures() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void removeFeatures(Collection<Artist> features) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void removeFeature(final Artist artist) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void addFeatures(final Collection<Artist> features) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
