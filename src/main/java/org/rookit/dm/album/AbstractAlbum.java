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

import static org.rookit.dm.album.DatabaseFields.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.rookit.dm.artist.Artist;
import org.rookit.dm.genre.Genre;
import org.rookit.dm.track.Track;
import org.rookit.dm.utils.DataModelValidator;
import org.smof.annnotations.SmofArray;
import org.smof.annnotations.SmofBuilder;
import org.smof.annnotations.SmofByte;
import org.smof.annnotations.SmofDate;
import org.smof.annnotations.SmofNumber;
import org.smof.annnotations.SmofObject;
import org.smof.annnotations.SmofString;
import org.smof.element.AbstractElement;
import org.smof.parsers.SmofType;

/**
 * Abstract implementation of the {@link Album} interface. Extend this class in
 * order to create a custom album type. 
 */
public abstract class AbstractAlbum extends AbstractElement implements Album {

	private static final DataModelValidator VALIDATOR = DataModelValidator.getDefault();
	
	/** Title of the album */
	@SmofString(name = TITLE, required = true)
	private String title;

	@SmofString(name = TYPE, required = true)
	private final TypeAlbum type;

	/** Set of authors of the album */
	@SmofArray(name = ARTISTS, required = true, type = SmofType.OBJECT)
	private Set<Artist> artists;
	/** 
	 * Map of discs, containing the discs of the album
	 * Key ({@link String}) - name of the disc
	 * Value ({@link Disc}}Disc) - disc object
	 */
	@SmofObject(name = DISCS)
	private Map<String, Disc> discs;

	/**
	 * Genres of the album
	 */
	@SmofArray(name = GENRES, type = SmofType.OBJECT)
	private Set<Genre> genres;

	/**
	 * Release of the album
	 */
	@SmofDate(name = RELEASE_DATE)
	private LocalDate releaseDate;
	/**
	 * Type of release
	 */
	@SmofString(name = RELEASE_TYPE)
	private final TypeRelease releaseType;

	/**
	 * Byte array containing the image of the album
	 */
	@SmofByte(name = COVER)
	private byte[] cover;
	
	@SmofNumber(name = DURATION)
	private long duration;
	
	@SmofNumber(name = PLAYS)
	private long plays;

	/**
	 * Default constructor for the object. All subclasses should use this constructor in order to create a
	 * fully functional album.
	 * 
	 * @param name title of the album
	 * @param type type of release
	 * @param artists artists responsible for album release. Can be an empty set which will be filled later, although cannot be null
	 */
	protected AbstractAlbum(TypeAlbum type, String name, TypeRelease releaseType, Set<Artist> artists){
		this.title = name;
		this.releaseType = releaseType;
		this.type = type;
		this.artists = artists;
		discs = new LinkedHashMap<>();
		genres = new LinkedHashSet<>();
	}

	@Override
	public String getFullTitle() {
		return releaseType.getFormattedName(title);
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String title) {
		VALIDATOR.checkArgumentStringNotEmpty(title, "A title must be specified");
		this.title = title;
	}

	@Override
	public Iterable<Artist> getArtists() {
		return artists;
	}

	@Override
	public void addArtist(Artist artist) {
		VALIDATOR.checkArgumentNotNull(artist, "Cannot add a null artist");
		artists.add(artist);
	}

	@Override
	public void setArtists(Set<Artist> artists) {
		VALIDATOR.checkArgumentNonEmptyCollection(artists, "Albums cannot have an empty artist set");
		this.artists = artists;
	}

	@Override
	public final Iterable<Track> getTracks() {
		final List<Track> tracks = discs.values().stream()
				.flatMap(d -> d.getTracks().stream())
				.collect(Collectors.toList());
		return tracks;
	}

	@Override
	public final Iterable<Track> getTracks(String discName){
		final Disc disc = getDisc(discName, false);
		return disc.getTracks();
	}

	@Override
	public Iterable<Integer> getTrackNumbers(String cd) {
		final Disc disc = getDisc(cd, false);
		return disc.tracks.keySet();
	}

	@Override
	public final Track getTrack(String discName, Integer number) {
		final Disc disc = getDisc(discName, false);
		return disc.getTrack(number);
	}

