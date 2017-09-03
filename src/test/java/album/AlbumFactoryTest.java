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
package album;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;
import org.rookit.dm.album.Album;
import org.rookit.dm.album.AlbumFactory;
import org.rookit.dm.album.TypeAlbum;
import org.rookit.dm.album.TypeRelease;
import org.rookit.dm.artist.Artist;
import org.rookit.dm.utils.PrintUtils;
import org.rookit.utils.print.TypeFormat;

import com.google.common.collect.Sets;

import utils.CoreFactory;

@SuppressWarnings("javadoc")
public class AlbumFactoryTest {

	private static CoreFactory factory;
	private static AlbumFactory albumFactory;

	@BeforeClass
	public static void setUpBeforeClass() {
		factory = CoreFactory.getDefault();
		albumFactory = AlbumFactory.getDefault();
	}
	
	@Test
	public void testGetDefault() {
		assertNotNull(AlbumFactory.getDefault());
	}

	@Test
	public void testCreateSingleArtistAlbum() {
		final String albumTitle = "Album Title";
		final Set<Artist> artists = factory.getRandomSetOfArtists();
		Album album;

		for(TypeRelease release : TypeRelease.values()){
			album = albumFactory.createSingleArtistAlbum(albumTitle, release, artists);
			assertEquals("Album type should be: " + TypeAlbum.ARTIST, TypeAlbum.ARTIST, album.getAlbumType());
			assertEquals("Album title should be: Album Title", albumTitle, album.getTitle());
			assertEquals("Type release should be: " + release.name(), release, album.getReleaseType());
			assertEquals("Album artists should be: " + artists.toString(), artists, album.getArtists());
			assertEquals("Album full title should be: " + release.getFormattedName(albumTitle), release.getFormattedName(albumTitle), album.getFullTitle());
		}
	}


	@Test(expected = IllegalArgumentException.class)
	public void testCreateSingleArtistAlbumEmptyAlbumTitle(){
		albumFactory.createSingleArtistAlbum("", factory.getRandomSetOfArtists());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateSingleArtistAlbumNullAlbumTitle() {
		albumFactory.createSingleArtistAlbum(null, factory.getRandomSetOfArtists());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateSingleArtistAlbumEmptyArtists() {
		albumFactory.createSingleArtistAlbum(factory.randomString(), Sets.newLinkedHashSet());
	}

	@Test
	public void testCreateSingleArtistAlbumFromTags() {
		final String albumTag = "Album Title";
		final TypeRelease release = TypeRelease.REMIXES;
		final Set<Artist> artists = factory.getRandomSetOfArtists();
		final String albumArtistsTag = PrintUtils.getIterableAsString(artists, TypeFormat.TAG);
		final Album album = albumFactory.createSingleArtistAlbum(release.getFormattedName(albumTag), albumArtistsTag);
		
		assertEquals("Album type should be: " + TypeAlbum.ARTIST, TypeAlbum.ARTIST, album.getAlbumType());
		assertEquals("Album title should be: Album Title", albumTag, album.getTitle());
		assertEquals("Type release should be: " + release.name(), album.getReleaseType(), release);
		assertEquals("Album artists should be: " + artists.toString(), artists, album.getArtists());
	}
	
	@Test
	public void testCreateAlbumSingle() {
		final TypeAlbum artist = TypeAlbum.ARTIST;
		final String title = "Album title1";
		final TypeRelease release = TypeRelease.DELUXE;
		final Set<Artist> artists = factory.getRandomSetOfArtists();
		final Album album = albumFactory.createAlbum(artist, title, release, artists);
		
		assertEquals("Unexpected album type", artist, album.getAlbumType());
		assertEquals("Unexpected album title", title, album.getTitle());
		assertEquals("Unexpected album release type", release, album.getReleaseType());
		assertEquals("Unexpected album artists", artists, album.getArtists());
	}
	
	@Test
	public void testCreateAlbumVA() {
		final TypeAlbum artist = TypeAlbum.VA;
		final String title = "Album title1";
		final TypeRelease release = TypeRelease.DELUXE;
		final Set<Artist> artists = factory.getRandomSetOfArtists();
		final Album album = albumFactory.createAlbum(artist, title, release, artists);
		
		assertEquals("Unexpected album type", artist, album.getAlbumType());
		assertEquals("Unexpected album title", title, album.getTitle());
		assertEquals("Unexpected album release type", release, album.getReleaseType());
		assertEquals("Unexpected album artists", Sets.newLinkedHashSet(), album.getArtists());
	}

	@Test
	public void testCreateVAAlbum() {
		final String title = "Albumtitle1";
		final TypeRelease release = TypeRelease.COVERS;
		final Album album = albumFactory.createVAAlbum(title, release);
		
		assertNotNull(album);
		assertEquals("Unexpected album title", title, album.getTitle());
		assertEquals("Unexpeted album type", TypeAlbum.VA, album.getAlbumType());
		assertEquals("Unexpected album release type", release, album.getReleaseType());
	}
	
	@Test
	public void testDefaultTypeRelease() {
		assertNotNull(albumFactory.getDefaultTypeRelease());
	}
	
}
