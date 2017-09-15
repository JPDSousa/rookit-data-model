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

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.text.RandomStringGenerator;
import org.rookit.dm.album.Album;
import org.rookit.dm.album.AlbumFactory;
import org.rookit.dm.album.TypeRelease;
import org.rookit.dm.artist.Artist;
import org.rookit.dm.artist.ArtistFactory;
import org.rookit.dm.artist.TypeArtist;
import org.rookit.dm.genre.Genre;
import org.rookit.dm.genre.GenreFactory;
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
	private static final int RANDOM_LENGTH = 999999999;

	private static DMTestFactory factory;

	public static DMTestFactory getDefault(){
		if(factory == null){
			factory = new DMTestFactory();
		}
		return factory;
	}
	
	private final RandomStringGenerator randomStringGenerator;
	private final TrackFactory trackFactory;
	
	private DMTestFactory(){
		randomStringGenerator = new RandomStringGenerator.Builder()
				.withinRange('a', 'z')
				.build();
		trackFactory = TrackFactory.getDefault();
	}

	public Track getRandomTrack() {
		final String title = randomString();
		return getRandomTrack(title);
	}
	
	public Track getRandomTrack(String title) {
		final Set<Artist> mainArtists = getRandomSetOfArtists();
		final Set<Artist> features = Sets.newLinkedHashSet();
		final Set<Genre> genres = getRandomSetOfGenres();
		
		return createOriginalTrack(title, mainArtists, features, genres);
	}
	
	public final Track getRandomTrack(TypeTrack type) {
		final String title = randomString();
		final Track original = getRandomTrack();
		final TypeVersion versionType = TypeVersion.EXTENDED;
		return trackFactory.createTrack(type, title, original, versionType);	
	}
	
	public Track createOriginalTrack(String title, Set<Artist> mainArtists, Set<Artist> features, Set<Genre> genres) {
		final Track track = trackFactory.createOriginalTrack(title);
		track.setMainArtists(mainArtists);
		track.setFeatures(features);
		track.setGenres(genres);
		
		return track;
	}

	public Set<Track> getRandomSetOfTracks(){
		Set<Track> tracks = Sets.newLinkedHashSet();
		Random random = new Random();
		for(int i=0; i<random.nextInt(19)+1; i++){
			tracks.add(getRandomTrack());
		}
		return tracks;
	}

	public Album getRandomAlbum(){
		Random random = new Random();
		final TypeRelease type = TypeRelease.values()[random.nextInt(TypeRelease.values().length)];	
		final String title = "AlbumTest"+random.nextInt(RANDOM_LENGTH);
		final Set<Artist> artists = getRandomSetOfArtists();
		
		return AlbumFactory.getDefault().createSingleArtistAlbum(title, type, artists);
	}

	public Set<Album> getRandomSetOfAlbuns(){
		Set<Album> albuns = Sets.newLinkedHashSet();
		Random random = new Random();
		for(int i=0; i<random.nextInt(19)+1; i++){
			albuns.add(getRandomAlbum());
		}
		return albuns;
	}

	public Set<Artist> getRandomSetOfArtists(){
		Set<Artist> artists = Sets.newLinkedHashSet();
		Random random = new Random();
		for(int i=0; i<random.nextInt(19)+1; i++){
			artists.add(getRandomArtist());
		}
		return artists;
	}

	public Artist getRandomArtist(){
		Random random = new Random();
		final TypeArtist type = TypeArtist.GROUP;
		Artist artist = ArtistFactory.getDefault().createArtist(type, "art"+random.nextInt(RANDOM_LENGTH));
		return artist;
	}

	public Set<Genre> getRandomSetOfGenres(){
		Set<Genre> genres = Sets.newLinkedHashSet();
		Random random = new Random();
		for(int i=0; i<random.nextInt(19)+1; i++){
			genres.add(getRandomGenre());
		}
		return genres;
	}

	public Genre getRandomGenre(){
		Random random = new Random();
		return GenreFactory.getDefault().createGenre("Genre"+random.nextInt(RANDOM_LENGTH));
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
	
	public Map<String, String> getRandomDataMap(int size){
		Map<String, String> randomData = new HashMap<>();

		for(int i=0; i<size; i++){
			randomData.put(randomString(), randomString());
		}
		return randomData;
	}

	public List<String> getRandomDataList(int size){
		List<String> randomData = new ArrayList<>();

		for(int i=0; i<size; i++){
			randomData.add(randomString());
		}

		return randomData;
	}

	public String randomString() {
		return randomStringGenerator.generate(20);
	}
}
