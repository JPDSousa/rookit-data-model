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
package org.rookit.dm.genre;

import static org.rookit.dm.genre.DatabaseFields.*;

import org.rookit.dm.utils.CoreValidator;
import org.smof.annnotations.SmofNumber;
import org.smof.annnotations.SmofString;
import org.smof.element.AbstractElement;

class DefaultGenre extends AbstractElement implements Genre {

	private static final CoreValidator VALIDATOR = CoreValidator.getDefault();
	
	@SmofString(name = NAME)
	private final String name;
	@SmofString(name = DESCRIPTION)
	private String description;
	
	@SmofNumber(name = PLAYS)
	private long plays;
	@SmofNumber(name = DURATION)
	private long duration;

	DefaultGenre(String name) {
		this.name = name;
		this.description = "";
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		VALIDATOR.checkArgumentStringNotEmpty(description, "Description cannot be empty");
		this.description = description;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final DefaultGenre other = (DefaultGenre) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public long getPlays() {
		return plays;
	}

	@Override
	public void play() {
		this.plays++;
	}

	@Override
	public void setPlays(long plays) {
		VALIDATOR.checkArgumentPositive(plays, "Plays cannot be negative");
		this.plays = plays;
	}

	@Override
	public void setDuration(long duration) {
		this.duration = duration;
	}

	@Override
	public long getDuration() {
		return duration;
	}
	
}
