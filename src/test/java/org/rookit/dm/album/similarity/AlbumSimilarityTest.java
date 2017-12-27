package org.rookit.dm.album.similarity;

import static org.junit.Assert.*;
import static org.hamcrest.number.OrderingComparison.*;

import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;
import org.rookit.dm.album.Album;
import org.rookit.dm.album.AlbumFactory;
import org.rookit.dm.album.TypeAlbum;
import org.rookit.dm.album.TypeRelease;
import org.rookit.dm.album.similarity.AlbumComparator;
import org.rookit.dm.artist.Artist;
import org.rookit.dm.test.DMTestFactory;

@SuppressWarnings("javadoc")
public class AlbumSimilarityTest {

	private static DMTestFactory randomFactory;
	private static AlbumFactory factory;
	private static AlbumComparator comparator;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		randomFactory = DMTestFactory.getDefault();
		factory = AlbumFactory.getDefault();
		comparator = new AlbumComparator();
	}

	@Test
	public final void testTypeAlbumMatters() {
		final String title = randomFactory.randomString();
		final TypeRelease type = TypeRelease.BESTOF;
		final Set<Artist> artists = randomFactory.getRandomSetOfArtists();
		final Album a1 = factory.createAlbum(TypeAlbum.ARTIST, title, type, artists);
		final Album a2 = factory.createAlbum(TypeAlbum.VA, title, type, artists);
		assertNotEquals(a1, a2);
		assertNotEquals(0, comparator.compare(a1, a2));
		assertEquals(0, comparator.compare(a1, a1));
		assertEquals(0, comparator.compare(a2, a2));
	}
	
	@Test
	public final void testTitleMatters() {
		final TypeRelease type = TypeRelease.BESTOF;
		final Set<Artist> artists = randomFactory.getRandomSetOfArtists();
		final Album a1 = factory.createSingleArtistAlbum("one title", type, artists);
		final Album a2 = factory.createSingleArtistAlbum("another title", type, artists);
		assertNotEquals(0, comparator.compare(a1, a2));
		assertEquals(0, comparator.compare(a1, a1));
		assertEquals(0, comparator.compare(a2, a2));
	}
	
	@Test
	public final void testTitleDistance() {
		final TypeRelease type = TypeRelease.BESTOF;
		final Set<Artist> artists = randomFactory.getRandomSetOfArtists();
		final Album a1 = factory.createSingleArtistAlbum("one title", type, artists);
		final Album a2 = factory.createSingleArtistAlbum("similar title", type, artists);
		final Album a3 = factory.createSingleArtistAlbum("completely different thing", type, artists);
		final int a1a2 = Math.abs(comparator.compare(a1, a2));
		final int a1a3 = Math.abs(comparator.compare(a1, a3));
		assertThat(a1a2, lessThan(a1a3));
		assertEquals(0, comparator.compare(a1, a1));
		assertEquals(0, comparator.compare(a2, a2));
		assertEquals(0, comparator.compare(a3, a3));
	}
	
	

}
