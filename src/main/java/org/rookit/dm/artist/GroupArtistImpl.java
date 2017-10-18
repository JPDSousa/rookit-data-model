/*******************************************************************************
 * Copyright (C) 2017 Joao Sousa
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package org.rookit.dm.artist;

import java.util.Set;

import org.smof.annnotations.SmofArray;
import org.smof.annnotations.SmofString;
import org.smof.parsers.SmofType;

import com.google.common.collect.Sets;

import static org.rookit.dm.artist.DatabaseFields.*;

class GroupArtistImpl extends AbstractArtist implements GroupArtist {

	@SmofArray(name = MEMBERS, type = SmofType.OBJECT)
	private Set<Musician> members;
	
	@SmofString(name = GROUP_TYPE)
	private TypeGroup groupType;
	
	protected GroupArtistImpl(String artistName) {
		super(TypeArtist.GROUP, artistName);
		groupType = TypeGroup.DEFAULT;
		members = Sets.newLinkedHashSet();
	}

	@Override
	public Iterable<Musician> getMembers() {
		return members;
	}

	@Override
	public void setMembers(Iterable<Musician> members) {
		VALIDATOR.checkArgumentNotNull(members, "Cannot set a null set of members");
		this.members = Sets.newLinkedHashSet(members);
	}

	@Override
	public void addMember(Musician member) {
		VALIDATOR.checkArgumentNotNull(member, "Cannot add a null member");
		members.add(member);
	}

	@Override
	public TypeGroup getGroupType() {
		return groupType;
	}

	@Override
	public void setGroupType(TypeGroup groupType) {
		VALIDATOR.checkArgumentNotNull(groupType, "Cannot set a null group type");
		this.groupType = groupType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((groupType == null) ? 0 : groupType.hashCode());
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
		GroupArtistImpl other = (GroupArtistImpl) obj;
		if (groupType != other.groupType) {
			return false;
		}
		return true;
	}
	
	

	

}
