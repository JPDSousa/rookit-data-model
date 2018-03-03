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

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assume.assumeThat;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.rookit.api.dm.album.Album;
import org.rookit.api.dm.album.AlbumFactory;
import org.rookit.api.dm.album.TrackSlot;
import org.rookit.api.dm.album.TypeRelease;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.factory.RookitFactories;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.track.Track;
import org.rookit.dm.test.DMTestFactory;
import org.rookit.dm.utils.TestUtils;
import org.rookit.test.AbstractTest;
import org.rookit.utils.resource.Resources;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.inject.Injector;

@SuppressWarnings("javadoc")
public class AlbumFieldTest extends AbstractTest<Album> {

	private static DMTestFactory factory;
	private static RookitFactories factories;

	@BeforeClass
	public static final void setUpBeforeClass() {
		final Injector injector = TestUtils.getInjector();
		factories = injector.getInstance(RookitFactories.class);
		factory = injector.getInstance(DMTestFactory.class);
	}

	@Test
	public final void testFullTitle() {
		assertThat(guineaPig.getFullTitle())
		.as("The full title cannot be null")
		.isNotNull();

		assertThat(guineaPig.getFullTitle())
		.as("The full title cannot be empty")
		.isNotEmpty();
	}

	@Test
	public final void testTitle() {
		final String testName = "album";
		guineaPig.setTitle(testName);

		assertThat(guineaPig.getTitle())
		.as("Name is not being properly assigned!")
		.isEqualTo(testName);
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

		assertThat(guineaPig.getArtists())
		.as("The artist set can never be null")
		.isNotNull();

		guineaPig.addArtist(artist);
		artists.add(artist);

		assertThat(guineaPig.getArtists())
		.as("Artists are not being properly added!")
		.isEqualTo(artists);
	}

