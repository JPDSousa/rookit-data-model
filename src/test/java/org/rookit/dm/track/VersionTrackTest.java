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
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.track.Track;
import org.rookit.api.dm.track.TrackTitle;
import org.rookit.api.dm.track.TypeTrack;
import org.rookit.api.dm.track.TypeVersion;
import org.rookit.api.dm.track.VersionTrack;
import org.rookit.api.dm.track.factory.TrackFactory;
import org.rookit.dm.test.DMTestFactory;
import org.rookit.dm.utils.TestUtils;

import com.google.inject.Injector;

@SuppressWarnings("javadoc")
public class VersionTrackTest {

	private static TrackFactory trackFactory;
	private static DMTestFactory factory;
	
	private VersionTrack guineaPig;
	private Track original;
	private TypeVersion version;
	
	@BeforeClass
	public static final void setUpBeforeClass() {
		final Injector injector = TestUtils.getInjector();
		trackFactory = injector.getInstance(TrackFactory.class);
		factory = injector.getInstance(DMTestFactory.class);
	}

	@Before
	public void createTrack() {
		version = factory.getRandomVersionType();
		original = factory.getRandomOriginalTrack();
		guineaPig = trackFactory.createVersionTrack(version, original);
	}
	
	@Test
	public final void testVersionType() {
		final Track orTrack = factory.getRandomOriginalTrack();
		final TypeVersion version = TypeVersion.ACOUSTIC;
		final VersionTrack track = trackFactory.createVersionTrack(version, orTrack);
		assertEquals(version, track.getVersionType());
	}
	
	@Test
	public final void testVersionArtists() {
		final Artist artist = factory.getRandomArtist();
		assertTrue(IterableUtils.isEmpty(guineaPig.getVersionArtists()));
		guineaPig.addVersionArtist(artist);
		assertTrue(IterableUtils.contains(guineaPig.getVersionArtists(), artist));
		assertEquals(1, IterableUtils.size(guineaPig.getVersionArtists()));
		guineaPig.addVersionArtist(artist);
		assertEquals(1, IterableUtils.size(guineaPig.getVersionArtists()));
	}
	
	@Test
	public final void testVersionToken() {
		final String versionToken = "some random token";
		assertEquals("", guineaPig.getVersionToken());
		guineaPig.setVersionToken(versionToken);
		assertEquals(versionToken, guineaPig.getVersionToken());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testNullVersionToken() {
		guineaPig.setVersionToken(null);
	}
	
	@Test
	public final void testFullTitle() {
		// TODO requires further testing
		assertNotNull(guineaPig.getFullTitle());
	}
	
	@Test
	public final void testLongFullTitle() {
		// TODO requires further testing
		assertNotNull(guineaPig.getLongFullTitle());
	}
	
	@Test
	public final void testTitle() {
		final String title = factory.randomString();
		assertEquals(original.getTitle(), guineaPig.getTitle());
		guineaPig.setTitle(title);
		assertEquals(title, guineaPig.getTitle().toString());
		assertEquals(original.getTitle(), guineaPig.getTitle());
		guineaPig.setTitle(new TrackTitle(title));
		assertEquals(title, guineaPig.getTitle().toString());
		assertEquals(original.getTitle(), guineaPig.getTitle());
	}
	
	@Test
	public final void testMainArtists() {
		assertEquals(original.getMainArtists(), guineaPig.getMainArtists());
		
		final Set<Artist> artists = factory.getRandomSetOfArtists();
		guineaPig.setMainArtists(artists);
		assertEquals(artists, guineaPig.getMainArtists());
		assertEquals(artists, original.getMainArtists());
		
		final Artist artist = factory.getRandomArtist();
		guineaPig.addMainArtist(artist);
		assertEquals(original.getMainArtists(), guineaPig.getMainArtists());
		assertTrue(original.getMainArtists().contains(artist));
	}
	
	@Test
	public final void testFeatures() {
		final Set<Artist> artists = factory.getRandomSetOfArtists();
		assertEquals(original.getFeatures(), guineaPig.getFeatures());
		guineaPig.setFeatures(artists);
		assertEquals(artists, guineaPig.getFeatures());
		assertEquals(original.getFeatures(), guineaPig.getFeatures());
		guineaPig.addFeature(factory.getRandomArtist());
		// TODO requires further testing
		assertEquals(original.getFeatures(), guineaPig.getFeatures());
	}

	@Test
	public final void testIsVersionTrack() {
		assertTrue(guineaPig.isVersionTrack());
	}
	
	@Test
	public final void testVersionTrack() {
		assertEquals(guineaPig, guineaPig.getAsVersionTrack());
	}
	
	@Test
	public final void testHashCode() {
		guineaPig.hashCode();
	}
	
	@Test
	public final void testEquals() {
		final ObjectId id = guineaPig.getId();
		final TypeVersion version = differentFrom(this.version);
		final VersionTrack differentVersion = trackFactory.createVersionTrack(version, original);
		differentVersion.setId(id);
		final VersionTrack differentArtists = trackFactory.createVersionTrack(this.version, original);
		differentArtists.addVersionArtist(factory.getRandomArtist());
		differentArtists.setId(id);
		final VersionTrack differentOriginal = trackFactory.createVersionTrack(this.version, factory.getRandomOriginalTrack());
		differentOriginal.setId(id);
		final VersionTrack differentToken = trackFactory.createVersionTrack(this.version, original);
		differentToken.setVersionToken("some different token");
		differentToken.setId(id);
		
		assertEquals(guineaPig, guineaPig);
		assertNotEquals(guineaPig, factory.getRandomTrack(TypeTrack.ORIGINAL));
		assertNotEquals(guineaPig, original);
		assertNotEquals(guineaPig, differentVersion);
		assertNotEquals(guineaPig, differentArtists);
		assertNotEquals(guineaPig, differentOriginal);
		assertNotEquals(guineaPig, differentToken);
	}
	
	private final TypeVersion differentFrom(TypeVersion version) {
		for(TypeVersion v : TypeVersion.values()) {
			if(!v.equals(version)) {
				return v;
			}
		}
		return null;
	}
}
