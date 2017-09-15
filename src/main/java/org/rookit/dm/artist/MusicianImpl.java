package org.rookit.dm.artist;

import org.smof.annnotations.SmofString;

import static org.rookit.dm.artist.DatabaseFields.*;

class MusicianImpl extends AbstractArtist implements Musician {

	@SmofString(name = GENDER)
	private TypeGender gender;
	
	@SmofString(name = FULL_NAME)
	private String fullName;
	
	protected MusicianImpl(String artistName) {
		super(TypeArtist.MUSICIAN, artistName);
	}

	@Override
	public TypeGender getGender() {
		return gender;
	}

	@Override
	public void setGender(TypeGender gender) {
		this.gender = gender;
	}

	@Override
	public String getFullName() {
		return fullName;
	}

	@Override
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		MusicianImpl other = (MusicianImpl) obj;
		if (fullName == null) {
			if (other.fullName != null) {
				return false;
			}
		} else if (!fullName.equals(other.fullName)) {
			return false;
		}
		if (gender != other.gender) {
			return false;
		}
		return true;
	}

}
