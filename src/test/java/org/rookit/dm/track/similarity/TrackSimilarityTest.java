package org.rookit.dm.track.similarity;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.track.Track;
import org.rookit.api.dm.track.TypeVersion;
import org.rookit.api.dm.track.VersionTrack;
import org.rookit.api.dm.track.factory.TrackFactory;
import org.rookit.dm.test.DMTestFactory;
import org.rookit.dm.utils.TestUtils;

import com.google.common.collect.Sets;
import com.google.inject.Injector;

@SuppressWarnings("javadoc")
public class TrackSimilarityTest {

	private static DMTestFactory dmFactory;
	private static TrackFactory factory;
	private static TrackComparator comparator;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		final Injector injector = TestUtils.getInjector();
		dmFactory = injector.getInstance(DMTestFactory.class);
		factory = injector.getInstance(TrackFactory.class);
		comparator = new TrackComparator();
	}
	
	@Test
	public final void sameTrackShouldBe0() {
		final Track track = dmFactory.getRandomTrack("just a title");
		
		assertEquals(0, comparator.similarity(track, track), 0);
	}
	
	@Test
	public final void differentTitlesShouldNotBe0() {
		final Set<Artist> mainArtist = Sets.newHashSet(dmFactory.getRandomArtist()); 
		final String title1 = "One More Drink";
		final Track t1 = factory.createOriginalTrack(title1);
		final Track t11 = factory.createOriginalTrack(title1);
		final Track t2 = factory.createOriginalTrack("White Out");
		t1.setMainArtists(mainArtist);
		t11.setMainArtists(mainArtist);
		t2.setMainArtists(mainArtist);
		
		assertEquals(0, comparator.similarity(t1, t11), 0);
		assertNotEquals(0, comparator.similarity(t1, t2));
	}
	
	@Test
	public final void differentVersionArtistsShouldNotBe0() {
		final TypeVersion version = TypeVersion.ACOUSTIC;
		final Set<Artist> a1 = dmFactory.getRandomSetOfArtists();
		final Set<Artist> a2 = dmFactory.getRandomUniqueSetOfArtists(a1);
		final Track original = dmFactory.getRandomOriginalTrack();
		final VersionTrack v1 = factory.createVersionTrack(version, original);
		final VersionTrack v11 = factory.createVersionTrack(version, original);
		final VersionTrack v2 = factory.createVersionTrack(version, original);
		v1.setVersionArtists(a1);
		v11.setVersionArtists(a1);
		v2.setVersionArtists(a2);
		
		assertEquals(0, comparator.similarity(v1, v11), 0);
		assertNotEquals(0, comparator.similarity(v1, v2));
	}
	
}
