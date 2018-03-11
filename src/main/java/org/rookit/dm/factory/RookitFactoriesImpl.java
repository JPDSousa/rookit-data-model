package org.rookit.dm.factory;

import org.rookit.api.dm.album.factory.AlbumFactory;
import org.rookit.api.dm.artist.factory.ArtistFactory;
import org.rookit.api.dm.genre.factory.GenreFactory;
import org.rookit.api.dm.play.factory.PlaylistFactory;
import org.rookit.api.dm.track.factory.TrackFactory;

import com.google.inject.Inject;

@SuppressWarnings("javadoc")
public final class RookitFactoriesImpl extends AbstractRookitFactories {
	
	private final ArtistFactory artistFactory;
	private final AlbumFactory albumFactory;
	private final GenreFactory genreFactory;
	private final TrackFactory trackFactory;
	private final PlaylistFactory playlistFactory;
	
	@Inject
	private RookitFactoriesImpl(
			final ArtistFactory artistFactory, 
			final AlbumFactory albumFactory, 
			final GenreFactory genreFactory, 
			final TrackFactory trackFactory, 
			final PlaylistFactory playlistFactory) {
		super();
		this.artistFactory = artistFactory;
		this.albumFactory = albumFactory;
		this.genreFactory = genreFactory;
		this.trackFactory = trackFactory;
		this.playlistFactory = playlistFactory;
	}

	@Override
	public GenreFactory getGenreFactory() {
		return genreFactory;
	}

	@Override
	public ArtistFactory getArtistFactory() {
		return artistFactory;
	}

	@Override
	public AlbumFactory getAlbumFactory() {
		return albumFactory;
	}
	
	@Override
	public TrackFactory getTrackFactory() {
		return trackFactory;
	}

	@Override
	public PlaylistFactory getPlaylistFactory() {
		return playlistFactory;
	}
	
}
