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
package org.rookit.dm.genre;

import static org.assertj.core.api.Assertions.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.rookit.api.dm.factory.RookitFactories;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.genre.factory.GenreFactory;
import org.rookit.dm.test.DMTestFactory;
import org.rookit.dm.utils.TestUtils;
import org.rookit.test.AbstractTest;

import com.google.inject.Injector;

@SuppressWarnings("javadoc")
public class GenreFieldsTest extends AbstractTest<Genre> {
	
	private static DMTestFactory factory;
	private static RookitFactories factories;
	
	@BeforeClass
	public static final void setUpBeforeClass() {
		final Injector injector = TestUtils.getInjector();
		factory = injector.getInstance(DMTestFactory.class);
		factories = injector.getInstance(RookitFactories.class);
	}
	
	@Test
	public final void testPlayable() {
		TestUtils.testPlayable(guineaPig);
	}

	@Test
	public void testName() {
		String name = "theGenre";
		guineaPig = factories.getGenreFactory().createGenre(name);
		assertThat(guineaPig.getName()).as("Name is not being properly assigned!").isEqualTo(name);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidGenreExceptionEmpty(){
		guineaPig = factories.getGenreFactory().createGenre("");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidGenreExceptionNull() {
		guineaPig = factories.getGenreFactory().createGenre(null);
	}

	@Test
	public void testDescription() {
		String testDescription = "this is the description";
		
		guineaPig.setDescription(testDescription);
		assertThat(guineaPig.getDescription())
		.as("Description is not being properly assigned!")
		.isNotEmpty()
		.get()
		.isEqualTo(testDescription);
	}

	@Test
	public void testEquals() {
		final GenreFactory factory = factories.getGenreFactory();
		final String testName = "genre";
		assertThat(factory.createGenre(testName).equals(factory.createGenre(testName))).isTrue();
	}
	
	@Test
	public void testCompareTo() {
		testCompareTo(guineaPig);
		testCompareTo(factory.getRandomGenre());
		testCompareTo(factories.getGenreFactory().createGenre("someRandomGenre"));
	}
	
	private void testCompareTo(Genre genre) {
		assertThat(guineaPig.compareTo(genre)).isEqualTo(guineaPig.getName().compareTo(genre.getName()));
	}

	@Override
	protected Genre createGuineaPig() {
		return factory.getRandomGenre();
	}

}
