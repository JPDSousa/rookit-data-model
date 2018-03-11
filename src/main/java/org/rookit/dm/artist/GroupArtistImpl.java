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

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.artist.GroupArtist;
import org.rookit.api.dm.artist.Musician;
import org.rookit.api.dm.artist.TypeArtist;
import org.rookit.api.dm.artist.TypeGroup;
import org.rookit.utils.VoidUtils;

import com.google.common.collect.Sets;

//TODO is it possible to turn this into Artist.class.getName() somehow??
@Entity(value="Artist")
class GroupArtistImpl extends AbstractArtist implements GroupArtist {

	@Reference(idOnly = true)
	private Set<Musician> members;
	
	private TypeGroup groupType;
	
	@SuppressWarnings("unused")
	private GroupArtistImpl() {
		this(null, null);
	}
	
	GroupArtistImpl(final String artistName, final BiStream picture) {
		super(TypeArtist.GROUP, artistName, picture);
		this.groupType = TypeGroup.DEFAULT;
		this.members = Collections.synchronizedSet(Sets.newLinkedHashSet());
	}

	@Override
	public Collection<Musician> getMembers() {
		return Collections.unmodifiableCollection(this.members);
	}

	@Override
	public Void setMembers(final Collection<Musician> members) {
		VALIDATOR.checkArgument().isNotNull(members, "members");
		this.members = Sets.newLinkedHashSet(members);
		return VoidUtils.returnVoid();
	}

	@Override
	public Void addMember(final Musician member) {
		VALIDATOR.checkArgument().isNotNull(member, "member");
		this.members.add(member);
		return VoidUtils.returnVoid();
	}

	@Override
	public TypeGroup getGroupType() {
		return groupType;
	}

	@Override
	public Void setGroupType(final TypeGroup groupType) {
		VALIDATOR.checkArgument().isNotNull(groupType, "group type");
		this.groupType = groupType;
		return VoidUtils.returnVoid();
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

	@Override
	public Void addMembers(final Collection<Musician> members) {
		VALIDATOR.checkArgument().isNotNull(members, "members");
		this.members.addAll(members);
		return VoidUtils.returnVoid();
	}

	@Override
	public Void removeMember(final Musician member) {
		VALIDATOR.checkArgument().isNotNull(member, "member");
		this.members.remove(member);
		return VoidUtils.returnVoid();
	}

	@Override
	public Void removeMembers(final Collection<Musician> members) {
		VALIDATOR.checkArgument().isNotNull(members, "members");
		this.members.removeAll(members);
		return VoidUtils.returnVoid();
	}

	@Override
	public Void clearMembers() {
		this.members.clear();
		return VoidUtils.returnVoid();
	}
	
}
