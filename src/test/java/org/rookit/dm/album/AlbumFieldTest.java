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

import static org.junit.Assert.*;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.rookit.dm.album.Album;
import org.rookit.dm.album.AlbumFactory;
import org.rookit.dm.album.TypeRelease;
import org.rookit.dm.artist.Artist;
import org.rookit.dm.genre.Genre;
import org.rookit.dm.test.DMTestFactory;
import org.rookit.dm.track.Track;
import org.rookit.dm.utils.TestUtils;

import com.google.common.collect.Sets;

@SuppressWarnings("javadoc")
public class AlbumFieldTest {

	private Album guineaPig;
	private static DMTestFactory factory;

	@BeforeClass
	public static void initialize(){
		factory = DMTestFactory.getDefault();
	}

	@Before
	public final void initializeTest(){
		guineaPig = factory.getRandomAlbum();
	}

	@Test
	public final void testFullTitle() {
		assertNotNull("The full title cannot be null", guineaPig.getFullTitle());
		assertNotEquals("The full title cannot be empty", "", guineaPig.getFullTitle());
	}

	@Test
	public final void testTitle() {
		String testName = "album";
		guineaPig.setTitle(testName);
		assertEquals("Name is not being properly assigned!", testName, guineaPig.getTitle());
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testEmptyAlbumTitle(){
		guineaPig.setTitle("");
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testNullAlbumTitle() {
		guineaPig.setTitle(null);
	}
	
	@Test
	public final void testPlayable() {
		TestUtils.testPlayable(guineaPig);
	}

	@Test
	public final void testArtists() {
		final Artist artist = factory.getRandomArtist();
		final Set<Artist> artists = Sets.newLinkedHashSet(guineaPig.getArtists());
		assertNotNull("The artist set can never be null", guineaPig.getArtists());
		guineaPig.addArtist(artist);
		artists.add(artist);
		assertEquals("Artists are not being properly added!", artists, guineaPig.getArtists());
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testNullArtists() {
		guineaPig.setArtists(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testEmptyArtist() {
		guineaPig.setArtists(Sets.newLinkedHashSet());
	}

	@Test
	public final void testTracksNoArguments() {
		List<Track> tracks = new ArrayList<>(factory.getRandomSetOfTracks());

		for(Track track : tracks){
			guineaPig.addTrackLast(track);
		}
		final Collection<Track> actual = guineaPig.getTracks()
				.stream()
				.map(TrackSlot::getTrack)
				.collect(Collectors.toList());
		assertEquals("Tracks are not being properly assigned!", tracks, actual);
	}

	@Test
	public final void testNullTracksNoArguments() {
		assertNotNull("The track set can never be null", guineaPig.getTracks());
	}

	@Test
	public final void testTracksByDisc() {
		final String discName = "disc";
		final List<Track> tracks = new ArrayList<>(factory.getRandomSetOfTracks());

		for(Track track : tracks) {
			guineaPig.addTrackLast(track, discName);
		}
		guineaPig.addTrackLast(factory.getRandomOriginalTrack(), "other disc");
		final Collection<Track> actual = guineaPig.getTracks(discName)
				.stream()
				.map(TrackSlot::getTrack)
				.collect(Collectors.toList());
		assertEquals("Tracks are not benig properly assigned", tracks, actual);
	}
	
	@Test
	public final void testNullTracksByDisc() {
		final String discName = "disc1";
		guineaPig.addTrackLast(factory.getRandomOriginalTrack(), discName);
		assertNotNull("The track set can never be null", guineaPig.getTracks(discName));
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testNullDiscTracksByDisc() {
		guineaPig.getTracks(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testEmptyDiscTracksByDisc() {
		guineaPig.getTracks("");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testNoSuchDiscTracks(){
		guineaPig.getTracks("cd1");
	}

	@Test
	public final void testTrackNumbers() {
		final String discName = "disc";
		final List<Track> tracks = new ArrayList<>(factory.getRandomSetOfTracks());
		final Set<Integer> numbers = new LinkedHashSet<>();

		for(int i = 0; i < tracks.size(); i++) {
			guineaPig.addTrack(tracks.get(i), i+1, discName);
			numbers.add(i+1);
		}
		guineaPig.addTrackLast(factory.getRandomOriginalTrack(), "other disc");
		assertEquals("Tracks are not being properly assigned", numbers, guineaPig.getTrackNumbers(discName));
	}
	
	@Test
	public final void testNullTrackNumbers() {
		final String discName = "discName";
		guineaPig.addTrackLast(factory.getRandomOriginalTrack(), discName);
		assertNotNull("The track set can never be null", guineaPig.getTrackNumbers(discName));
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testNullDiscTrackNumbers() {
		guineaPig.getTrackNumbers(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testEmptyDiscTrackNumbers() {
		guineaPig.getTrackNumbers("");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testNoSuchDiscTrackNumbers(){
		guineaPig.getTrackNumbers("cd1");
	}
	
	@Test
	public final void testGetTrack() {
		final Track track = factory.getRandomOriginalTrack();
		final String discName = "disc";
		final Integer number = 8;
		guineaPig.addTrack(track, number, discName);
		final TrackSlot expected = TrackSlot.create(discName, number, track);
		assertEquals("Get/add track is not working", expected, guineaPig.getTrack(discName, number));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testNullTrackAddTrack() {
		guineaPig.addTrack(null, 2, "disc");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testNullNumberAddTrack() {
		guineaPig.addTrack(factory.getRandomOriginalTrack(), null, "disc");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testNullDiscAddTrack() {
		guineaPig.addTrack(factory.getRandomOriginalTrack(), 2, null);
	}

	@Test
	public final void testAddNumberlessTrack() {
		List<Track> tracks = new ArrayList<>(factory.getRandomSetOfTracks());

		for(Track track : tracks) {
			guineaPig.addTrack(track, 0);
		}
		final Collection<Track> actual = guineaPig.getTracks()
				.stream()
				.map(TrackSlot::getTrack)
				.collect(Collectors.toList());
		assertEquals("Numberless tracks are not being correctly added", tracks, actual);
	}

	@Test
	public final void testAddTrackLast() {
		final List<Track> tracks = new ArrayList<>(factory.getRandomSetOfTracks());

		for(Track track : tracks) {
			guineaPig.addTrackLast(track);
		}
		final Collection<Track> actual = guineaPig.getTracks()
				.stream()
				.map(TrackSlot::getTrack)
				.collect(Collectors.toList());
		assertEquals("Tracks are not being properly added to last position!", tracks, actual);
	}
	
	@Test(expected = RuntimeException.class)
	public final void testDiscAmbiguityAddTrackLast() {
		guineaPig.addTrackLast(factory.getRandomOriginalTrack(), "cd1");
		guineaPig.addTrackLast(factory.getRandomOriginalTrack(), "cd2");
		//this should throw an exception, as it is ambiguous to which of
		//the discs the track is to be added
		guineaPig.addTrackLast(factory.getRandomOriginalTrack());
	}
	
	@Test
	public final void testRelocate() {
		final Track track = factory.getRandomOriginalTrack();
		final String previousDiscName = "cd1";
		final int previousNumber = 1;
		final int nextNumber = 2;
		final String nextDiscName = "cd2";
		guineaPig.addTrack(track, previousNumber, previousDiscName);
		guineaPig.relocate(previousDiscName, previousNumber, nextDiscName, nextNumber);
		final TrackSlot expected = TrackSlot.create(nextDiscName, nextNumber, track);
		assertEquals("Tracks are not being properly relocated", expected, guineaPig.getTrack(nextDiscName, nextNumber));
	}

	@Test
	public final void testTracksCount() {
		Set<Track> tracksCD1 = factory.getRandomSetOfTracks();
		Set<Track> tracksCD2 = factory.getRandomSetOfTracks();

		for(Track track : tracksCD1){
			guineaPig.addTrackLast(track, "cd1");
		}
		for(Track track : tracksCD2){
			guineaPig.addTrackLast(track, "cd2");
		}
		assertEquals("Track counter should count all the album's tracks!", tracksCD1.size()+tracksCD2.size(), guineaPig.getTracksCount());
	}
	
	public final void testEmptyTrackCount() {
		assertEquals("An empty album has always 0 track", 0, guineaPig.getTracksCount());
	}

	@Test
	public final void testTracksCountDisc() {
		Set<Track> tracksCD1 = factory.getRandomSetOfTracks();
		Set<Track> tracksCD2 = factory.getRandomSetOfTracks();

		for(Track track : tracksCD1){
			guineaPig.addTrackLast(track, "cd1");
		}
		for(Track track : tracksCD2){
			guineaPig.addTrackLast(track, "cd2");
		}
		assertEquals("Track counter should count all the album's tracks!", tracksCD1.size(), guineaPig.getTracksCount("cd1"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testEmptyTracksCountDisc() {
		final List<Track> tracks = new ArrayList<>(factory.getRandomSetOfTracks());
		
		for(Track track : tracks) {
			guineaPig.addTrackLast(track, "dics1");
		}
		guineaPig.getTracksCount("disc2");
	}

	@Test
	public final void testReleaseDate() {
		final LocalDate release = LocalDate.now();
		guineaPig.setReleaseDate(release);
		assertEquals("Release date is not being properly assigned!", release, guineaPig.getReleaseDate());
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public final void testNullReleaseDate() {
		guineaPig.setReleaseDate(null);
	}
	
	@Test
	public final void testDuration() {
		final Random random = new Random();
		final Duration totalDuration = Duration.ZERO;
		assertEquals(Duration.ZERO, guineaPig.getDuration());
		final Set<Track> tracks = factory.getRandomSetOfTracks();
		for(Track track : tracks) {
			final Duration randomDuration = Duration.ofMillis(random.nextLong());
			track.setDuration(randomDuration);
			totalDuration.plus(randomDuration);
			guineaPig.addTrackLast(track);
		}
		assertEquals(totalDuration, guineaPig.getDuration());
	}

	@Test
	public final void testAllGenres() {
		final Set<Genre> genres = Sets.newLinkedHashSet(guineaPig.getGenres());

		for(Track track : factory.getRandomSetOfTracks()){
			final Set<Genre> trackGenres = factory.getRandomSetOfGenres();
			track.setGenres(trackGenres);
			genres.addAll(trackGenres);
			guineaPig.addTrackLast(track);
		}
		assertEquals("All Genres not properly being extracted!", genres, guineaPig.getAllGenres());
	}

	@Test
	public final void testGenres() {
		TestUtils.testGenres(guineaPig);
	}
	
	@Test
	public final void testDiscCount() {
		guineaPig.addTrackLast(factory.getRandomOriginalTrack(), "dics1");
		guineaPig.addTrackLast(factory.getRandomOriginalTrack(), "dics2");
		assertEquals("Unexpected number of discs", 2, guineaPig.getDiscCount());
	}
	
	@Test
	public final void testDiscs() {
		final Set<String> discs = Sets.newLinkedHashSet(Arrays.asList("disc1", "disc2"));
		guineaPig.addTrackLast(factory.getRandomOriginalTrack(), "disc1");
		guineaPig.addTrackLast(factory.getRandomOriginalTrack(), "disc2");
		assertEquals("Discs do not match", discs, guineaPig.getDiscs());
	}

	@Test
	public final void testType() {
		guineaPig = AlbumFactory.getDefault().createSingleArtistAlbum("Album", TypeRelease.BESTOF, factory.getRandomSetOfArtists());
		assertEquals("Type is not being properly assigned!", TypeRelease.BESTOF, guineaPig.getReleaseType());
	}

	@Test
	public final void testEqualsObject() {
		final AlbumFactory albumFactory = AlbumFactory.getDefault();
		final String title = "album";
		final TypeRelease release = TypeRelease.DELUXE;
		final Set<Artist> artists = factory.getRandomSetOfArtists();
		assertEquals(albumFactory.createSingleArtistAlbum(title, release, artists),
				albumFactory.createSingleArtistAlbum(title, release, artists));
		assertEquals(albumFactory.createSingleArtistAlbum(title, release, artists).hashCode(),
				albumFactory.createSingleArtistAlbum(title, release, artists).hashCode());
		assertNotEquals("Artists not matching!", albumFactory.createSingleArtistAlbum(title, release, artists),
				albumFactory.createSingleArtistAlbum(title, release, factory.getRandomSetOfArtists()));
		assertNotEquals("Artists not matching!", albumFactory.createSingleArtistAlbum(title, release, artists).hashCode(),
				albumFactory.createSingleArtistAlbum(title, release, factory.getRandomSetOfArtists()).hashCode());
		assertNotEquals("Title not matching!", albumFactory.createSingleArtistAlbum(title, release, artists), 
				albumFactory.createSingleArtistAlbum(StringUtils.reverse(title), release, artists));
		assertNotEquals("Title not matching!", albumFactory.createSingleArtistAlbum(title, release, artists).hashCode(), 
				albumFactory.createSingleArtistAlbum(StringUtils.reverse(title), release, artists).hashCode());
		assertNotEquals("Release not matching!", albumFactory.createSingleArtistAlbum(title, release, artists), 
				albumFactory.createSingleArtistAlbum(title, TypeRelease.LIMITED, artists));
		assertNotEquals("Release not matching!", albumFactory.createSingleArtistAlbum(title, release, artists).hashCode(), 
				albumFactory.createSingleArtistAlbum(title, TypeRelease.LIMITED, artists).hashCode());
	}

	@Test
	public final void testCover() throws IOException {
		Random random = new Random();
		byte[] cover = new byte[2048];
		byte[] actual = new byte[2048];
		random.nextBytes(cover);
		guineaPig.setCover(cover);
		guineaPig.getCover().getAttachedByteArray().read(actual);
		assertArrayEquals("Cover not being properly set!", cover, actual);
	}
	
	@Test
	public final void testCompareTo() {
		final AlbumFactory factory = AlbumFactory.getDefault();
		testCompareTo(AlbumFieldTest.factory.getRandomAlbum());
		testCompareTo(factory.createVAAlbum("someRandomTitle", TypeRelease.BESTOF));
	}
	
	private void testCompareTo(Album album) {
		assertEquals(guineaPig.getTitle().compareTo(album.getTitle()), guineaPig.compareTo(album));
	}

}
