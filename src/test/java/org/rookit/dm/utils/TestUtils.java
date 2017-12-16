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
package org.rookit.dm.utils;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.Assert.*;

import org.rookit.dm.genre.Genre;
import org.rookit.dm.genre.Genreable;
import org.rookit.dm.play.Playable;
import org.rookit.dm.test.DMTestFactory;

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
