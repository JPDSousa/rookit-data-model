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
package org.rookit.dm.utils;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.rookit.dm.album.Album;
import org.rookit.dm.album.AlbumFactory;
import org.rookit.dm.album.TypeRelease;
import org.rookit.dm.artist.Artist;
import org.rookit.dm.artist.ArtistFactory;
import org.rookit.dm.genre.Genre;
import org.rookit.dm.genre.GenreFactory;
import org.rookit.dm.genre.Genreable;
import org.rookit.dm.track.Track;
import org.rookit.dm.track.TrackFactory;
import org.rookit.dm.track.TypeTrack;
import org.rookit.dm.track.TypeVersion;

import com.google.common.collect.Sets;

@SuppressWarnings("javadoc")
public final class DMTestFactory {

	public static final Path TEST_RESOURCE = Paths.get("testStore");
	public static final Path TRACK_RESOURCE = TEST_RESOURCE.resolve("tracks").resolve("unparsed");
	public static final Path FORMATS = TRACK_RESOURCE.getParent().resolve("testFormats");

	private static DMTestFactory factory;

	public static DMTestFactory getDefault(){
		if(factory == null){
			factory = new DMTestFactory();
		}
		return factory;
	}
	
	private DMTestFactory(){}

	public Track getRandomTrack() {
		final String title = RandomStringUtils.randomAlphabetic(20);
		return getRandomTrack(title);
	}
	
	public Track getRandomTrack(String title) {
		final Set<Artist> mainArtists = getRandomSetOfArtists();
		final Set<Artist> features = Sets.newLinkedHashSet();
		final Set<Genre> genres = getRandomSetOfGenres();
		
		return createOriginalTrack(title, mainArtists, features, genres);
	}
	
	public Track createOriginalTrack(String title, Set<Artist> mainArtists, Set<Artist> features, Set<Genre> genres) {
		final Track track = TrackFactory.getDefault().createOriginalTrack(title);
		track.setMainArtists(mainArtists);
		track.setFeatures(features);
		track.setGenres(genres);
		
		return track;
	}

	public Set<Track> getRandomSetOfTracks(){
		Set<Track> tracks = new HashSet<>();
		Random random = new Random();
		for(int i=0; i<random.nextInt(19)+1; i++){
			tracks.add(getRandomTrack());
		}
		return tracks;
	}

	public Album getRandomAlbum(){
		Random random = new Random();
		final TypeRelease type = TypeRelease.values()[random.nextInt(TypeRelease.values().length)];	
		final String title = "AlbumTest"+random.nextInt(500);
		final Set<Artist> artists = getRandomSetOfArtists();
		
		return AlbumFactory.getDefault().createSingleArtistAlbum(title, type, artists);
	}

	public Set<Album> getRandomSetOfAlbuns(){
		Set<Album> albuns = new HashSet<>();
		Random random = new Random();
		for(int i=0; i<random.nextInt(19)+1; i++){
			albuns.add(getRandomAlbum());
		}
		return albuns;
	}

	public Set<Artist> getRandomSetOfArtists(){
		Set<Artist> artists = new HashSet<>();
		Random random = new Random();
		for(int i=0; i<random.nextInt(19)+1; i++){
			artists.add(getRandomArtist());
		}
		return artists;
	}

	public Artist getRandomArtist(){
		Random random = new Random();
		Artist artist = ArtistFactory.getDefault().createArtist("art"+random.nextInt(500));
		return artist;
	}

	public Set<Genre> getRandomSetOfGenres(){
		Set<Genre> genres = new HashSet<>();
		Random random = new Random();
		for(int i=0; i<random.nextInt(19)+1; i++){
			genres.add(getRandomGenre());
		}
		return genres;
	}

	public Genre getRandomGenre(){
		Random random = new Random();
		return GenreFactory.getDefault().createGenre("Genre"+random.nextInt(500));
	}
	
	public TypeTrack getRandomTrackType() {
		final TypeTrack[] values = TypeTrack.values();
		final Random random = new Random();
		final int index = random.nextInt(values.length);
		return values[index];
	}

	public TypeVersion getRandomVersionType() {
		final TypeVersion[] values = TypeVersion.values();
		final Random random = new Random();
		final int index = random.nextInt(values.length);
		return values[index];
	}

	public void testGenres(final Genreable g) {
		Set<Genre> genres = getRandomSetOfGenres();
		g.setGenres(Sets.newLinkedHashSet());
		for(Genre genre : genres){
			g.addGenre(genre);
		}
		assertEquals(genres, g.getGenres());
	}
	
	public Map<String, String> getRandomDataMap(int size){
		Map<String, String> randomData = new HashMap<>();

		for(int i=0; i<size; i++){
			randomData.put(RandomStringUtils.randomAlphabetic(20), RandomStringUtils.randomAlphanumeric(20));
		}
		return randomData;
	}

	public List<String> getRandomDataList(int size){
		List<String> randomData = new ArrayList<>();

		for(int i=0; i<size; i++){
			randomData.add(RandomStringUtils.randomAlphabetic(20));
		}

		return randomData;
	}

	public String randomString() {
		return RandomStringUtils.randomAlphabetic(20);
	}
}
