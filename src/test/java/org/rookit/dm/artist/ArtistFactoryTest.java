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

import static org.assertj.core.api.Assertions.*;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.artist.factory.ArtistFactory;
import org.rookit.dm.test.DMTestFactory;
import org.rookit.dm.utils.TestUtils;
import org.rookit.test.AbstractTest;
import org.rookit.utils.print.PrintUtils;
import org.rookit.utils.print.TypeFormat;

import com.google.common.collect.Lists;
import com.google.inject.Injector;

@SuppressWarnings("javadoc")
public class ArtistFactoryTest extends AbstractTest<ArtistFactory> {

	private static DMTestFactory factory;
	private static Injector injector;
	
	@BeforeClass
	public static void setup() {
		injector = TestUtils.getInjector();
		factory = injector.getInstance(DMTestFactory.class);
	}

	@Test
	public void testCreateFeatArtists() {
		List<Artist> artists = Lists.newArrayList(factory.getRandomSetOfArtists());
		Set<Artist> feats = new LinkedHashSet<>();
		Set<Artist> album = new LinkedHashSet<>();
		
		for(int i=0; i< artists.size(); i++){
			if(i < artists.size()/2){
				feats.add(artists.get(i));
			}
			else{
				album.add(artists.get(i));
			}
		}
		
		final String artistTag = PrintUtils.getIterableAsString(artists, TypeFormat.TAG);
		final String albumArtistTag = PrintUtils.getIterableAsString(album, TypeFormat.TAG);
		assertThat(guineaPig.createFeatArtists(artistTag, albumArtistTag))
		.isEqualTo(feats);
	}
	
	@Test
	public void testCreateFeatArtistsNone(){
		String artistTag = PrintUtils.getIterableAsString(factory.getRandomSetOfArtists(), TypeFormat.TAG);
		
		assertThat(guineaPig.createFeatArtists(artistTag, artistTag).isEmpty()).as("There should be no feat artists").isTrue();
	}
	
	@Test
	public void testCreateFeatArtistsAll(){
		Set<Artist> artists = factory.getRandomSetOfArtists();
		
		final String aritstTag = PrintUtils.getIterableAsString(artists, TypeFormat.TAG);
		final String albumArtistsTag = StringUtils.EMPTY;
		
		assertThat(guineaPig.createFeatArtists(aritstTag, albumArtistsTag))
		.isEqualTo(artists);
	}
	
	@Test
	public void testCreateArtists(){
		List<Artist> artists = Lists.newArrayList(factory.getRandomSetOfArtists());
		String[] artistNames = new String[artists.size()];
		final List<Artist> actual;
		
		for(int i=0; i < artists.size(); i++){
			artistNames[i] = artists.get(i).getName();
		}
		actual = Lists.newArrayList(guineaPig.createArtists(artistNames));
		
		assertThat(guineaPig.createArtists(new String[]{}).isEmpty()).as("Empty array as parameter should return an empty set").isTrue();
		assertThat(actual).as("Method should be able to reverse engineer the to array method.").isEqualTo(artists);
	}
	
	@Test
	public void testGetArtistsFromFormat(){
		final Set<Artist> expected = factory.getRandomSetOfArtists();
		final Set<Artist> actual = guineaPig.getArtistsFromFormat(PrintUtils.getIterableAsString(expected, TypeFormat.TITLE));
		
		assertThat(actual).isEqualTo(expected);
	}

	@Override
	protected ArtistFactory createGuineaPig() {
		return injector.getInstance(ArtistFactory.class);
	}
	
	

}
