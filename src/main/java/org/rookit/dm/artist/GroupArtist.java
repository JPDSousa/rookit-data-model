package org.rookit.dm.artist;

@SuppressWarnings("javadoc")
public interface GroupArtist extends ExtendedArtist {
	
	public TypeGroup getGroupType();
	public void setGroupType(TypeGroup type);
	
	public Iterable<Musician> getMembers();
	public void setMembers(Iterable<Musician> members);
	public void addMember(Musician member);

}
