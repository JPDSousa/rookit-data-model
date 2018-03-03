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
import org.rookit.api.dm.track.Track;
import org.rookit.api.dm.track.TrackTitle;
import org.rookit.api.dm.track.TypeTrack;
import org.rookit.api.dm.track.TypeVersion;
import org.rookit.api.dm.track.VersionTrack;
import org.rookit.api.dm.track.factory.TrackFactory;
import org.rookit.dm.test.DMTestFactory;
import org.rookit.dm.utils.TestUtils;
import org.rookit.test.AbstractTest;

import com.google.inject.Injector;

@SuppressWarnings("javadoc")
public class VersionTrackTest extends AbstractTest<VersionTrack> {

	private static TrackFactory trackFactory;
	private static DMTestFactory factory;
	
	private Track original;
	private TypeVersion version;
	
	@BeforeClass
	public static final void setUpBeforeClass() {
		final Injector injector = TestUtils.getInjector();
		trackFactory = injector.getInstance(TrackFactory.class);
		factory = injector.getInstance(DMTestFactory.class);
	}
	
	@Test
	public final void testVersionType() {
		final Track orTrack = factory.getRandomOriginalTrack();
		final TypeVersion version = TypeVersion.ACOUSTIC;
		final VersionTrack track = trackFactory.createVersionTrack(version, orTrack);
		assertThat(track.getVersionType()).isEqualTo(version);
	}
	
	@Test
	public final void testVersionArtists() {
		final Artist artist = factory.getRandomArtist();
		assertThat(IterableUtils.isEmpty(guineaPig.getVersionArtists())).isTrue();
		guineaPig.addVersionArtist(artist);
		assertThat(IterableUtils.contains(guineaPig.getVersionArtists(), artist)).isTrue();
		assertThat(IterableUtils.size(guineaPig.getVersionArtists())).isEqualTo(1);
		guineaPig.addVersionArtist(artist);
		assertThat(IterableUtils.size(guineaPig.getVersionArtists())).isEqualTo(1);
	}
	
	@Test
	public final void testVersionToken() {
		final String versionToken = "some random token";
		assertThat(guineaPig.getVersionToken()).isEqualTo("");
		guineaPig.setVersionToken(versionToken);
		assertThat(guineaPig.getVersionToken()).isEqualTo(versionToken);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testNullVersionToken() {
		guineaPig.setVersionToken(null);
	}
	
	@Test
	public final void testFullTitle() {
		// TODO requires further testing
		assertThat(guineaPig.getFullTitle()).isNotNull();
	}
	
	@Test
	public final void testLongFullTitle() {
		// TODO requires further testing
		assertThat(guineaPig.getLongFullTitle()).isNotNull();
	}
	
	@Test
	public final void testTitle() {
		final String title = factory.randomString();
		assertThat(guineaPig.getTitle()).isEqualTo(original.getTitle());
		guineaPig.setTitle(title);
		assertThat(guineaPig.getTitle().toString()).isEqualTo(title);
		assertThat(guineaPig.getTitle()).isEqualTo(original.getTitle());
		guineaPig.setTitle(new TrackTitle(title));
		assertThat(guineaPig.getTitle().toString()).isEqualTo(title);
		assertThat(guineaPig.getTitle()).isEqualTo(original.getTitle());
	}
	
	@Test
	public final void testMainArtists() {
		assertThat(guineaPig.getMainArtists()).isEqualTo(original.getMainArtists());
		
		final Set<Artist> artists = factory.getRandomSetOfArtists();
		guineaPig.setMainArtists(artists);
		assertThat(guineaPig.getMainArtists()).isEqualTo(artists);
		assertThat(original.getMainArtists()).isEqualTo(artists);
		
		final Artist artist = factory.getRandomArtist();
		guineaPig.addMainArtist(artist);
		assertThat(guineaPig.getMainArtists()).isEqualTo(original.getMainArtists());
		assertThat(original.getMainArtists().contains(artist)).isTrue();
	}
	
	@Test
	public final void testFeatures() {
		final Set<Artist> artists = factory.getRandomSetOfArtists();
		assertThat(guineaPig.getFeatures()).isEqualTo(original.getFeatures());
		guineaPig.setFeatures(artists);
		assertThat(guineaPig.getFeatures()).isEqualTo(artists);
		assertThat(guineaPig.getFeatures()).isEqualTo(original.getFeatures());
		guineaPig.addFeature(factory.getRandomArtist());
		// TODO requires further testing
		assertThat(guineaPig.getFeatures()).isEqualTo(original.getFeatures());
	}

	@Test
	public final void testIsVersionTrack() {
		assertThat(guineaPig.isVersionTrack()).isTrue();
	}
	
	@Test
	public final void testVersionTrack() {
		assertThat(guineaPig.getAsVersionTrack().toJavaUtil())
		.isNotEmpty()
		.get()
		.isEqualTo(guineaPig);
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
		
		assertThat(guineaPig)
		.isEqualTo(guineaPig)
		.isNotEqualTo(factory.getRandomTrack(TypeTrack.ORIGINAL))
		.isNotEqualTo(original)
		.isNotEqualTo(differentVersion)
		.isNotEqualTo(differentToken)
		.isNotEqualTo(differentOriginal)
		.isNotEqualTo(differentArtists);
	}
	
	private final TypeVersion differentFrom(TypeVersion version) {
		for(TypeVersion v : TypeVersion.values()) {
			if(!v.equals(version)) {
				return v;
			}
		}
		return null;
	}

	@Override
	protected VersionTrack createGuineaPig() {
		version = factory.getRandomVersionType();
		original = factory.getRandomOriginalTrack();
		return trackFactory.createVersionTrack(version, original);
	}
}
