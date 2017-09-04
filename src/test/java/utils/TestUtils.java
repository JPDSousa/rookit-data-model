package utils;

import java.util.Set;

import static org.junit.Assert.*;

import org.rookit.dm.genre.Genre;
import org.rookit.dm.genre.Genreable;
import org.rookit.dm.utils.DMTestFactory;

import com.google.common.collect.Sets;

@SuppressWarnings("javadoc")
public class TestUtils {
	
	private static final DMTestFactory FACTORY = DMTestFactory.getDefault();
	
	public static void testGenres(final Genreable g) {
		Set<Genre> genres = FACTORY.getRandomSetOfGenres();
		g.setGenres(Sets.newLinkedHashSet());
		for(Genre genre : genres){
			g.addGenre(genre);
		}
		assertEquals(genres, g.getGenres());
	}

}
