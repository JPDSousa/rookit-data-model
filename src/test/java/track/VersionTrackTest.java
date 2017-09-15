package track;

import static org.junit.Assert.*;

import java.util.Set;

import org.apache.commons.collections4.IterableUtils;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.rookit.dm.artist.Artist;
import org.rookit.dm.track.Track;
import org.rookit.dm.track.TrackFactory;
import org.rookit.dm.track.TrackTitle;
import org.rookit.dm.track.TypeTrack;
import org.rookit.dm.track.TypeVersion;
import org.rookit.dm.track.VersionTrack;
import org.rookit.dm.utils.DMTestFactory;

@SuppressWarnings("javadoc")
public class VersionTrackTest {

	private static DMTestFactory factory;
	private static TrackFactory trackFactory;
	
	private VersionTrack guineaPig;
	private Track original;
	private TypeVersion version;

	@BeforeClass
	public static void initialize() {
		factory = DMTestFactory.getDefault();
		trackFactory = TrackFactory.getDefault();
	}

	@Before
	public void createTrack() {
		version = factory.getRandomVersionType();
		original = factory.getRandomTrack();
		guineaPig = trackFactory.createVersionTrack(version, original);
	}
	
	@Test
	public final void testVersionType() {
		final Track orTrack = factory.getRandomTrack();
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
		final Set<Artist> artists = factory.getRandomSetOfArtists();
		assertEquals(original.getMainArtists(), guineaPig.getMainArtists());
		guineaPig.setMainArtists(artists);
		assertEquals(artists, guineaPig.getMainArtists());
		assertEquals(original.getMainArtists(), guineaPig.getMainArtists());
		guineaPig.addMainArtist(factory.getRandomArtist());
		// TODO requires further testing
		assertEquals(original.getMainArtists(), guineaPig.getMainArtists());
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
		final VersionTrack differentOriginal = trackFactory.createVersionTrack(this.version, factory.getRandomTrack());
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