	private Disc getDisc(String discName, boolean create) {
		VALIDATOR.checkArgumentStringNotEmpty(discName, "The disc name is not valid");
		Disc disc = discs.get(discName);
		if(!create) {
			final String errorMsg = "The disc " + discName + " was not found in album " + getTitle();
			VALIDATOR.checkArgumentNotNull(disc, errorMsg);
		}
		else if(disc == null) {
			disc = new Disc();
			discs.put(discName, disc);
		}
		return disc;
	}

	@Override
	public final void addTrack(Track track, Integer number) {
		VALIDATOR.checkArgumentPositive(number, "The track number cannot be null or negative");
		final Disc disc = getDefaultDisc();
		addTrack(track, number, disc);
	}

	private Disc getDefaultDisc() {
		final String errMsg = getFullTitle() + " has more than one disc, thus one must be explicitly specified";
		discs.putIfAbsent(DEFAULT_DISC, new Disc());
		VALIDATOR.checkSingleEntryMap(discs, errMsg);
		return discs.get(DEFAULT_DISC);
	}

	@Override
	public final void addTrack(Track track, Integer number, String discName) {
		final Disc disc = getDisc(discName, true);
		addTrack(track, number, disc);
	}

	private void addTrack(Track track, Integer number, Disc disc) {
		VALIDATOR.checkArgumentNotNull(number, "The number cannot be null");
		if(number.equals(NUMBERLESS)) {
			addTrackLast(track, disc);
		}
		else {
			VALIDATOR.checkArgumentNotNull(track, "The track cannot be null");
			disc.add(track, number);
		}
	}

	@Override
	public final void addTrackLast(Track track) {
		final Disc disc = getDefaultDisc();
		addTrackLast(track, disc);
	}

	@Override
	public final void addTrackLast(Track track, String discName) {
		final Disc disc = getDisc(discName, true);
		addTrackLast(track, disc);
	}

	private void addTrackLast(Track track, Disc disc) {
		final Integer number = disc.getNextEmpty();
		addTrack(track, number, disc);
	}

	@Override
	public final void relocate(String discName, Integer number, String newDiscName, Integer newNumber) {
		//discName and newDiscName are validated by getDisc()
		VALIDATOR.checkArgumentNotNull(number, "Must specify a non-null track number to relocate");
		VALIDATOR.checkArgumentNotNull(newNumber, "Must specify a non-null track number to relocate");
		final Disc oldDisc = getDisc(discName, false);
		final Track track = oldDisc.remove(number);
		final Disc newDisc = getDisc(newDiscName, true);
		newDisc.add(track, newNumber);
	}

	@Override
	public final int getTracksCount() {
		int counter = 0;

		for(Disc disc : discs.values()){
			counter += disc.getTrackCount();
		}

		return counter;
	}

	@Override
	public final int getTracksCount(String discName) {
		final Disc disc = getDisc(discName, false);
		return disc.getTrackCount();
	}

	@Override
	public final LocalDate getReleaseDate() {
		return releaseDate;
	}

	@Override
	public final void setReleaseDate(LocalDate year) {
		VALIDATOR.checkArgumentNotNull(year, "The year cannot be null");
		this.releaseDate = year;
	}

	@Override
	public final double getDurationSec() {
		long total = 0;

		for(Track track : getTracks()){
			total += track.getDuration();
		}

		return total;
	}

	@Override
	public final double getDurationSec(String discName){
		long total = 0;

		for(Track track : getTracks(discName)){
			total += track.getDuration();
		}

		return total;
	}

	@Override
	public final Iterable<Genre> getAllGenres() {
		final Set<Genre> genres = new LinkedHashSet<Genre>();
		getTracks().forEach(t -> t.getGenres().forEach(g -> genres.add(g)));

		return genres;
	}

	@Override
	public final Iterable<Genre> getGenres() {
		return genres;
	}

	@Override
	public final void addGenre(Genre genre) {
		VALIDATOR.checkArgumentNotNull(genre, "Cannot add a null genre");
		genres.add(genre);
	}

