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

import org.mongodb.morphia.annotations.Entity;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.artist.Musician;
import org.rookit.api.dm.artist.TypeArtist;
import org.rookit.api.dm.artist.TypeGender;

import java.util.Objects;
import java.util.Optional;

import javax.annotation.Generated;
import com.google.common.base.MoreObjects;

//TODO is it possible to turn this into Artist.class.getName() somehow??
@Entity(value="Artist")
class MusicianImpl extends AbstractArtist implements Musician {

	private TypeGender gender;
	
	private String fullName;
	
	@SuppressWarnings("unused")
	@Deprecated
	private MusicianImpl() {
		this(null, null);
	}
	
	MusicianImpl(String artistName, BiStream biStream) {
		super(TypeArtist.MUSICIAN, artistName, biStream);
	}

	@Override
	public Optional<TypeGender> getGender() {
		return Optional.ofNullable(this.gender);
	}

	@Override
	public void setGender(final TypeGender gender) {
		VALIDATOR.checkArgument().isNotNull(gender, "gender");
		this.gender = gender;
	}

	@Override
	public Optional<String> getFullName() {
		return Optional.ofNullable(this.fullName);
	}

	@Override
	public void setFullName(final String fullName) {
		VALIDATOR.checkArgument().isNotEmpty(fullName, "fullName");
		this.fullName = fullName;
	}

	@Override
	@Generated(value = "GuavaEclipsePlugin")
	public int hashCode() {
		return Objects.hash(super.hashCode(), gender, fullName);
	}

	@Override
	@Generated(value = "GuavaEclipsePlugin")
	public boolean equals(Object object) {
		if (object instanceof MusicianImpl) {
			if (!super.equals(object))
				return false;
			MusicianImpl that = (MusicianImpl) object;
			return Objects.equals(this.gender, that.gender) && Objects.equals(this.fullName, that.fullName);
		}
		return false;
	}

	@Override
	@Generated(value = "GuavaEclipsePlugin")
	public String toString() {
		return MoreObjects.toStringHelper(this).add("super", super.toString()).add("gender", gender)
				.add("fullName", fullName).toString();
	}

}
