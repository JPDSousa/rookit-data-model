package org.rookit.dm.track;

import java.util.Set;

import org.rookit.dm.artist.Artist;
import org.rookit.dm.track.audio.TrackKey;
import org.rookit.dm.track.audio.TrackMode;

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

	T setExplicit(boolean explicit);

	//Audio features
	T setBPM(short bpm);
	T setTrackKey(TrackKey trackKey);
	T setTrackMode(TrackMode trackMode);
	T setInstrumental(boolean isInstrumental);
	T setLive(boolean isLive);
	T setAcoustic(boolean isAcoustic);
	T setDanceability(double danceability);
	T setEnergy(double energy);
	T setValence(double valence);
}
