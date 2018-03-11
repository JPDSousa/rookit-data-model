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

import static org.assertj.core.api.Assertions.*;

import java.util.Set;

import org.apache.commons.collections4.IterableUtils;
import org.bson.types.ObjectId;
import org.junit.BeforeClass;
import org.junit.Test;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.track.Track;
import org.rookit.api.dm.track.TrackTitle;
import org.rookit.api.dm.track.TypeTrack;
import org.rookit.api.dm.track.TypeVersion;
import org.rookit.api.dm.track.factory.TrackFactory;
import org.rookit.dm.test.DMTestFactory;
import org.rookit.dm.utils.TestUtils;
import org.rookit.test.AbstractTest;

import com.google.common.collect.Sets;
import com.google.inject.Injector;

@SuppressWarnings("javadoc")
public class TrackFieldsTest extends AbstractTest<Track> {

	private static TrackFactory trackFactory;
	private static DMTestFactory factory;
	
	@BeforeClass
	public static final void setUpBeforeClass() {
		final Injector injector = TestUtils.getInjector();
		trackFactory = injector.getInstance(TrackFactory.class);
		factory = injector.getInstance(DMTestFactory.class);
	}

	@Test
	public final void testTitle() {
		final TrackTitle testTitle = new TrackTitle(factory.randomString());
		guineaPig.setTitle(testTitle.getTitle());
		assertThat(guineaPig.getTitle()).isEqualTo(testTitle);
	}
	
	@Test
	public final void testTrackTitle() {
		TrackTitle testTitle = new TrackTitle(factory.randomString());
		guineaPig.setTitle(testTitle);
		assertThat(guineaPig.getTitle()).isEqualTo(testTitle);
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
		assertThat(guineaPig.getLongFullTitle()).isNotNull();
	}
	
	@Test
	public final void testFullTitle() {
		assertThat(guineaPig.getFullTitle()).isNotNull();
	}
	
