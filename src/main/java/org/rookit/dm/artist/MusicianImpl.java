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
import org.rookit.api.dm.artist.Musician;
import org.rookit.api.dm.artist.TypeArtist;
import org.rookit.api.dm.artist.TypeGender;
import org.rookit.dm.artist.profile.MutableProfile;
import org.rookit.dm.play.able.event.MutableEventStatsFactory;

import javax.annotation.Generated;
import java.util.Objects;

final class MusicianImpl extends AbstractArtist implements Musician {

    private final TypeGender gender;

    private final String fullName;

    MusicianImpl(final MutableProfile profile,
                 final TypeGender gender,
                 final String fullName,
                 final MutableEventStatsFactory eventStatsFactory) {
        super(profile, eventStatsFactory);
        this.gender = gender;
        this.fullName = fullName;
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public boolean equals(final Object object) {
        if (object instanceof MusicianImpl) {
            if (!super.equals(object)) {
                return false;
            }
            final MusicianImpl that = (MusicianImpl) object;
            return Objects.equals(this.gender, that.gender)
                    && Objects.equals(this.fullName, that.fullName);
        }
        return false;
    }

    @Override
    public String getFullName() {
        return this.fullName;
    }

    @Override
    public TypeGender getGender() {
        return this.gender;
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public int hashCode() {
        return Objects.hash(Integer.valueOf(super.hashCode()), this.gender, this.fullName);
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("super", super.toString())
                .add("gender", this.gender)
                .add("fullName", this.fullName)
                .toString();
    }

    @Override
    public TypeArtist type() {
        return TypeArtist.MUSICIAN;
    }
}
