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
package org.rookit.dm.album;

import static org.rookit.api.dm.album.AlbumFields.*;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.album.Album;
import org.rookit.api.dm.album.TrackSlot;
import org.rookit.api.dm.album.TypeAlbum;
import org.rookit.api.dm.album.TypeRelease;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.play.StaticPlaylist;
import org.rookit.api.dm.track.Track;
import org.rookit.api.storage.DBManager;
import org.rookit.dm.genre.AbstractGenreable;
import org.rookit.dm.utils.DataModelValidator;
import org.rookit.utils.DurationUtils;
import org.rookit.utils.SupplierUtils;
import org.rookit.utils.VoidUtils;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Objects;
import javax.annotation.Generated;

/**
 * Abstract implementation of the {@link Album} interface. Extend this class in
 * order to create a custom album type. 
 */
public abstract class AbstractAlbum extends AbstractGenreable implements Album {

	private static final DataModelValidator VALIDATOR = DataModelValidator.getDefault();

	/** 
	 * Title of the album
	 */
	@Property(TITLE)
	private String title;

	@Property(TYPE)
	private final TypeAlbum type;

	/** Set of authors of the album */
	@Reference(value = ARTISTS, idOnly = true)
	private Set<Artist> artists;

	/** 
	 * Map of discs, containing the discs of the album
	 * Key ({@link String}) - name of the disc
	 * Value ({@link Disc}}Disc) - disc object
	 */
	@Embedded(value = DISCS)
	private Map<String, Disc> discs;

	@Property(TRACKS)
	private int tracks;

	/**
	 * Release of the album
	 */
	@Property(RELEASE_DATE)
	private LocalDate releaseDate;

	/**
	 * Type of release
	 */
	@Property(RELEASE_TYPE)
	private final TypeRelease releaseType;

	/**
	 * Smof GridFS Reference containing the image of the album
	 */
	@Embedded(COVER)
	private final BiStream cover;

