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
package album;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.rookit.dm.album.Album;
import org.rookit.dm.album.AlbumFactory;
import org.rookit.dm.album.TypeRelease;
import org.rookit.dm.artist.Artist;
import org.rookit.dm.genre.Genre;
import org.rookit.dm.track.Track;
import org.rookit.dm.utils.DMTestFactory;

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
	public void initializeTest(){
		guineaPig = factory.getRandomAlbum();
	}

	@Test
	public void testFullTitle() {
		assertNotNull("The full title cannot be null", guineaPig.getFullTitle());
		assertNotEquals("The full title cannot be empty", "", guineaPig.getFullTitle());
	}

	@Test
	public void testTitle() {
		String testName = "album";
		guineaPig.setTitle(testName);
		assertEquals("Name is not being properly assigned!", testName, guineaPig.getTitle());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyAlbumTitle(){
		guineaPig.setTitle("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullAlbumTitle() {
		guineaPig.setTitle(null);
	}

	@Test
	public void testArtists() {
		final Artist artist = factory.getRandomArtist();
		final Set<Artist> artists = Sets.newLinkedHashSet(guineaPig.getArtists());
		assertNotNull("The artist set can never be null", guineaPig.getArtists());
		guineaPig.addArtist(artist);
		artists.add(artist);
		assertEquals("Artists are not being properly added!", artists, guineaPig.getArtists());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullArtists() {
		guineaPig.setArtists(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyArtist() {
		guineaPig.setArtists(Sets.newLinkedHashSet());
	}

	@Test
	public void testTracksNoArguments() {
		List<Track> tracks = new ArrayList<>(factory.getRandomSetOfTracks());

		for(Track track : tracks){
			guineaPig.addTrackLast(track);
		}
		assertEquals("Tracks are not being properly assigned!", tracks, guineaPig.getTracks());
	}

	@Test
	public void testNullTracksNoArguments() {
		assertNotNull("The track set can never be null", guineaPig.getTracks());
	}

	@Test
	public void testTracksByDisc() {
		final String discName = "disc";
		final List<Track> tracks = new ArrayList<>(factory.getRandomSetOfTracks());

		for(Track track : tracks) {
			guineaPig.addTrackLast(track, discName);
		}
		guineaPig.addTrackLast(factory.getRandomTrack(), "other disc");
		assertEquals("Tracks are not benig properly assigned", tracks, guineaPig.getTracks(discName));
	}
	
	@Test
	public void testNullTracksByDisc() {
		final String discName = "disc1";
		guineaPig.addTrackLast(factory.getRandomTrack(), discName);
		assertNotNull("The track set can never be null", guineaPig.getTracks(discName));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullDiscTracksByDisc() {
		guineaPig.getTracks(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyDiscTracksByDisc() {
		guineaPig.getTracks("");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNoSuchDiscTracks(){
		guineaPig.getTracks("cd1");
	}

	@Test
	public void testTrackNumbers() {
		final String discName = "disc";
		final List<Track> tracks = new ArrayList<>(factory.getRandomSetOfTracks());
		final Set<Integer> numbers = new LinkedHashSet<>();

		for(int i = 0; i < tracks.size(); i++) {
			guineaPig.addTrack(tracks.get(i), i+1, discName);
			numbers.add(i+1);
		}
		guineaPig.addTrackLast(factory.getRandomTrack(), "other disc");
		assertEquals("Tracks are not being properly assigned", numbers, guineaPig.getTrackNumbers(discName));
	}
	
	@Test
	public void testNullTrackNumbers() {
		final String discName = "discName";
		guineaPig.addTrackLast(factory.getRandomTrack(), discName);
		assertNotNull("The track set can never be null", guineaPig.getTrackNumbers(discName));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullDiscTrackNumbers() {
		guineaPig.getTrackNumbers(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyDiscTrackNumbers() {
		guineaPig.getTrackNumbers("");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNoSuchDiscTrackNumbers(){
		guineaPig.getTrackNumbers("cd1");
	}
	
	@Test
	public void testGetTrack() {
		final Track track = factory.getRandomTrack();
		final String discName = "disc";
		final Integer number = 8;
		guineaPig.addTrack(track, number, discName);
		assertEquals("Get/add track is not working", track, guineaPig.getTrack(discName, number));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNullTrackAddTrack() {
		guineaPig.addTrack(null, 2, "disc");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNullNumberAddTrack() {
		guineaPig.addTrack(factory.getRandomTrack(), null, "disc");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNullDiscAddTrack() {
		guineaPig.addTrack(factory.getRandomTrack(), 2, null);
	}

	@Test
	public void testAddNumberlessTrack() {
		List<Track> tracks = new ArrayList<>(factory.getRandomSetOfTracks());

		for(Track track : tracks) {
			guineaPig.addTrack(track, 0);
		}
		assertEquals("Numberless tracks are not being correctly added", tracks, guineaPig.getTracks());
	}

	@Test
	public void testAddTrackLast() {
		final List<Track> tracks = new ArrayList<>(factory.getRandomSetOfTracks());

		for(Track track : tracks) {
			guineaPig.addTrackLast(track);
		}
		assertEquals("Tracks are not being properly added to last position!", tracks, guineaPig.getTracks());
	}
	
	@Test(expected = RuntimeException.class)
	public void testDiscAmbiguityAddTrackLast() {
		guineaPig.addTrackLast(factory.getRandomTrack(), "cd1");
		guineaPig.addTrackLast(factory.getRandomTrack(), "cd2");
		//this should throw an exception, as it is ambiguous to which of
		//the discs the track is to be added
		guineaPig.addTrackLast(factory.getRandomTrack());
	}
	
	@Test
	public void testRelocate() {
		final Track track = factory.getRandomTrack();
		guineaPig.addTrack(track, 1, "cd1");
		guineaPig.relocate("cd1", 1, "cd2", 2);
		assertEquals("Tracks are not being properly relocated", track, guineaPig.getTrack("cd2", 2));
	}

	@Test
	public void testTracksCount() {
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
	
	public void testEmptyTrackCount() {
		assertEquals("An empty album has always 0 track", 0, guineaPig.getTracksCount());
	}

	@Test
	public void testTracksCountDisc() {
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
	public void testEmptyTracksCountDisc() {
		final List<Track> tracks = new ArrayList<>(factory.getRandomSetOfTracks());
		
		for(Track track : tracks) {
			guineaPig.addTrackLast(track, "dics1");
		}
		guineaPig.getTracksCount("disc2");
	}

	@Test
	public void testReleaseDate() {
		final LocalDate release = LocalDate.now();
		guineaPig.setReleaseDate(release);
		assertEquals("Release date is not being properly assigned!", release, guineaPig.getReleaseDate());
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testNullReleaseDate() {
		guineaPig.setReleaseDate(null);
	}

	@Test
	public void testAllGenres() {
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
	public void testGenres() {
		factory.testGenres(guineaPig);
	}
	
	@Test
	public void testDiscCount() {
		guineaPig.addTrackLast(factory.getRandomTrack(), "dics1");
		guineaPig.addTrackLast(factory.getRandomTrack(), "dics2");
		assertEquals("Unexpected number of discs", 2, guineaPig.getDiscCount());
	}
	
	@Test
	public void testDiscs() {
		final Set<String> discs = Sets.newLinkedHashSet(Arrays.asList("disc1", "disc2"));
		guineaPig.addTrackLast(factory.getRandomTrack(), "disc1");
		guineaPig.addTrackLast(factory.getRandomTrack(), "disc2");
		assertEquals("Discs do not match", discs, guineaPig.getDiscs());
	}

	@Test
	public void testType() {
		guineaPig = AlbumFactory.getDefault().createSingleArtistAlbum("Album", TypeRelease.BESTOF, factory.getRandomSetOfArtists());
		assertEquals("Type is not being properly assigned!", TypeRelease.BESTOF, guineaPig.getReleaseType());
	}

	@Test
	public void testEqualsObject() {
		final AlbumFactory albumFactory = AlbumFactory.getDefault();
		final String title = "album";
		final TypeRelease release = TypeRelease.DELUXE;
		final Set<Artist> artists = factory.getRandomSetOfArtists();
		assertTrue("Equals should return true", albumFactory.createSingleArtistAlbum(title, release, artists)
				.equals(albumFactory.createSingleArtistAlbum(title, release, artists)));
		assertFalse("Equals should return false: artists not matching!", albumFactory.createSingleArtistAlbum(title, release, artists)
				.equals(albumFactory.createSingleArtistAlbum(title, release, factory.getRandomSetOfArtists())));
		assertFalse("Equals should return false: title not matching!", albumFactory.createSingleArtistAlbum(title, release, artists)
				.equals(albumFactory.createSingleArtistAlbum(StringUtils.reverse(title), release, artists)));
		assertFalse("Equals should return false: release not matching!", albumFactory.createSingleArtistAlbum(title, release, artists)
				.equals(albumFactory.createSingleArtistAlbum(title, TypeRelease.LIMITED, artists)));
	}

	@Test
	public void testCover() {
		Random random = new Random();
		byte[] cover = new byte[2048];
		random.nextBytes(cover);
		guineaPig.setCover(cover);
		assertEquals("Cover not being properly set!", cover, guineaPig.getCover());
	}

}
