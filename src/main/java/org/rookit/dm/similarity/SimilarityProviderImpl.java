package org.rookit.dm.similarity;

import java.util.Map;

import org.apache.commons.collections4.map.LazyMap;
import org.rookit.api.dm.RookitModel;
import org.rookit.api.dm.album.Album;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.play.Playlist;
import org.rookit.api.dm.track.Track;
import org.rookit.dm.album.similarity.AlbumComparator;
import org.rookit.dm.artist.similarity.ArtistComparator;
import org.rookit.dm.genre.similarity.GenreComparator;
import org.rookit.dm.play.similarity.PlaylistComparator;
import org.rookit.dm.similarity.Similarity;
import org.rookit.dm.similarity.calculator.SimilarityMeasure;
import org.rookit.dm.track.similarity.TrackComparator;
import org.rookit.dm.utils.DataModelValidator;

import com.google.common.collect.Maps;

class SimilarityProviderImpl implements SimilarityProvider {

	private static final DataModelValidator VALIDATOR = DataModelValidator.getDefault();
	
	private Map<Class<?>, Similarity<?>> similarities;
	
	SimilarityProviderImpl() {
		similarities = LazyMap.lazyMap(Maps.newHashMap(), input -> {
			if(Track.class.isAssignableFrom(input)) {
				return new TrackComparator();
			}
			else if(Artist.class.isAssignableFrom(input)) {
				return new ArtistComparator();
			}
			else if(Album.class.isAssignableFrom(input)) {
				return new AlbumComparator();
			}
			else if(Genre.class.isAssignableFrom(input)) {
				return new GenreComparator();
			}
			else if(Playlist.class.isAssignableFrom(input)) {
				return new PlaylistComparator();
			}
			VALIDATOR.invalidOperation("Cannot find a similarity measure for: " + input);
			return null;
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends RookitModel> SimilarityMeasure<T> getMeasure(Class<T> clazz, T base) {
		final Similarity<T> similarity = (Similarity<T>) similarities.get(clazz);
		return SimilarityMeasure.create(base, similarity);
	}

}
