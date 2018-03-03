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
package org.rookit.dm.album;

import static org.assertj.core.api.Assertions.*;

import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;
import org.rookit.api.dm.album.Album;
import org.rookit.api.dm.album.AlbumFactory;
import org.rookit.api.dm.album.TypeAlbum;
import org.rookit.api.dm.album.TypeRelease;
import org.rookit.api.dm.artist.Artist;
import org.rookit.dm.test.DMTestFactory;
import org.rookit.dm.utils.TestUtils;
import org.rookit.test.AbstractTest;
import org.rookit.utils.print.PrintUtils;
import org.rookit.utils.print.TypeFormat;

import com.google.common.collect.Sets;
import com.google.inject.Injector;

@SuppressWarnings("javadoc")
public class AlbumFactoryTest extends AbstractTest<AlbumFactory>  {

	private static DMTestFactory factory;
	private static Injector injector;
	
	@BeforeClass
	public static final void setupBeforeClass() {
		injector = TestUtils.getInjector();
		factory = injector.getInstance(DMTestFactory.class);
	}
	
	@Test
	public void testCreateSingleArtistAlbum() {
		final String albumTitle = "Album Title";
		final Set<Artist> artists = factory.getRandomSetOfArtists();

		for(TypeRelease release : TypeRelease.values()){
			final Album album;
			album = guineaPig.createSingleArtistAlbum(albumTitle, release, artists);
			assertThat(album.getAlbumType())
			.isEqualTo(TypeAlbum.ARTIST);
			
			assertThat(album.getTitle())
			.isEqualTo(albumTitle);
			
			assertThat(album.getReleaseType())
			.isEqualTo(release);
			
			assertThat(album.getArtists())
			.isEqualTo(artists);
			
			assertThat(album.getFullTitle())
			.isEqualTo(release.getFormattedName(albumTitle));
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateSingleArtistAlbumEmptyAlbumTitle(){
		guineaPig.createSingleArtistAlbum("", factory.getRandomSetOfArtists());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateSingleArtistAlbumNullAlbumTitle() {
		guineaPig.createSingleArtistAlbum(null, factory.getRandomSetOfArtists());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateSingleArtistAlbumEmptyArtists() {
		guineaPig.createSingleArtistAlbum(factory.randomString(), Sets.newLinkedHashSet());
	}

	@Test
	public void testCreateSingleArtistAlbumFromTags() {
		final String albumTag = "Album Title";
		final TypeRelease release = TypeRelease.REMIXES;
		final Set<Artist> artists = factory.getRandomSetOfArtists();
		final String albumArtistsTag = PrintUtils.getIterableAsString(artists, TypeFormat.TAG);
		final Album album = guineaPig.createSingleArtistAlbum(release.getFormattedName(albumTag), albumArtistsTag);
		
		assertThat(album.getAlbumType()).isEqualTo(TypeAlbum.ARTIST);
		assertThat(album.getTitle()).isEqualTo(albumTag);
		assertThat(release).isEqualTo(album.getReleaseType());
		assertThat(album.getArtists()).isEqualTo(artists);
	}
	
	@Test
	public void testCreateAlbumSingle() {
		final TypeAlbum artist = TypeAlbum.ARTIST;
		final String title = "Album title1";
		final TypeRelease release = TypeRelease.DELUXE;
		final Set<Artist> artists = factory.getRandomSetOfArtists();
		final Album album = guineaPig.createAlbum(artist, title, release, artists);
		
		assertThat(album.getAlbumType()).as("Unexpected album type").isEqualTo(artist);
		assertThat(album.getTitle()).as("Unexpected album title").isEqualTo(title);
		assertThat(album.getReleaseType()).as("Unexpected album release type").isEqualTo(release);
		assertThat(album.getArtists()).as("Unexpected album artists").isEqualTo(artists);
	}
	
	@Test
	public void testCreateAlbumVA() {
		final TypeAlbum artist = TypeAlbum.VA;
		final String title = "Album title1";
		final TypeRelease release = TypeRelease.DELUXE;
		final Set<Artist> artists = factory.getRandomSetOfArtists();
		final Album album = guineaPig.createAlbum(artist, title, release, artists);
		
		assertThat(album.getAlbumType()).as("Unexpected album type").isEqualTo(artist);
		assertThat(album.getTitle()).as("Unexpected album title").isEqualTo(title);
		assertThat(album.getReleaseType()).as("Unexpected album release type").isEqualTo(release);
		assertThat(album.getArtists()).as("Unexpected album artists").isEqualTo(Sets.newLinkedHashSet());
	}

	@Test
	public void testCreateVAAlbum() {
		final String title = "Albumtitle1";
		final TypeRelease release = TypeRelease.COVERS;
		final Album album = guineaPig.createVAAlbum(title, release);
		
		assertThat(album).isNotNull();
		assertThat(album.getTitle()).as("Unexpected album title").isEqualTo(title);
		assertThat(album.getAlbumType()).as("Unexpeted album type").isEqualTo(TypeAlbum.VA);
		assertThat(album.getReleaseType()).as("Unexpected album release type").isEqualTo(release);
	}

	@Override
	protected AlbumFactory createGuineaPig() {
		return injector.getInstance(AlbumFactory.class);
	}
	
}