	/**
	 * Default constructor for the object. All subclasses should use this constructor in order to create a
	 * fully functional album.
	 * 
	 * @param name title of the album
	 * @param type type of release
	 * @param artists artists responsible for album release. Can be an empty set which will be filled later, although cannot be null
	 */
	protected AbstractAlbum(
			final TypeAlbum type, String name, 
			final TypeRelease releaseType, 
			final Set<Artist> artists,
			final BiStream cover){
		this.title = name;
		this.releaseType = releaseType;
		this.type = type;
		this.artists = artists;
		this.tracks = 0;
		discs = Maps.newLinkedHashMap();
		this.cover = cover;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public Void setTitle(final String title) {
		VALIDATOR.checkArgumentStringNotEmpty(title, "A title must be specified");
		this.title = title;
		return VoidUtils.returnVoid();
	}

	@Override
	public Collection<Artist> getArtists() {
		return Collections.unmodifiableSet(artists);
	}

	@Override
	public Void addArtist(final Artist artist) {
		VALIDATOR.checkArgumentNotNull(artist, "Cannot add a null artist");
		artists.add(artist);
		return VoidUtils.returnVoid();
	}

	@Override
	public Void setArtists(final Set<Artist> artists) {
		VALIDATOR.checkArgumentNonEmptyCollection(artists, "Albums cannot have an empty artist set");
		this.artists = artists;
		return VoidUtils.returnVoid();
	}

	@Override
	public final List<TrackSlot> getTracks() {
		final List<TrackSlot> tracks = Lists.newArrayListWithCapacity(getTracksCount());
		for(String disc : getDiscs()) {
			tracks.addAll(discs.get(disc).getTracksWithSlots(disc));
		}
		return Collections.unmodifiableList(tracks);
	}

	@Override
	public final Collection<TrackSlot> getTracks(final String discName){
		final Disc disc = getDisc(discName, false);
		return Collections.unmodifiableCollection(disc.getTracksWithSlots(discName));
	}

	@Override
	public Collection<Integer> getTrackNumbers(final String discName) {
		final Disc disc = getDisc(discName, false);
		return Collections.unmodifiableSet(disc.tracks.keySet());
	}

	@Override
	public final TrackSlot getTrack(final String discName, final Integer number) {
		final Disc disc = getDisc(discName, false);
		return new TrackSlotImpl(discName, number, disc.getTrack(number));
	}

	private Disc getDisc(final String discName, final boolean create) {
		VALIDATOR.checkArgumentStringNotEmpty(discName, "The disc name is not valid");
		
		Disc disc = discs.get(discName);
		if(!create) {
			final String errorMsg = "The disc " + discName + " was not found in album " + getTitle();
			VALIDATOR.checkArgumentNotNull(disc, errorMsg);
		}
		else if(Objects.isNull(disc)) {
			disc = new Disc();
			discs.put(discName, disc);
		}
		return disc;
	}

	@Override
	public Void addTrack(final Track track, final Integer number, final String discName) {
		final Disc disc = getDisc(discName, true);
		addTrack(track, number, disc);
		return VoidUtils.returnVoid();
	}

	private synchronized void addTrack(final Track track, final Integer number, final Disc disc) {
		VALIDATOR.checkArgumentNotNull(number, "The number cannot be null");
		if(number.equals(NUMBERLESS)) {
			addTrackLast(track, disc);
		}
		else {
			VALIDATOR.checkArgumentNotNull(track, "The track cannot be null");
			disc.add(track, number);
		}
		tracks++;
	}

	@Override
	public final Void addTrackLast(final Track track, final String discName) {
		final Disc disc = getDisc(discName, true);
		addTrackLast(track, disc);
		return VoidUtils.returnVoid();
	}

	private void addTrackLast(final Track track, final Disc disc) {
		synchronized(disc) {
			final Integer number = disc.getNextEmpty();
			addTrack(track, number, disc);
		}
	}

	@Override
	public final void relocate(final String discName, final Integer number, final String newDiscName, final Integer newNumber) {
		//discName and newDiscName are validated by getDisc()
		VALIDATOR.checkArgumentNotNull(number, "Must specify a non-null track number to relocate");
		VALIDATOR.checkArgumentNotNull(newNumber, "Must specify a non-null track number to relocate");
		final Disc oldDisc = getDisc(discName, false);
		final Track track = oldDisc.remove(number);
		if(track == null) {
			final String errorMsg = "there is no track in [" + discName 
					+ "|"+number + "] to relocate";
			VALIDATOR.handleException(new RuntimeException(errorMsg));
		}
		final Disc newDisc = getDisc(newDiscName, true);
		newDisc.add(track, newNumber);
	}

	@Override
	public final int getTracksCount() {
		return tracks;
	}

	@Override
	public final int getTracksCount(final String discName) {
		final Disc disc = getDisc(discName, false);
		return disc.getTrackCount();
	}

	@Override
	public final LocalDate getReleaseDate() {
		return releaseDate;
	}

	@Override
	public final Void setReleaseDate(final LocalDate year) {
		VALIDATOR.checkArgumentNotNull(year, "The year cannot be null");
		this.releaseDate = year;
		return VoidUtils.returnVoid();
	}

	@Override
	public final Void setDuration(final Duration duration) {
		return VALIDATOR.invalidOperation("Cannot set duration for albums");
	}
	
	@Override
	public Optional<Duration> getDuration() {
		return Album.super.getDuration();
	}

	@Override
	public final Duration getDuration(final String discName){
		return getDisc(discName, false).getTracks().stream()
				.map(Track::getDuration)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.reduce(Duration.ZERO, DurationUtils::plus);
	}

	@Override
	public final Set<String> getDiscs() {
		return Collections.unmodifiableSet(discs.keySet());
	}

	@Override
	public final TypeRelease getReleaseType(){
		return releaseType;
	}

	@Override
	public final Void setCover(byte[] image) {
		VALIDATOR.checkArgumentNotNull(image, "The image must contain data");

		try {
			this.cover.toOutput().write(image);
		} catch (IOException e) {
			VALIDATOR.handleIOException(e);
		}
		return VoidUtils.returnVoid();
	}

	@Override
	public BiStream getCover() {
		return cover;
	}

	@Override
	@Generated(value = "GuavaEclipsePlugin")
	public int hashCode() {
		return Objects.hash(super.hashCode(), title, type, artists, releaseType);
	}

	@Override
	@Generated(value = "GuavaEclipsePlugin")
	public boolean equals(Object object) {
		if (object instanceof AbstractAlbum) {
			if (!super.equals(object))
				return false;
			AbstractAlbum that = (AbstractAlbum) object;
			return Objects.equals(this.title, that.title) 
					&& Objects.equals(this.type, that.type)
					&& Objects.equals(this.artists, that.artists) 
					&& Objects.equals(this.releaseType, that.releaseType);
		}
		return false;
	}

	@Override
	public boolean contains(final String disc, final Integer track) {
		VALIDATOR.checkArgumentStringNotEmpty(disc, "Disc name is not valid");
		VALIDATOR.checkArgumentNotNull(track, "Must specify a non-null track number");

		return discs.containsKey(disc) && discs.get(disc).tracks.containsKey(track);
	}

	@Override
	public final TypeAlbum getAlbumType() {
		return type;
	}

	@Override
	public StaticPlaylist freeze(final DBManager db, final int limit) {
		final StaticPlaylist playlist = db.getFactories()
				.getPlaylistFactory()
				.createStaticPlaylist(getFullTitle());
		getTracks().stream()
		.map(TrackSlot::getTrack)
		.forEach(playlist::add);

		return playlist;
	}

	/**
	 * <h1>Disc class.</h1> This class is private and its only use is to
	 * create an abstraction level that eases the process of manage discs
	 * inside albums.
	 * 
	 * @author Joao Sousa (jpd.sousa@campus.fct.unl.pt)
	 */
	@Embedded
	public static class Disc {

		/**
		 * Map that contains the tracks.
		 * <p>Key - track number
		 * <p>Value - track object
		 */
		@Reference(idOnly = true)
		private final Map<Integer, Track> tracks;

		private Disc(){
			tracks = Maps.newLinkedHashMap();
		}

		private Collection<TrackSlot> getTracksWithSlots(final String thisDiscName) {
			return this.tracks.keySet().stream()
					.map(number -> new TrackSlotImpl(thisDiscName, number, this.tracks.get(number)))
					.collect(Collectors.toList());
		}

		private Track getTrack(final Integer number) {
			return tracks.get(number);
		}

		private Track remove(final Integer trackNumber) {
			return tracks.remove(trackNumber);
		}

		private void add(final Track track, final Integer number) {
			if(tracks.containsKey(number)) {
				final String errorMsg = number + " already contains a track";
				VALIDATOR.handleException(new RuntimeException(errorMsg));
			}
			tracks.put(number, track);
		}

		private Integer getNextEmpty() {
			return IntStream.generate(SupplierUtils.incrementalSupplier(1))
					.filter(index -> !tracks.containsKey(index))
					.boxed()
					.findFirst()
					.orElseThrow(() -> new RuntimeException("Cannot find an empty slot"));
		}

		private Collection<Track> getTracks(){
			return Collections.unmodifiableCollection(tracks.values());
		}

		private int getTrackCount(){
			return tracks.size();
		}
	}

}
