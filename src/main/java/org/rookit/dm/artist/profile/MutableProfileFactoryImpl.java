/*******************************************************************************
 * Copyright (C) 2018 Joao Sousa
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
package org.rookit.dm.artist.profile;

import com.google.common.base.MoreObjects;
import com.google.inject.Inject;
import org.dozer.Mapper;
import org.rookit.api.dm.artist.profile.Profile;
import org.rookit.api.dm.artist.profile.ProfileFactory;
import org.rookit.api.dm.key.Key;

final class MutableProfileFactoryImpl implements MutableProfileFactory {

    private final ProfileFactory profileFactory;
    private final Mapper mapper;

    @Inject
    private MutableProfileFactoryImpl(final ProfileFactory profileFactory, final Mapper mapper) {
        this.profileFactory = profileFactory;
        this.mapper = mapper;
    }

    private MutableProfile fromProfile(final Profile profile) {
        if (profile instanceof MutableProfile) {
            return (MutableProfile) profile;
        }
        return this.mapper.map(profile, MutableProfileImpl.class);
    }

    @Override
    public MutableProfile create(final String name, final String isni) {
        return fromProfile(this.profileFactory.create(name, isni));
    }

    @Override
    public MutableProfile create(final Key key) {
        return fromProfile(this.profileFactory.create(key));
    }

    @Override
    public MutableProfile createEmpty() {
        return fromProfile(this.profileFactory.createEmpty());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("profileFactory", this.profileFactory)
                .add("mapper", this.mapper)
                .toString();
    }
}
