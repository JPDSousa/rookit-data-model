package org.rookit.dm.track.audio;

@SuppressWarnings("javadoc")
public interface AudioFeature extends AudioFeatureSetter<Void> {

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
