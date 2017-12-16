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

import static org.junit.Assert.*;

import java.util.Set;

import org.apache.commons.collections4.IterableUtils;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.rookit.dm.artist.Artist;
import org.rookit.dm.genre.Genre;
import org.rookit.dm.test.DMTestFactory;
import org.rookit.dm.track.Track;
import org.rookit.dm.track.TrackFactory;
import org.rookit.dm.track.TrackTitle;
import org.rookit.dm.track.TypeTrack;
import org.rookit.dm.track.TypeVersion;
import org.rookit.dm.utils.TestUtils;
import org.rookit.utils.exception.InvalidOperationException;

import com.google.common.collect.Sets;

@SuppressWarnings("javadoc")
public class TrackFieldsTest {

	private Track guineaPig;
	private static DMTestFactory factory;
	private static TrackFactory trackFactory;

	@BeforeClass
	public static void initialize() {
		factory = DMTestFactory.getDefault();
		trackFactory = TrackFactory.getDefault();
	}

	@Before
	public final void createTrack() {
		guineaPig = factory.getRandomOriginalTrack();
	}

	@Test
	public final void testTitle() {
		TrackTitle testTitle = new TrackTitle(factory.randomString());
		guineaPig.setTitle(testTitle.getTitle());
		assertEquals(testTitle, guineaPig.getTitle());
	}
	
