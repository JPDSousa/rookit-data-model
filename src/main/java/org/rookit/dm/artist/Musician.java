package org.rookit.dm.artist;

@SuppressWarnings("javadoc")
public interface Musician extends ExtendedArtist {

	TypeGender getGender();
	void setGender(TypeGender gender);
	
	String getFullName();
	void setFullName(String fullName);
}
