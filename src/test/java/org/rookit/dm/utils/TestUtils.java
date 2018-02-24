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

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Set;

import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.genre.Genreable;
import org.rookit.api.dm.play.able.Playable;
import org.rookit.dm.inject.DMFactoriesModule;
import org.rookit.dm.test.DMTestFactory;


import com.google.common.collect.Sets;
import com.google.inject.Guice;
import com.google.inject.Injector;

@SuppressWarnings("javadoc")
public final class TestUtils {
	
	private static final Injector INJECTOR = Guice.createInjector(
			DMTestFactory.getModule(),
			new DMFactoriesModule());
	
	public static final Injector getInjector() {
		return INJECTOR;
	}
	
	public static final void testGenres(final DMTestFactory factory, final Genreable g) {
		Set<Genre> genres = factory.getRandomSetOfGenres();
		g.setGenres(Sets.newLinkedHashSet());
		for(Genre genre : genres){
			g.addGenre(genre);
		}
		
		assertThat(g.getGenres())
		.containsExactlyInAnyOrderElementsOf(genres);
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
		
		assertThat(initialSkips)
		.isGreaterThanOrEqualTo(0);
		assertThat(p.getLastSkipped().toJavaUtil())
		.contains(lastSkipped);
		
		for(int i = 0; i < testSkips; i++) {
			p.skip();
		}
		
		assertThat(p.getLastSkipped().toJavaUtil())
		.isNotEmpty()
		.get()
		.isNotEqualTo(lastSkipped);
		
		assertThat(p.getSkipped())
		.isEqualTo(initialSkips+testSkips);
		
		p.setSkipped(testSetSkips);
		assertThat(p.getSkipped())
		.isEqualTo(testSetSkips);
	}

	private static final void assertPlays(final Playable p) {
		final LocalDate lastPlayed = LocalDate.now().minusDays(3);
		final long initialPlays = p.getPlays();
		final int testPlays = 5;
		final long testSetPlays = 230293020;
		p.setLastPlayed(lastPlayed);
		
		assertThat(initialPlays)
		.isGreaterThanOrEqualTo(0);
		assertThat(p.getLastPlayed().toJavaUtil())
		.contains(lastPlayed);
		
		for(int i = 0; i < testPlays; i++) {
			p.play();
		}
		
		assertThat(p.getLastPlayed().toJavaUtil())
		.isNotEmpty()
		.get()
		.isNotEqualTo(lastPlayed);
		
		assertThat(p.getPlays())
		.isEqualTo(initialPlays+testPlays);
		
		p.setSkipped(testSetPlays);
		assertThat(p.getSkipped())
		.isEqualTo(testSetPlays);
	}

}