	@Test
	public final void testTrackTitle() {
		TrackTitle testTitle = new TrackTitle(factory.randomString());
		guineaPig.setTitle(testTitle);
		assertEquals(testTitle, guineaPig.getTitle());
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testEmptyTrackTitle(){
		guineaPig.setTitle("");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testNullTrackTitleString() {
		guineaPig.setTitle((String) null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testNullTrackTitle() {
		guineaPig.setTitle((TrackTitle) null);
	}

	@Test
	public final void testLongFullTitle() {
		assertNotNull(guineaPig.getLongFullTitle());
	}
	
	@Test
	public final void testFullTitle() {
		assertNotNull(guineaPig.getFullTitle());
	}
	
	@Test
	public final void testMainArtists() {
		Set<Artist> artists = factory.getRandomSetOfArtists();
		guineaPig.setMainArtists(artists);
		assertEquals("Main artists are not being assigned!", artists, guineaPig.getMainArtists());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testNullMainArtists() {
		guineaPig.setMainArtists(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testEmptyMainArtists() {
		guineaPig.setMainArtists(Sets.newLinkedHashSet());
	}
	
	@Test
	public final void testAddMainArtist() {
		final Artist artist = factory.getRandomArtist();
		guineaPig.addMainArtist(artist);
		assertTrue(IterableUtils.contains(guineaPig.getMainArtists(), artist));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testAddNullMainArtists() {
		guineaPig.addMainArtist(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testAddAlreadyFeatureAsMainArtist() {
		final Artist artist = factory.getRandomArtist();
		guineaPig.addFeature(artist);
		guineaPig.addMainArtist(artist);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testAddAlreadyProducerAsMainArtist() {
		final Artist artist = factory.getRandomArtist();
		guineaPig.addProducer(artist);
		guineaPig.addMainArtist(artist);
	}

	@Test
	public final void testFeatures() {
		Set<Artist> artists = factory.getRandomSetOfArtists();
		guineaPig.setFeatures(artists);
		assertEquals(artists, guineaPig.getFeatures());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testNullFeatures() {
		guineaPig.setFeatures(null);
	}
	
	@Test
	public final void testAddFeature() {
		final Artist artist = factory.getRandomArtist();
		guineaPig.addFeature(artist);
		assertTrue(IterableUtils.contains(guineaPig.getFeatures(), artist));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testAddNullFeature() {
		guineaPig.addFeature(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testAddAlreadyMainArtistAsFeature() {
		final Artist artist = factory.getRandomArtist();
		guineaPig.addMainArtist(artist);
		guineaPig.addFeature(artist);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testAddAlreadyProducerAsFeature() {
		final Artist artist = factory.getRandomArtist();
		guineaPig.addProducer(artist);
		guineaPig.addFeature(artist);
	}
	
	@Test
	public final void testPlayable() {
		TestUtils.testPlayable(guineaPig);
	}
	
	@Test
	public final void testExplicit() {
		guineaPig.setExplicit(true);
		assertTrue(guineaPig.isExplicit());
		guineaPig.setExplicit(false);
		assertFalse(guineaPig.isExplicit());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testExplicitNull() {
		guineaPig.setExplicit(null);
	}
	
	@Test
	public final void testTrackType() {
		for(TypeTrack type : TypeTrack.values()) {
			assertEquals(type, factory.getRandomTrack(type).getType());
		}
	}
	
	@Test
	public final void testToString() {
		assertEquals(guineaPig.getLongFullTitle().toString(), guineaPig.toString());
	}
	
	@Test
	public final void testGetPath() {
		assertNotNull(guineaPig.getPath());
	}

	@Test
	public final void testSetHiddenTrack() {
		final String hiddenTrack = factory.randomString();
		guineaPig.setHiddenTrack(hiddenTrack);
		assertEquals(hiddenTrack, guineaPig.getHiddenTrack());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testSetNullHiddenTrack() {
		guineaPig.setHiddenTrack(null);
	}
	
	@Test
	public final void testSetEmptyHiddenTrack() {
		guineaPig.setHiddenTrack("");
	}
	
	@Test
	public final void testGetHiddenTrackNotNull() {
		assertNotNull(guineaPig.getHiddenTrack());
	}

	@Test
	public final void testProducers() {
		Set<Artist> artists = factory.getRandomSetOfArtists();
		guineaPig.setProducers(artists);
		assertEquals(artists, guineaPig.getProducers());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testNullProducers() {
		guineaPig.setProducers(null);
	}
	
	@Test
	public final void testAddProducer() {
		final Artist artist = factory.getRandomArtist();
		guineaPig.addProducer(artist);
		assertTrue(IterableUtils.contains(guineaPig.getProducers(), artist));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testAddNullProducer() {
		guineaPig.addProducer(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testAddAlreadyMainArtistAsProducer() {
		final Artist artist = factory.getRandomArtist();
		guineaPig.addMainArtist(artist);
		guineaPig.addProducer(artist);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testAddAlreadyFeatureAsProducer() {
		final Artist artist = factory.getRandomArtist();
		guineaPig.addFeature(artist);
		guineaPig.addProducer(artist);
	}
	
	@Test
	public final void testGetAsVersionTrack() {
		final Track track = TrackFactory.getDefault().createVersionTrack(TypeVersion.ALTERNATIVE, guineaPig);
		assertEquals(track, track.getAsVersionTrack());
	}
	
	@Test(expected = InvalidOperationException.class)
	public final void testOriginalGetAsVersionTrack() {
		final Track track = TrackFactory.getDefault().createOriginalTrack(factory.randomString());
		track.getAsVersionTrack();
	}
	
	@Test
	public final void testIsVersionTrack() {
		final Track track = TrackFactory.getDefault().createVersionTrack(TypeVersion.ALTERNATIVE, guineaPig);
		assertTrue(track.isVersionTrack());
	}
	
	@Test
	public final void testOriginalIsVersionTrack() {
		final Track track = TrackFactory.getDefault().createOriginalTrack(factory.randomString());
		assertFalse(track.isVersionTrack());
	}
	
	@Test
	public final void testBpm() {
		final short bpm = 140;
		guineaPig.setBPM(bpm);
		assertEquals(bpm, guineaPig.getBPM());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testNegativeBpm() {
		guineaPig.setBPM((short) -100);
	}
	
	
	@Test
	public final void testZeroBpm() {
		guineaPig.setBPM((short) 0);
	}
	
	@Test
	public final void testLyrics() {
		final String lyrics = factory.randomString();
		guineaPig.setLyrics(lyrics);
		assertEquals(lyrics, guineaPig.getLyrics());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testNullLyrics() {
		guineaPig.setLyrics(null);
	}
	
	@Test
	public final void testEmptyLyrics() {
		guineaPig.setLyrics("");
	}
	
	@Test
	public final void testGenres() {
		TestUtils.testGenres(guineaPig);
	}
	
	@Test
	public final void testAllGenres() {
		final Set<Genre> genres = factory.getRandomSetOfGenres();
		guineaPig.setGenres(genres);
		assertEquals(genres, guineaPig.getAllGenres());
	}
	
	@Test
	public final void testEquals() {
		final Track track1 = factory.getRandomTrack(TypeTrack.ORIGINAL);
		final Track track2 = factory.getRandomTrack(TypeTrack.VERSION);
		assertEquals(track1, track1);
		assertNotEquals(track1, factory.getRandomArtist());
		assertNotEquals(track1, track2);
	}

	@Test
	public final void testEqualsById() {
		final String title = factory.randomString();
		final Set<Artist> mainArtists = factory.getRandomSetOfArtists();
		final Set<Artist> features = Sets.newLinkedHashSet();
		final Set<Genre> genres = factory.getRandomSetOfGenres();
		final Track track1 = trackFactory.createOriginalTrack(title);
		final Track track2 = trackFactory.createOriginalTrack(title);
		track1.setMainArtists(mainArtists);
		track2.setMainArtists(mainArtists);
		track1.setFeatures(features);
		track2.setFeatures(features);
		track1.setGenres(genres);
		track2.setGenres(genres);
		final ObjectId id = new ObjectId();
		
		track1.setId(id);
		track2.setId(id);
		assertEquals(track1, track2);
	}
	
	@Test
	public final void testEqualsByType() {
		final TrackFactory trackFactory = TrackFactory.getDefault();
		
		final Track track1 = trackFactory.createOriginalTrack(factory.randomString());
		final Track track2 = trackFactory.createVersionTrack(TypeVersion.ACOUSTIC, track1);
		assertFalse(track1.equals(track2));
	}	
	
	@Test
	public final void testEqualsOriginalTrack() {
		final TrackFactory trackFactory = TrackFactory.getDefault();
		final String title = factory.randomString();
		final Track track1 = trackFactory.createOriginalTrack(title);
		final Track track2 = trackFactory.createOriginalTrack(title);
		final Set<Artist> mainArtists = factory.getRandomSetOfArtists();
		
		track2.setId(track1.getId());
		assertEquals(track1, track2);
		track1.setMainArtists(mainArtists);
		assertNotEquals(track1, track2);
		track2.setMainArtists(mainArtists);
		assertEquals(track1, track2);
		track1.setFeatures(factory.getRandomSetOfArtists());
		assertEquals(track1, track2);
		track2.setFeatures(factory.getRandomSetOfArtists());
		assertEquals(track1, track2);
	}
	
	@Test
	public final void testEqualsVersionTrack() {
		final TrackFactory trackFactory = TrackFactory.getDefault();
		final Track original = trackFactory.createOriginalTrack(factory.randomString());
		final TypeVersion version = TypeVersion.LIVE;
		final Track track1 = trackFactory.createVersionTrack(version, original);
		final Track track2 = trackFactory.createVersionTrack(version, original);
		track1.setId(track2.getId());
		assertEquals(track1, track2);
	}
	
	@Test
	public final void testCompareTo() {
		testCompareTo(guineaPig);
		testCompareTo(factory.getRandomOriginalTrack());
		testCompareTo(factory.getRandomTrack("someRandomTrack"));
	}
	
	private void testCompareTo(Track track) {
		assertEquals(guineaPig.getTitle().toString().compareTo(track.getTitle().toString()), guineaPig.compareTo(track));
	}
}
