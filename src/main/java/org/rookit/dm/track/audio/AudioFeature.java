package org.rookit.dm.track.audio;

@SuppressWarnings("javadoc")
public interface AudioFeature<T> extends AudioFeatureSetter<T> {

	short getBPM();
	
	TrackKey getTrackKey();
	
	TrackMode getTrackMode();
	
	Boolean isInstrumental();
	
	Boolean isLive();
	
	Boolean isAcoustic();
	
	double getDanceability();
	
	double getEnergy();
	
	double getValence();
}
