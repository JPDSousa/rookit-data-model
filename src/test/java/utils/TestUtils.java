package utils;

import java.util.Set;

import static org.junit.Assert.*;

import org.rookit.dm.genre.Genre;
import org.rookit.dm.genre.Genreable;
import org.rookit.dm.track.Playable;
import org.rookit.dm.utils.DMTestFactory;

import com.google.common.collect.Sets;

@SuppressWarnings("javadoc")
public final class TestUtils {
	
	private static final DMTestFactory FACTORY = DMTestFactory.getDefault();
	
	public static final void testGenres(final Genreable g) {
		Set<Genre> genres = FACTORY.getRandomSetOfGenres();
		g.setGenres(Sets.newLinkedHashSet());
		for(Genre genre : genres){
			g.addGenre(genre);
		}
		assertEquals(genres, g.getGenres());
	}
	
	public static final void testPlayable(final Playable p) {
		final long initialPlays = p.getPlays();
		final int testPlays = 5;
		final long testSetPlays = 230293020;
		assertTrue(initialPlays >= 0);
		for(int i = 0; i < testPlays; i++) {
			p.play();
		}
		assertEquals(initialPlays+testPlays, p.getPlays());
		p.setPlays(testSetPlays);
		assertEquals(testSetPlays, p.getPlays());
	}

}
