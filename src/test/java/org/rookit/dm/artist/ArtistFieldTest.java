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
package org.rookit.dm.artist;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.rookit.dm.artist.Artist;
import org.rookit.dm.artist.ArtistFactory;
import org.rookit.dm.artist.TypeArtist;
import org.rookit.dm.utils.DMTestFactory;
import org.rookit.dm.utils.TestUtils;

@SuppressWarnings("javadoc")
public class ArtistFieldTest {

	private Artist guineaPig;
	
	private static ArtistFactory artistFactory;
	private static DMTestFactory factory;

	@BeforeClass
	public static void initialize(){
		factory = DMTestFactory.getDefault();
		artistFactory = ArtistFactory.getDefault();
	}

	@Before
	public void intializeTest(){
		guineaPig = factory.getRandomArtist();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArtistException() {
		guineaPig = artistFactory.createArtist(TypeArtist.GROUP, "");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArtistExceptionTypeNull() {
		guineaPig = artistFactory.createArtist(null, "justaregularname");
	}

	@Test
	public void testName() {
		String testName = "abc";
		guineaPig = artistFactory.createArtist(TypeArtist.GROUP, testName);
		assertEquals("Name is not being properly assigned!", testName, guineaPig.getName());
	}

	@Test
	public void testRelatedArtist() {
		Set<Artist> related = factory.getRandomSetOfArtists();
		assertEquals("Related artists are not being properly initialized", new HashSet<>(), guineaPig.getRelatedArtists());
		for(Artist art : related){
			guineaPig.addRelatedArtist(art);
		}
		assertEquals("Related artists are not being properly assigned!", related, guineaPig.getRelatedArtists());
		for(Artist art : related){
			guineaPig.addRelatedArtist(art);
		}
		assertEquals("Related artists are accepting duplicates!", related, guineaPig.getRelatedArtists());
	}

	@Test
	public void testOrigin() {
		String testOrigin = "Russia";
		guineaPig.setOrigin(testOrigin);
		assertEquals("Origin is not being properly assigned", testOrigin, guineaPig.getOrigin());
	}
	
	@Test
	public final void testPlayable() {
		TestUtils.testPlayable(guineaPig);
	}

	@Test
	public void testEqualsObject() {
		String testName = "amithesameastheother";
		final TypeArtist type = TypeArtist.GROUP;
		final Artist artist1 = artistFactory.createArtist(type, testName);
		final Artist artist2 = artistFactory.createArtist(type, testName);
		artist2.setId(artist1.getId());
		assertEquals(artist1, artist2);
	}
	
	@Test
	public final void testHashCode() {
		String testName = "amithesameastheother";
		final TypeArtist type = TypeArtist.GROUP;
		final Artist artist1 = artistFactory.createArtist(type, testName);
		final Artist artist2 = artistFactory.createArtist(type, testName);
		artist2.setId(artist1.getId());
		assertEquals(artist1.hashCode(), artist2.hashCode());
	}

	@Test
	public void testGenres() {
		TestUtils.testGenres(guineaPig);
	}

}