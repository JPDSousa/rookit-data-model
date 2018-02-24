package org.rookit.dm.factory;

import org.rookit.api.dm.RookitModel;
import org.rookit.api.dm.album.Album;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.factory.RookitFactories;
import org.rookit.api.dm.factory.RookitFactory;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.play.Playlist;
import org.rookit.api.dm.track.Track;
import org.rookit.dm.utils.DataModelValidator;

@SuppressWarnings("javadoc")
public abstract class AbstractRookitFactories implements RookitFactories {

	protected static final DataModelValidator VALIDATOR = DataModelValidator.getDefault();
	
	protected AbstractRookitFactories() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends RookitModel> RookitFactory<T> getFactory(Class<T> clazz) {
		if (Album.class.isAssignableFrom(clazz)) {
			return (RookitFactory<T>) getAlbumFactory();
		} else if (Track.class.isAssignableFrom(clazz)) {
			return (RookitFactory<T>) getTrackFactory();
		} else if (Playlist.class.isAssignableFrom(clazz)) {
			return (RookitFactory<T>) getPlaylistFactory();
		} else if (Artist.class.isAssignableFrom(clazz)) {
			return (RookitFactory<T>) getArtistFactory();
		} else if (Genre.class.isAssignableFrom(clazz)) {
			return (RookitFactory<T>) getGenreFactory();
		}
		
		VALIDATOR.invalidOperation("Cannot create a factory for: " + clazz);
		return null;
	}

}