package org.rookit.dm.utils;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.Assert.*;

import org.rookit.dm.genre.Genre;
import org.rookit.dm.genre.Genreable;
import org.rookit.dm.play.Playable;
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
		assertPlays(p);
		assertSkip(p);
	}
	
	private static final void assertSkip(final Playable p) {
		final LocalDate lastSkipped = LocalDate.now().minusDays(3);
		final long initialSkips = p.getSkipped();
		final int testSkips = 5;
		final long testSetSkips = 230293020;
		p.setLastSkipped(lastSkipped);
		assertEquals(lastSkipped, p.getLastSkipped());
		assertTrue(initialSkips >= 0);
		for(int i = 0; i < testSkips; i++) {
			p.skip();
		}
		assertNotEquals(lastSkipped, p.getLastSkipped());
		assertEquals(initialSkips+testSkips, p.getSkipped());
		p.setSkipped(testSetSkips);
		assertEquals(testSetSkips, p.getSkipped());
	}

	private static final void assertPlays(final Playable p) {
		final LocalDate lastPlayed = LocalDate.now().minusDays(3);
		final long initialPlays = p.getPlays();
		final int testPlays = 5;
		final long testSetPlays = 230293020;
		p.setLastPlayed(lastPlayed);
		assertEquals(lastPlayed, p.getLastPlayed());
		assertTrue(initialPlays >= 0);
		for(int i = 0; i < testPlays; i++) {
			p.play();
		}
		assertNotEquals(lastPlayed, p.getLastPlayed());
		assertEquals(initialPlays+testPlays, p.getPlays());
		p.setPlays(testSetPlays);
		assertEquals(testSetPlays, p.getPlays());
	}

}
