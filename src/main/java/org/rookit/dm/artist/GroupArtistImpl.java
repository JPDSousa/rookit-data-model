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

import com.google.common.base.MoreObjects;
import com.google.common.collect.Sets;
import org.rookit.api.dm.artist.*;
import org.rookit.dm.artist.profile.MutableProfile;
import org.rookit.dm.play.able.event.MutableEventStatsFactory;
import org.rookit.utils.VoidUtils;

import javax.annotation.Generated;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

class GroupArtistImpl extends AbstractArtist implements GroupArtist {

    private Set<Musician> members;

    private final TypeGroup groupType;

    GroupArtistImpl(final MutableProfile profile,
                    final TypeGroup groupType,
                    final MutableEventStatsFactory eventStatsFactory) {
        super(profile, eventStatsFactory);
        this.groupType = groupType;
        this.members = Collections.synchronizedSet(Sets.newLinkedHashSet());
    }

    @Override
    public Void addMember(final Musician member) {
        VALIDATOR.checkArgument().isNotNull(member, "member");
        this.members.add(member);
        return VoidUtils.returnVoid();
    }

    @Override
    public Void addMembers(final Collection<Musician> members) {
        VALIDATOR.checkArgument().isNotNull(members, "members");
        this.members.addAll(members);
        return VoidUtils.returnVoid();
    }

    @Override
    public Void clearMembers() {
        this.members.clear();
        return VoidUtils.returnVoid();
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public boolean equals(final Object object) {
        if (object instanceof GroupArtistImpl) {
            if (!super.equals(object)) {
                return false;
            }
            final GroupArtistImpl that = (GroupArtistImpl) object;
            return Objects.equals(this.groupType, that.groupType);
        }
        return false;
    }

    @Override
    public TypeGroup getGroupType() {
        return this.groupType;
    }

    @Override
    public Collection<Musician> getMembers() {
        return Collections.unmodifiableCollection(this.members);
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public int hashCode() {
        return Objects.hash(Integer.valueOf(super.hashCode()), this.groupType);
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
    public Void setMembers(final Collection<Musician> members) {
        VALIDATOR.checkArgument().isNotNull(members, "members");
        this.members = Sets.newLinkedHashSet(members);
        return VoidUtils.returnVoid();
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("super", super.toString())
                .add("members", this.members)
                .add("groupType", this.groupType)
                .toString();
    }

    @Override
    public TypeArtist type() {
        return TypeArtist.GROUP;
    }

}