	@Test
	public final void testGetArtistsIsImmutable() {
		final Collection<Artist> artists = guineaPig.getArtists();

		assertThatThrownBy(() -> artists.add(factory.getRandomArtist()))
		.isInstanceOf(RuntimeException.class);
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testNullArtists() {
		guineaPig.setArtists(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testEmptyArtist() {
		guineaPig.setArtists(Sets.newLinkedHashSet());
	}

	@Test(expected = RuntimeException.class)
	public final void testGetTracksIsIummtable() {
		guineaPig.getTracks().add(new TrackSlotImpl("disc", 2, factory.getRandomOriginalTrack()));
	}

	@Test
	public final void testTracksNoArguments() {
		final List<Track> tracks = new ArrayList<>(factory.getRandomSetOfTracks());
		final String disc = "disc";

		for (final Track track : tracks){
			guineaPig.addTrackLast(track, disc);
		}
		final Collection<Track> actual = guineaPig.getTracks()
				.stream()
				.map(TrackSlot::getTrack)
				.collect(Collectors.toList());
		assertThat(actual).as("Tracks are not being properly assigned!").isEqualTo(tracks);
	}

	@Test
	public final void testNullTracksNoArguments() {
		assertThat(guineaPig.getTracks()).as("The track set can never be null").isNotNull();
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
		assertThat(actual).as("Tracks are not benig properly assigned").isEqualTo(tracks);
	}

	@Test
	public final void testNullTracksByDisc() {
		final String discName = "disc1";
		guineaPig.addTrackLast(factory.getRandomOriginalTrack(), discName);
		assertThat(guineaPig.getTracks(discName)).as("The track set can never be null").isNotNull();
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
		assertThat(guineaPig.getTrackNumbers(discName)).as("Tracks are not being properly assigned").isEqualTo(numbers);
	}

	@Test
	public final void testNullTrackNumbers() {
		final String discName = "discName";
		guineaPig.addTrackLast(factory.getRandomOriginalTrack(), discName);
		assertThat(guineaPig.getTrackNumbers(discName)).as("The track set can never be null").isNotNull();
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
		final TrackSlot expected = new TrackSlotImpl(discName, number, track);

		assertThat(guineaPig.getTrack(discName, number))
		.as("Reading a previously added track")
		.isEqualTo(expected);
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
		final List<Track> tracks = new ArrayList<>(factory.getRandomSetOfTracks());
		final String disc = "disc";

		for (final Track track : tracks) {
			guineaPig.addTrack(track, 0, disc);
		}
		final Collection<Track> actual = guineaPig.getTracks()
				.stream()
				.map(TrackSlot::getTrack)
				.collect(Collectors.toList());
		assertThat(actual).as("Numberless tracks are not being correctly added").isEqualTo(tracks);
	}

	@Test
	public final void testAddTrackLast() {
		final List<Track> tracks = new ArrayList<>(factory.getRandomSetOfTracks());
		final String disc = "disc";

		for(Track track : tracks) {
			guineaPig.addTrackLast(track, disc);
		}
		final Collection<Track> actual = guineaPig.getTracks()
				.stream()
				.map(TrackSlot::getTrack)
				.collect(Collectors.toList());
		assertThat(actual).as("Tracks are not being properly added to last position!").isEqualTo(tracks);
	}

	@Test
	public final void testRelocate() {
		final Track track = factory.getRandomOriginalTrack();
		final String previousDiscName = "cd1";
		final int previousNumber = 1;
		final int nextNumber = previousNumber+1;
		final String nextDiscName = "cd2";
		
		guineaPig.addTrack(track, previousNumber, previousDiscName);
		guineaPig.relocate(previousDiscName, previousNumber, nextDiscName, nextNumber);
		
		final TrackSlot expected = new TrackSlotImpl(nextDiscName, nextNumber, track);
		
		assertThat(guineaPig.getTrack(nextDiscName, nextNumber))
		.as("Reading a relocated track")
		.isEqualTo(expected);
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
		assertThat(guineaPig.getTracksCount()).as("Track counter should count all the album's tracks!").isEqualTo(tracksCD1.size()+tracksCD2.size());
	}

	public final void testEmptyTrackCount() {
		assertThat(guineaPig.getTracksCount()).as("An empty album has always 0 track").isEqualTo(0);
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
		assertThat(guineaPig.getTracksCount("cd1")).as("Track counter should count all the album's tracks!").isEqualTo(tracksCD1.size());
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
		assertThat(guineaPig.getReleaseDate()).as("Release date is not being properly assigned!").isEqualTo(release);
	}


	@Test(expected = IllegalArgumentException.class)
	public final void testNullReleaseDate() {
		guineaPig.setReleaseDate(null);
	}

	@Test
	public final void testDuration() {
		final Random random = new Random();
		final Duration totalDuration = Duration.ZERO;
		
		assumeThat(guineaPig.getDuration(), is(equalTo(Duration.ZERO)));
		
		final Set<Track> tracks = factory.getRandomSetOfTracks();
		for(Track track : tracks) {
			final Duration randomDuration = Duration.ofMillis(random.nextLong());
			track.setDuration(randomDuration);
			totalDuration.plus(randomDuration);
			guineaPig.addTrackLast(track, "disc");
		}

		assertThat(guineaPig.getDuration().toJavaUtil())
		.isNotEmpty()
		.contains(totalDuration);
	}

	@Test
	public final void testAllGenres() {
		final Collection<Genre> albumGenres = guineaPig.getGenres();
		final Set<Genre> allTrackGenres = Sets.newLinkedHashSet();

		for(final Track track : factory.getRandomSetOfTracks()){
			final Set<Genre> trackGenres = factory.getRandomSetOfGenres();
			track.setGenres(trackGenres);
			allTrackGenres.addAll(trackGenres);
			guineaPig.addTrackLast(track, "disc");
		}
		
		final Set<Genre> allGenres = ImmutableSet.<Genre>builder()
				.addAll(allTrackGenres)
				.addAll(albumGenres)
				.build();
		
		assertThat(guineaPig.getAllGenres())
		.as("All Genres should include genres from the album and from tracks")
		.containsAll(albumGenres)
		.containsAll(allTrackGenres)
		.containsExactlyInAnyOrderElementsOf(allGenres);
	}

	@Test
	public final void testGenres() {
		TestUtils.testGenres(factory, guineaPig);
	}

	@Test
	public final void testDiscCount() {
		guineaPig.addTrackLast(factory.getRandomOriginalTrack(), "dics1");
		guineaPig.addTrackLast(factory.getRandomOriginalTrack(), "dics2");
		assertThat(guineaPig.getDiscCount()).as("Unexpected number of discs").isEqualTo(2);
	}

	@Test
	public final void testDiscs() {
		final Set<String> discs = Sets.newLinkedHashSet(Arrays.asList("disc1", "disc2"));
		guineaPig.addTrackLast(factory.getRandomOriginalTrack(), "disc1");
		guineaPig.addTrackLast(factory.getRandomOriginalTrack(), "disc2");
		assertThat(guineaPig.getDiscs()).as("Discs do not match").isEqualTo(discs);
	}

	@Test
	public final void testType() {
		guineaPig = factories.getAlbumFactory()
				.createSingleArtistAlbum("Album", TypeRelease.BESTOF, factory.getRandomSetOfArtists());
		assertThat(guineaPig.getReleaseType()).as("Type is not being properly assigned!").isEqualTo(TypeRelease.BESTOF);
	}

	@Test
	public final void testEqualsObject() {
		final AlbumFactory albumFactory = factories.getAlbumFactory();
		final String title = "album";
		final TypeRelease release = TypeRelease.DELUXE;
		final Set<Artist> artists = factory.getRandomSetOfArtists();
		
		final Supplier<Album> albumSup = () -> albumFactory
				.createSingleArtistAlbum(title, release, artists);
		final Supplier<Album> albumDiffArtistsSup = () -> albumFactory
				.createSingleArtistAlbum(title, release, factory.getRandomSetOfArtists());
		final Supplier<Album> albumDiffTitle = () -> albumFactory
				.createSingleArtistAlbum(StringUtils.reverse(title), release, artists);
		final Supplier<Album> albumDiffRelease = () -> albumFactory
				.createSingleArtistAlbum(title, TypeRelease.LIMITED, artists);
		
		final Album album = albumSup.get();
		
		assertThat(album.hashCode())
		.isEqualTo(albumSup.get().hashCode());
		
		assertThat(album)
		.isEqualTo(albumSup.get());
		
		assertEqualsAndHashCode(albumDiffArtistsSup, album, "artist");
		assertEqualsAndHashCode(albumDiffTitle, album, "title");
		assertEqualsAndHashCode(albumDiffRelease, album, "release type");
	}

	private void assertEqualsAndHashCode(final Supplier<Album> actual, final Album expected,
			final String fieldName) {
		
		assertThat(expected.hashCode())
		.as("Hash code should take %s into consideration", fieldName)
		.isNotEqualTo(actual.get().hashCode());
		
		assertThat(expected)
		.as("Equals should take %s into consideration", fieldName)
		.isNotEqualTo(actual.get());
	} 

	@Test
	public final void testCover() throws IOException {
		final Path birds = Resources.RESOURCES_TEST.resolve("birds.jpg"); 
		byte[] cover = Files.readAllBytes(birds);
		byte[] actual = new byte[cover.length];
		guineaPig.setCover(cover);
		guineaPig.getCover().toInput().read(actual);
		assertThat(actual).as("Cover not being properly set!").isEqualTo(cover);
	}

	@Test
	public final void testCompareTo() {
		final AlbumFactory factory = factories.getAlbumFactory();
		testCompareTo(AlbumFieldTest.factory.getRandomAlbum());
		testCompareTo(factory.createVAAlbum("someRandomTitle", TypeRelease.BESTOF));
	}

	private void testCompareTo(Album album) {
		assertThat(guineaPig.compareTo(album)).isEqualTo(guineaPig.getTitle().compareTo(album.getTitle()));
	}

	@Override
	protected Album createGuineaPig() {
		return factory.getRandomAlbum();
	}

}
