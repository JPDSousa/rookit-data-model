package org.rookit.dm.track.audio;

@SuppressWarnings("javadoc")
public interface AudioFeature {

	short getBPM();
	
	TrackKey getTrackKey();
	
	TrackMode getTrackMode();
	
	boolean isInstrumental();
	
	boolean isLive();
	
	boolean isAcoustic();
	
	double getDanceability();
	
	double getEnergy();
	
	double getValence();
}
