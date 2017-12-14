package org.rookit.dm.track;

import java.util.Set;

import org.rookit.dm.artist.Artist;

@SuppressWarnings("javadoc")
public interface TrackSetter<T> {

	T setTitle(String title);
	T setTitle(TrackTitle title);

	T setMainArtists(Set<Artist> artists);
	T addMainArtist(Artist artist);

	T setFeatures(Set<Artist> features);
	T addFeature(Artist artist);

	T setHiddenTrack(String hiddenTrack);

	T addProducer(Artist producer);
	T setProducers(Set<Artist> producer);
	
	T setLyrics(String lyrics);

	T setExplicit(Boolean explicit);

}
