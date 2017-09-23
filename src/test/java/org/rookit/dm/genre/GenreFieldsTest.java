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

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.rookit.dm.genre.Genre;
import org.rookit.dm.genre.GenreFactory;
import org.rookit.dm.utils.DMTestFactory;
import org.rookit.dm.utils.TestUtils;

@SuppressWarnings("javadoc")
public class GenreFieldsTest {
	
	private static DMTestFactory factory;
	private Genre guineaPig;
	
	@BeforeClass
	public static void initialize(){
		factory = DMTestFactory.getDefault();
	}
	
	@Before
	public void initializeTest(){
		guineaPig = factory.getRandomGenre();
	}
	
	@Test
	public final void testPlayable() {
		TestUtils.testPlayable(guineaPig);
	}

	@Test
	public void testName() {
		String name = "theGenre";
		guineaPig = GenreFactory.getDefault().createGenre(name);
		assertEquals("Name is not being properly assigned!", name, guineaPig.getName());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidGenreExceptionEmpty(){
		guineaPig = GenreFactory.getDefault().createGenre("");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidGenreExceptionNull() {
		guineaPig = GenreFactory.getDefault().createGenre(null);
	}

	@Test
	public void testDescription() {
		String testDescription = "this is the description";
		
		guineaPig.setDescription(testDescription);
		assertEquals("Description is not being properly assigned!", testDescription, guineaPig.getDescription());
	}

	@Test
	public void testEquals() {
		final GenreFactory factory = GenreFactory.getDefault();
		final String testName = "genre";
		assertTrue(factory.createGenre(testName).equals(factory.createGenre(testName)));
	}

}
