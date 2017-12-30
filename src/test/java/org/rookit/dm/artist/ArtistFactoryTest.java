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

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;
import org.rookit.dm.artist.Artist;
import org.rookit.dm.artist.ArtistFactory;
import org.rookit.dm.utils.DMTestFactory;
import org.rookit.dm.utils.PrintUtils;
import org.rookit.utils.print.TypeFormat;

import com.google.common.collect.Lists;

@SuppressWarnings("javadoc")
public class ArtistFactoryTest {

	private static ArtistFactory factory;
	private static DMTestFactory coreFactory;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		factory = ArtistFactory.getDefault();
		coreFactory = DMTestFactory.getDefault();
	}

	@Test
	public void testCreateFeatArtists() {
		Set<Artist> artists = coreFactory.getRandomSetOfArtists();
		Set<Artist> feats = new LinkedHashSet<>();
		Set<Artist> album = new LinkedHashSet<>();
		
		for(int i=0; i< artists.size(); i++){
			if(i < artists.size()/2){
				feats.add(Lists.newArrayList(artists).get(i));
			}
			else{
				album.add(Lists.newArrayList(artists).get(i));
			}
		}
		
		assertEquals("Feats should be: " + feats.toString(), feats, 
				factory.createFeatArtists(PrintUtils.getIterableAsString(artists, TypeFormat.TAG), 
						PrintUtils.getIterableAsString(album, TypeFormat.TAG)));
	}
	
	@Test
	public void testCreateFeatArtistsNone(){
		String artistTag = PrintUtils.getIterableAsString(coreFactory.getRandomSetOfArtists(), TypeFormat.TAG);
		
		assertTrue("There should be no feat artists", factory.createFeatArtists(artistTag, artistTag).isEmpty());
	}
	
	@Test
	public void testCreateFeatArtistsAll(){
		Set<Artist> artists = coreFactory.getRandomSetOfArtists();
		
		assertEquals("All artists should be featuring.", artists,
				factory.createFeatArtists(PrintUtils.getIterableAsString(artists, TypeFormat.TAG), ""));
	}
	
	@Test
	public void testCreateArtists(){
		List<Artist> artists = Lists.newArrayList(coreFactory.getRandomSetOfArtists());
		String[] artistNames = new String[artists.size()];
		final List<Artist> actual;
		
		for(int i=0; i < artists.size(); i++){
			artistNames[i] = artists.get(i).getName();
		}
		actual = Lists.newArrayList(factory.createArtists(artistNames));
		
		assertTrue("Empty array as parameter should return an empty set", factory.createArtists(new String[]{}).isEmpty());
		assertEquals("Method should be able to reverse engineer the to array method.", artists, actual);
	}
	
	@Test
	public void testGetArtistsFromFormat(){
		final Set<Artist> expected = coreFactory.getRandomSetOfArtists();
		final Set<Artist> actual = factory.getArtistsFromFormat(PrintUtils.getIterableAsString(expected, TypeFormat.TITLE));
		
		assertEquals(expected, actual);
	}
	
	

}
