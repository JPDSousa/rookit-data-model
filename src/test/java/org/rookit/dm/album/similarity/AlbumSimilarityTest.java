package org.rookit.dm.album.similarity;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.rookit.api.dm.album.Album;
import org.rookit.api.dm.album.AlbumFactory;
import org.rookit.api.dm.album.TypeAlbum;
import org.rookit.api.dm.album.TypeRelease;
import org.rookit.api.dm.artist.Artist;
import org.rookit.dm.album.similarity.AlbumComparator;
import org.rookit.dm.test.DMTestFactory;
import org.rookit.dm.utils.TestUtils;

import com.google.inject.Injector;

@SuppressWarnings("javadoc")
public class AlbumSimilarityTest {

	private static DMTestFactory dmFactory;
	private static AlbumComparator comparator;
	private static AlbumFactory factory;
	
	@Before
	public void setUp() {
		final Injector injector = TestUtils.getInjector();
		dmFactory = injector.getInstance(DMTestFactory.class);
		factory = injector.getInstance(AlbumFactory.class);
		comparator = new AlbumComparator();
	}
	
	@Test
	public final void sameAlbumShouldBe0() {
		final Album album = dmFactory.getRandomAlbum();
		assertEquals(0, comparator.similarity(album, album), 0);
	}

	@Test
	public final void testTypeAlbumMatters() {
		final String title = dmFactory.randomString();
		final TypeRelease type = TypeRelease.BESTOF;
		final Set<Artist> artists = dmFactory.getRandomSetOfArtists();
		final Album a1 = factory.createAlbum(TypeAlbum.ARTIST, title, type, artists);
		final Album a2 = factory.createAlbum(TypeAlbum.VA, title, type, artists);
		assertNotEquals(a1, a2);
		assertNotEquals(0, comparator.similarity(a1, a2));
		assertEquals(0, comparator.similarity(a1, a1), 0);
		assertEquals(0, comparator.similarity(a2, a2), 0);
	}
	
	@Test
	public final void testTitleMatters() {
		final TypeRelease type = TypeRelease.BESTOF;
		final Set<Artist> artists = dmFactory.getRandomSetOfArtists();
		final Album a1 = factory.createSingleArtistAlbum("one title", type, artists);
		final Album a2 = factory.createSingleArtistAlbum("another title", type, artists);
		assertNotEquals(0, comparator.similarity(a1, a2));
		assertEquals(0, comparator.similarity(a1, a1), 0);
		assertEquals(0, comparator.similarity(a2, a2), 0);
	}
	
	@Test
	public final void testTitleDistance() {
		final TypeRelease type = TypeRelease.BESTOF;
		final Set<Artist> artists = dmFactory.getRandomSetOfArtists();
		final Album a1 = factory.createSingleArtistAlbum("one title", type, artists);
		final Album a2 = factory.createSingleArtistAlbum("similar title", type, artists);
		final Album a3 = factory.createSingleArtistAlbum("completely different thing", type, artists);
		final double a1a2 = Math.abs(comparator.similarity(a1, a2));
		final double a1a3 = Math.abs(comparator.similarity(a1, a3));
		assertThat(a1a2, lessThan(a1a3));
		assertEquals(0, comparator.similarity(a1, a1), 0);
		assertEquals(0, comparator.similarity(a2, a2), 0);
		assertEquals(0, comparator.similarity(a3, a3), 0);
	}
	
	

}
