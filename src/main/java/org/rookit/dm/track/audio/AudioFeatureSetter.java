package org.rookit.dm.track.audio;

@SuppressWarnings("javadoc")
public interface AudioFeatureSetter<T> {

	T setBPM(short bpm);
	
	T setTrackKey(TrackKey trackKey);
	
	T setTrackMode(TrackMode trackMode);
	
	T setInstrumental(Boolean isInstrumental);
	
	T setLive(Boolean isLive);
	
	T setAcoustic(Boolean isAcoustic);
	
	T setDanceability(double danceability);
	
	T setEnergy(double energy);
	
	T setValence(double valence);
	
}
