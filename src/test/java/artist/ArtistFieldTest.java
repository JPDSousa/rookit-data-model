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
package artist;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import artist.Artist;
import artist.ArtistFactory;
import utils.CoreFactory;

@SuppressWarnings("javadoc")
public class ArtistFieldTest {

	private Artist guineaPig;
	
	private static ArtistFactory artistFactory;
	private static CoreFactory factory;

	@BeforeClass
	public static void initialize(){
		factory = CoreFactory.getDefault();
		artistFactory = ArtistFactory.getDefault();
	}

	@Before
	public void intializeTest(){
		guineaPig = factory.getRandomArtist();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArtistException() {
		guineaPig = artistFactory.createArtist("");
	}

	@Test
	public void testName() {
		String testName = "abc";
		guineaPig = artistFactory.createArtist(testName);
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
	public void testEqualsObject() {
		String testName = "amithesameastheother";
		assertTrue("Equals not properly implemented!", artistFactory.createArtist(testName)
				.equals(artistFactory.createArtist(testName)));
	}

	@Test
	public void testGenres() {
		factory.testGenres(guineaPig);
	}

}