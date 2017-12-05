package org.rookit.dm.track.audio;

@SuppressWarnings("javadoc")
public interface AudioFeatureSetter<T> {

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