	@Test
	public final void testMainArtists() {
		Set<Artist> artists = factory.getRandomSetOfArtists();
		guineaPig.setMainArtists(artists);
		assertThat(guineaPig.getMainArtists()).as("Main artists are not being assigned!").isEqualTo(artists);
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
		assertThat(IterableUtils.contains(guineaPig.getMainArtists(), artist)).isTrue();
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
		assertThat(guineaPig.getFeatures()).isEqualTo(artists);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testNullFeatures() {
		guineaPig.setFeatures(null);
	}
	
	@Test
	public final void testAddFeature() {
		final Artist artist = factory.getRandomArtist();
		guineaPig.addFeature(artist);
		assertThat(IterableUtils.contains(guineaPig.getFeatures(), artist)).isTrue();
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
		assertThat(guineaPig.isExplicit()).isTrue();
		guineaPig.setExplicit(false);
		assertThat(guineaPig.isExplicit()).isFalse();
	}
	
	@Test
	public final void testTrackType() {
		for(final TypeTrack type : TypeTrack.values()) {
			assertThat(factory.getRandomTrack(type).getType()).isEqualTo(type);
		}
	}
	
	@Test
	public final void testGetPath() {
		assertThat(guineaPig.getPath()).isNotNull();
	}

	@Test
	public final void testSetHiddenTrack() {
		final String hiddenTrack = factory.randomString();
		guineaPig.setHiddenTrack(hiddenTrack);
		assertThat(guineaPig.getHiddenTrack())
		.isNotEmpty()
		.get()
		.isEqualTo(hiddenTrack);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testSetNullHiddenTrack() {
		guineaPig.setHiddenTrack(null);
	}
	
	@Test
	public final void testSetEmptyHiddenTrackFails() {
		assertThatThrownBy(() -> guineaPig.setHiddenTrack(""))
		.as("Setting an empty hidden track")
		.isInstanceOf(IllegalArgumentException.class);
	}
	
	@Test
	public final void testGetHiddenTrackNotNull() {
		assertThat(guineaPig.getHiddenTrack()).isNotNull();
	}

	@Test
	public final void testProducers() {
		Set<Artist> artists = factory.getRandomSetOfArtists();
		guineaPig.setProducers(artists);
		assertThat(guineaPig.getProducers()).isEqualTo(artists);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testNullProducers() {
		guineaPig.setProducers(null);
	}
	
	@Test
	public final void testAddProducer() {
		final Artist artist = factory.getRandomArtist();
		guineaPig.addProducer(artist);
		assertThat(IterableUtils.contains(guineaPig.getProducers(), artist)).isTrue();
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
		final Track track = trackFactory.createVersionTrack(TypeVersion.ALTERNATIVE, guineaPig);
		assertThat(track.getAsVersionTrack().get()).isEqualTo(track);
	}
	
	public final void testOriginalGetAsVersionTrack() {
		final Track track = trackFactory.createOriginalTrack(factory.randomString());
		assertThat(track.getAsVersionTrack())
		.as("An original track fetched as version")
		.isEmpty();
	}
	
	@Test
	public final void testIsVersionTrack() {
		final Track track = trackFactory.createVersionTrack(TypeVersion.ALTERNATIVE, guineaPig);
		assertThat(track.isVersionTrack()).isTrue();
	}
	
	@Test
	public final void testOriginalIsVersionTrack() {
		final Track track = trackFactory.createOriginalTrack(factory.randomString());
		assertThat(track.isVersionTrack()).isFalse();
	}
	
	@Test
	public final void testBpm() {
		final short bpm = 140;
		guineaPig.setBPM(bpm);
		assertThat(guineaPig.getBPM())
		.isNotEmpty()
		.get()
		.isEqualTo(bpm);
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
		assertThat(guineaPig.getLyrics())
		.isNotEmpty()
		.get()
		.isEqualTo(lyrics);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testNullLyrics() {
		guineaPig.setLyrics(null);
	}
	
	@Test
	public final void testEmptyLyrics() {
		assertThatThrownBy(() -> guineaPig.setLyrics(""))
		.as("Invalid lyrics")
		.isInstanceOf(IllegalArgumentException.class)
		.hasMessageContaining("empty");
	}
	
	@Test
	public final void testGenres() {
		TestUtils.testGenres(factory, guineaPig);
	}
	
	@Test
	public final void testAllGenres() {
		final Set<Genre> genres = factory.getRandomSetOfGenres();
		guineaPig.setGenres(genres);
		assertThat(guineaPig.getAllGenres())
		.containsExactlyInAnyOrderElementsOf(genres);
	}
	
	@Test
	public final void testEquals() {
		final Track track1 = factory.getRandomTrack(TypeTrack.ORIGINAL);
		final Track track2 = factory.getRandomTrack(TypeTrack.VERSION);
		
		assertThat(track1)
		.isEqualTo(track1)
		.isNotEqualTo(factory.getRandomArtist())
		.isNotEqualTo(track2);
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
		assertThat(track2).isEqualTo(track1);
	}
	
	@Test
	public final void testEqualsByType() {
		final Track track1 = trackFactory.createOriginalTrack(factory.randomString());
		final Track track2 = trackFactory.createVersionTrack(TypeVersion.ACOUSTIC, track1);
		assertThat(track1.equals(track2)).isFalse();
	}	
	
	@Test
	public final void testEqualsOriginalTrack() {
		final String title = factory.randomString();
		final Track track1 = trackFactory.createOriginalTrack(title);
		final Track track2 = trackFactory.createOriginalTrack(title);
		final Set<Artist> mainArtists = factory.getRandomSetOfArtists();
		
		track2.setId(track1.getId());
		assertThat(track2)
		.isEqualTo(track1);
		
		track1.setMainArtists(mainArtists);
		assertThat(track1)
		.isNotEqualTo(track2);
		
		track2.setMainArtists(mainArtists);
		assertThat(track2)
		.isEqualTo(track1);
		
		track1.setFeatures(factory.getRandomSetOfArtists());
		assertThat(track2)
		.isEqualTo(track1);
		
		track2.setFeatures(factory.getRandomSetOfArtists());
		assertThat(track2)
		.isEqualTo(track1);
	}
	
	@Test
	public final void testEqualsVersionTrack() {
		final Track original = trackFactory.createOriginalTrack(factory.randomString());
		final TypeVersion version = TypeVersion.LIVE;
		final Track track1 = trackFactory.createVersionTrack(version, original);
		final Track track2 = trackFactory.createVersionTrack(version, original);
		track1.setId(track2.getId());
		assertThat(track2).isEqualTo(track1);
	}
	
	@Test
	public final void testCompareTo() {
		testCompareTo(guineaPig);
		testCompareTo(factory.getRandomOriginalTrack());
		testCompareTo(factory.getRandomTrack("someRandomTrack"));
	}
	
	private void testCompareTo(Track track) {
		assertThat(guineaPig.compareTo(track)).isEqualTo(guineaPig.getTitle().toString().compareTo(track.getTitle().toString()));
	}

	@Override
	protected Track createGuineaPig() {
		return factory.getRandomOriginalTrack();
	}
}
