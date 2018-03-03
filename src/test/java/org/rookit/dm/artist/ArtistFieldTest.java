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

import static org.junit.Assume.assumeThat;
import static org.hamcrest.Matchers.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.artist.TypeArtist;
import org.rookit.api.dm.artist.factory.ArtistFactory;
import org.rookit.api.dm.factory.RookitFactories;
import org.rookit.dm.test.DMTestFactory;
import org.rookit.dm.utils.TestUtils;
import org.rookit.test.AbstractTest;

import com.google.common.collect.Sets;
import com.google.inject.Injector;

@SuppressWarnings("javadoc")
public class ArtistFieldTest extends AbstractTest<Artist> {

	private static DMTestFactory factory;
	private static RookitFactories factories;
	
	@BeforeClass
	public static final void setUpBeforeClass() {
		final Injector injector = TestUtils.getInjector();
		factory = injector.getInstance(DMTestFactory.class);
		factories = injector.getInstance(RookitFactories.class);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArtistException() {
		guineaPig = factories.getArtistFactory().createArtist(TypeArtist.GROUP, "");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArtistExceptionTypeNull() {
		guineaPig = factories.getArtistFactory().createArtist(null, "justaregularname");
	}

	@Test
	public void testName() {
		String testName = "abc";
		guineaPig = factories.getArtistFactory().createArtist(TypeArtist.GROUP, testName);
		assertThat(guineaPig.getName()).as("Name is not being properly assigned!").isEqualTo(testName);
	}

	@Test
	public void testRelatedArtist() {
		Set<Artist> related = factory.getRandomSetOfArtists();
		assertThat(guineaPig.getRelatedArtists()).as("Related artists are not being properly initialized").isEqualTo(new HashSet<>());
		for(Artist art : related){
			guineaPig.addRelatedArtist(art);
		}
		assertThat(guineaPig.getRelatedArtists()).as("Related artists are not being properly assigned!").isEqualTo(related);
		for(Artist art : related){
			guineaPig.addRelatedArtist(art);
		}
		assertThat(guineaPig.getRelatedArtists()).as("Related artists are accepting duplicates!").isEqualTo(related);
	}

	@Test
	public void testOrigin() {
		String testOrigin = "Russia";
		guineaPig.setOrigin(testOrigin);
		assertThat(guineaPig.getOrigin()).as("Origin is not being properly assigned").isEqualTo(testOrigin);
	}
	
	@Test
	public final void testPlayable() {
		TestUtils.testPlayable(guineaPig);
	}

	@Test
	public void testEqualsObject() {
		final ArtistFactory artistFactory = factories.getArtistFactory();
		String testName = "amithesameastheother";
		final TypeArtist type = TypeArtist.GROUP;
		final Artist artist1 = artistFactory.createArtist(type, testName);
		final Artist artist2 = artistFactory.createArtist(type, testName);
		artist2.setId(artist1.getId());
		assertThat(artist2).isEqualTo(artist1);
	}
	
	@Test
	public final void testHashCode() {
		final ArtistFactory artistFactory = factories.getArtistFactory();
		String testName = "amithesameastheother";
		final TypeArtist type = TypeArtist.GROUP;
		final Artist artist1 = artistFactory.createArtist(type, testName);
		final Artist artist2 = artistFactory.createArtist(type, testName);
		artist2.setId(artist1.getId());
		assertThat(artist2.hashCode()).isEqualTo(artist1.hashCode());
	}
	
	@Test
	public final void testIPI() {
		final String ipi = factory.randomString();
		
		assumeThat(guineaPig.getIPI(), is(not(equalTo(ipi))));
		guineaPig.setIPI(ipi);
		assertThat(guineaPig.getIPI()).isEqualTo(ipi);
	}
	
	@Test
	public final void testISNI() {
		final String isni = factory.randomString();
		
		assumeThat(guineaPig.getISNI(), is(not(equalTo(isni))));
		guineaPig.setISNI(isni);
		assertThat(guineaPig.getISNI()).isEqualTo(isni);
	}
	
	@Test
	public final void testPicture() throws IOException {
		final byte[] picture = factory.randomString().getBytes();
		assertThat(guineaPig.getPicture()).isNotNull();
		guineaPig.setPicture(picture);
		final byte[] actual = new byte[picture.length];
		guineaPig.getPicture().toInput().read(actual);
		assertThat(actual).isEqualTo(picture);
	}
	
	//TODO test beginDate and endDate -> begin date cannot happen before endDate and so on

	@Test
	public void testGenres() {
		TestUtils.testGenres(factory, guineaPig);
	}
	
	@Test
	public final void testGetAllGenres() {
		assertThat(guineaPig.getAllGenres()).isEqualTo(guineaPig.getGenres());
	}
	
	@Test
	public final void testAliases() {
		final String alias = factory.randomString();
		guineaPig.addAlias(alias);
		assertThat(guineaPig.getAliases().contains(alias)).isTrue();
		final Set<String> aliases = Sets.newLinkedHashSetWithExpectedSize(1);
		aliases.add(alias);
		guineaPig.setAliases(aliases);
		assertThat(guineaPig.getAliases()).isEqualTo(aliases);
	}
	
	@Test
	public void testCompareTo() {
		final ArtistFactory artistFactory = factories.getArtistFactory();
		testCompareTo(factory.getRandomArtist());
		testCompareTo(artistFactory.createArtist(TypeArtist.GROUP, "someRandomArtist"));
	}
	
	private void testCompareTo(Artist artist) {
		assertThat(guineaPig.compareTo(artist)).isEqualTo(guineaPig.getName().compareTo(artist.getName()));
	}

	@Override
	protected Artist createGuineaPig() {
		return factory.getRandomArtist();
	}

}
