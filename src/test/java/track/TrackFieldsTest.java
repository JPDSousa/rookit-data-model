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
package track;

import static org.junit.Assert.*;

import java.util.Set;

import org.apache.commons.collections4.IterableUtils;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Sets;

import artist.Artist;
import exceptions.InvalidOperationException;
import genre.Genre;
import track.Track;
import track.TrackFactory;
import track.TrackTitle;
import track.TypeVersion;
import utils.CoreFactory;

@SuppressWarnings("javadoc")
public class TrackFieldsTest {

	private Track guineaPig;
	private static CoreFactory factory;

	@BeforeClass
	public static void initialize() {
		factory = CoreFactory.getDefault();
	}

	@Before
	public void createTrack() {
		guineaPig = factory.getRandomTrack();
	}

	@Test
	public void testTitle() {
		TrackTitle testTitle = new TrackTitle(factory.randomString());
		guineaPig.setTitle(testTitle.getTitle());
		assertEquals(testTitle, guineaPig.getTitle());
	}
	
	@Test
	public void testTrackTitle() {
		TrackTitle testTitle = new TrackTitle(factory.randomString());
		guineaPig.setTitle(testTitle);
		assertEquals(testTitle, guineaPig.getTitle());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyTrackTitle(){
		guineaPig.setTitle("");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNullTrackTitleString() {
		guineaPig.setTitle((String) null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNullTrackTitle() {
		guineaPig.setTitle((TrackTitle) null);
	}

	@Test
	public void testLongFullTitle() {
		assertNotNull(guineaPig.getLongFullTitle());
	}
	
	@Test
	public void testFullTitle() {
		assertNotNull(guineaPig.getFullTitle());
	}
	
	@Test
	public void testMainArtists() {
		Set<Artist> artists = factory.getRandomSetOfArtists();
		guineaPig.setMainArtists(artists);
		assertEquals("Main artists are not being assigned!", artists, guineaPig.getMainArtists());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNullMainArtists() {
		guineaPig.setMainArtists(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEmptyMainArtists() {
		guineaPig.setMainArtists(Sets.newLinkedHashSet());
	}
	
	@Test
	public void testAddMainArtist() {
		final Artist artist = factory.getRandomArtist();
		guineaPig.addMainArtist(artist);
		assertTrue(IterableUtils.contains(guineaPig.getMainArtists(), artist));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddNullMainArtists() {
		guineaPig.addMainArtist(null);
	}

	@Test
	public void testFeatures() {
		Set<Artist> artists = factory.getRandomSetOfArtists();
		guineaPig.setFeatures(artists);
		assertEquals(artists, guineaPig.getFeatures());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNullFeatures() {
		guineaPig.setFeatures(null);
	}
	
	@Test
	public void testAddFeature() {
		final Artist artist = factory.getRandomArtist();
		guineaPig.addFeature(artist);
		assertTrue(IterableUtils.contains(guineaPig.getFeatures(), artist));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddNullFeature() {
		guineaPig.addFeature(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddAlreadyMainArtistAsFeature() {
		final Artist artist = factory.getRandomArtist();
		guineaPig.addMainArtist(artist);
		guineaPig.addFeature(artist);
	}
	
	//TODO test getPath

	@Test
	public void testSetHiddenTrack() {
		final String hiddenTrack = factory.randomString();
		guineaPig.setHiddenTrack(hiddenTrack);
		assertEquals(hiddenTrack, guineaPig.getHiddenTrack());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetNullHiddenTrack() {
		guineaPig.setHiddenTrack(null);
	}
	
	@Test
	public void testSetEmptyHiddenTrack() {
		guineaPig.setHiddenTrack("");
	}
	
	@Test
	public void testGetHiddenTrackNotNull() {
		assertNotNull(guineaPig.getHiddenTrack());
	}

	@Test
	public void testProducers() {
		Set<Artist> artists = factory.getRandomSetOfArtists();
		guineaPig.setProducers(artists);
		assertEquals(artists, guineaPig.getProducers());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNullProducers() {
		guineaPig.setProducers(null);
	}
	
	@Test
	public void testAddProducer() {
		final Artist artist = factory.getRandomArtist();
		guineaPig.addProducer(artist);
		assertTrue(IterableUtils.contains(guineaPig.getProducers(), artist));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddNullProducer() {
		guineaPig.addProducer(null);
	}
	
	@Test
	public void testGetAsVersionTrack() {
		final Track track = TrackFactory.getDefault().createVersionTrack(TypeVersion.ALTERNATIVE, guineaPig);
		assertEquals(track, track.getAsVersionTrack());
	}
	
	@Test(expected = InvalidOperationException.class)
	public void testOriginalGetAsVersionTrack() {
		final Track track = TrackFactory.getDefault().createOriginalTrack(factory.randomString());
		track.getAsVersionTrack();
	}
	
	@Test
	public void testIsVersionTrack() {
		final Track track = TrackFactory.getDefault().createVersionTrack(TypeVersion.ALTERNATIVE, guineaPig);
		assertTrue(track.isVersionTrack());
	}
	
	@Test
	public void testOriginalIsVersionTrack() {
		final Track track = TrackFactory.getDefault().createOriginalTrack(factory.randomString());
		assertFalse(track.isVersionTrack());
	}
	
	@Test
	public void testBpm() {
		final short bpm = 140;
		guineaPig.setBPM(bpm);
		assertEquals(bpm, guineaPig.getBPM());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNegativeBpm() {
		guineaPig.setBPM((short) -100);
	}
	
	
	@Test
	public void testZeroBpm() {
		guineaPig.setBPM((short) 0);
	}
	
	@Test
	public void testLyrics() {
		final String lyrics = factory.randomString();
		guineaPig.setLyrics(lyrics);
		assertEquals(lyrics, guineaPig.getLyrics());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNullLyrics() {
		guineaPig.setLyrics(null);
	}
	
	@Test
	public void testEmptyLyrics() {
		guineaPig.setLyrics("");
	}
	
	@Test
	public void testGenres() {
		factory.testGenres(guineaPig);
	}

	@Test
	public void testEqualsById() {
		final String title = factory.randomString();
		final Set<Artist> mainArtists = factory.getRandomSetOfArtists();
		final Set<Artist> features = Sets.newLinkedHashSet();
		final Set<Genre> genres = factory.getRandomSetOfGenres(); 
		final Track track1 = factory.createOriginalTrack(title, mainArtists, features, genres);
		final Track track2 = factory.createOriginalTrack(title, mainArtists, features, genres);
		final ObjectId id = new ObjectId();
		
		track1.setId(id);
		track2.setId(id);
		assertEquals(track1, track2);
		track2.setId(new ObjectId());
		assertNotEquals(track1, track2);
	}
	
	@Test
	public void testEqualsByType() {
		final TrackFactory trackFactory = TrackFactory.getDefault();
		
		final Track track1 = trackFactory.createOriginalTrack(factory.randomString());
		final Track track2 = trackFactory.createVersionTrack(TypeVersion.ACOUSTIC, track1);
		assertFalse(track1.equals(track2));
	}	
	
	@Test
	public void testEqualsOriginalTrack() {
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
	public void testEqualsVersionTrack() {
		final TrackFactory trackFactory = TrackFactory.getDefault();
		final Track original = trackFactory.createOriginalTrack(factory.randomString());
		final TypeVersion version = TypeVersion.LIVE;
		final Track track1 = trackFactory.createVersionTrack(version, original);
		final Track track2 = trackFactory.createVersionTrack(version, original);
		track1.setId(track2.getId());
		assertEquals(track1, track2);
	}
}
