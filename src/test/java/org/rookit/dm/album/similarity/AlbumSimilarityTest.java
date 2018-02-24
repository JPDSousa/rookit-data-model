package org.rookit.dm.album.similarity;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assume.assumeThat;

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
		
		assertThat(comparator.similarity(album, album))
		.isEqualTo(0);
	}

	@Test
	public final void testTypeAlbumMatters() {
		final String title = dmFactory.randomString();
		final TypeRelease type = TypeRelease.BESTOF;
		final Set<Artist> artists = dmFactory.getRandomSetOfArtists();
		final Album a1 = factory.createAlbum(TypeAlbum.ARTIST, title, type, artists);
		final Album a2 = factory.createAlbum(TypeAlbum.VA, title, type, artists);
		
		assertFieldMatters(a1, a2);
	}
	
	@Test
	public final void testTitleMatters() {
		final TypeRelease type = TypeRelease.BESTOF;
		final Set<Artist> artists = dmFactory.getRandomSetOfArtists();
		final Album a1 = factory.createSingleArtistAlbum("one title", type, artists);
		final Album a2 = factory.createSingleArtistAlbum("another title", type, artists);

		assertFieldMatters(a1, a2);
	}

	private void assertFieldMatters(final Album a1, final Album a2) {
		assumeThat(a1, is(not(equalTo(a2))));
		assumeThat(comparator.similarity(a1, a2), is(not(equalTo(0))));
		
		assertThat(comparator.similarity(a1, a1))
		.isEqualTo(0);
		assertThat(comparator.similarity(a2, a2))
		.isEqualTo(0);
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
		
		assertThat(a1a2).isLessThan(a1a3);
	}
	
	

}