	@Override
	public void setGenres(Set<Genre> genres) {
		VALIDATOR.checkArgumentNotNull(genres, "The genre set cannot be null");
		this.genres = genres;
	}

	@Override
	public final int getDiscCount() {
		return getDiscs().size();
	}

	@Override
	public final Set<String> getDiscs() {
		return discs.keySet();
	}

	@Override
	public final TypeRelease getReleaseType(){
		return releaseType;
	}

	@Override
	public final void setCover(byte[] image) {
		VALIDATOR.checkArgumentNotNull(image, "The image must contain data");
		cover = image;
	}

	@Override
	public byte[] getCover() {
		return cover;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((artists == null) ? 0 : artists.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((releaseType == null) ? 0 : releaseType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		AbstractAlbum other = (AbstractAlbum) obj;
		if (artists == null) {
			if (other.artists != null) {
				return false;
			}
		} else if (!artists.equals(other.artists)) {
			return false;
		}
		if (title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!title.equals(other.title)) {
			return false;
		}
		if (releaseType != other.releaseType) {
			return false;
		}
		return true;
	}

	@Override
	public final boolean contains(Track track) {
		VALIDATOR.checkArgumentNotNull(track, "The track to test must not be null");
		return discs.values().stream()
				.flatMap(d -> d.getTracks().stream())
				.filter(t -> t.equals(track))
				.findFirst()
				.isPresent();
	}

	@Override
	public boolean contains(String disc, Integer track) {
		VALIDATOR.checkArgumentStringNotEmpty(disc, "Disc name is not valid");
		VALIDATOR.checkArgumentNotNull(track, "Must specify a non-null track number");
		return discs.containsKey(disc) && discs.get(disc).tracks.containsKey(track);
	}

	@Override
	public final TypeAlbum getAlbumType() {
		return type;
	}

	/**
	 * <h1>Disc class.</h1> This class is private and its only use is to
	 * create an abstraction level that eases the process of manage discs
	 * inside albums.
	 * @author Joao
	 *
	 */
	public static class Disc {

		/**
		 * 
		 */
		/**
		 * Map that contains the tracks.
		 * <p>Key - track number
		 * <p>Value - track object
		 */
		@SmofObject(name="tracks", mapValueType = SmofType.OBJECT)
		private final Map<Integer, Track> tracks;

		@SmofBuilder
		private Disc(){
			tracks = new LinkedHashMap<>();
		}

		private Track getTrack(Integer number) {
			return tracks.get(number);
		}

		private Track remove(Integer trackNumber) {
			return tracks.remove(trackNumber);
		}

		private void add(Track track, Integer number) {
			tracks.put(number, track);
		}

		private Integer getNextEmpty() {
			Integer emptyIndex = 1;
			while(tracks.containsKey(emptyIndex)){
				emptyIndex++;
			}
			return emptyIndex;
		}

		private List<Track> getTracks(){
			return new ArrayList<>(tracks.values());
		}

		private int getTrackCount(){
			return tracks.size();
		}
	}

	@Override
	public Integer getTrackNumber(Track track) {
		final Pair<String, Integer> meta = getTrackMetadata(track);
		if(meta != null) {
			return meta.getRight();
		}
		return null;
	}

	@Override
	public String getTrackDisc(Track track) {
		final Pair<String, Integer> meta = getTrackMetadata(track);
		if(meta != null) {
			return meta.getLeft();
		}
		return null;
	}
	
	private Pair<String, Integer> getTrackMetadata(Track track) {
		for(String discName : getDiscs()) {
			for(Integer number : getTrackNumbers(discName)) {
				final Track currentTrack = getTrack(discName, number);
				if(track.equals(currentTrack)) {
					return Pair.of(discName, number);
				}
			}
		}
		return null;
	}

	@Override
	public long getPlays() {
		return plays;
	}

	@Override
	public void play() {
		this.plays++;
	}

	@Override
	public void setPlays(long plays) {
		VALIDATOR.checkArgumentPositive(plays, "Plays cannot be negative");
		this.plays = plays;
	}

	@Override
	public void setDuration(long duration) {
		this.duration = duration;
	}

	@Override
	public long getDuration() {
		return duration;
	}

}
