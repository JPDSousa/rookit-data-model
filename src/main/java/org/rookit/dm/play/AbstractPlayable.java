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
package org.rookit.dm.play;

import java.time.Duration;
import java.time.LocalDate;

import org.rookit.dm.AbstractRookitModel;
import org.rookit.dm.utils.DataModelValidator;
import org.smof.annnotations.SmofDate;
import org.smof.annnotations.SmofNumber;

@SuppressWarnings("javadoc")
public abstract class AbstractPlayable extends AbstractRookitModel implements Playable {

	protected static final DataModelValidator VALIDATOR = DataModelValidator.getDefault();
	
	@SmofNumber(name = PLAYS)
	private long plays;
	
	@SmofNumber(name = SKIPPED)
	private long skipped;
	
	@SmofDate(name = LAST_SKIPPED)
	private LocalDate lastSkipped;
	
	@SmofDate(name = LAST_PLAYED)
	private LocalDate lastPlayed;
	
	@SmofNumber(name = DURATION)
	private Duration duration;
	
	protected AbstractPlayable() {}

	@Override
	public long getPlays() {
		return plays;
	}

	@Override
	public Void play() {
		plays++;
		return setLastPlayed(LocalDate.now());
	}

	@Override
	public Void setPlays(long plays) {
		VALIDATOR.checkArgumentPositive(plays, "Plays cannot be negative");
		this.plays = plays;
		return null;
	}
	
	@Override
	public Void setDuration(Duration duration) {
		this.duration = duration;
		return null;
	}

	@Override
	public Duration getDuration() {
		return duration;
	}
	
	@Override
	public LocalDate getLastPlayed() {
		return lastPlayed;
	}

	@Override
	public Void setLastPlayed(LocalDate lastPlayed) {
		this.lastPlayed = lastPlayed;
		return null;
	}

	@Override
	public long getSkipped() {
		return skipped;
	}

	@Override
	public Void skip() {
		skipped++;
		return setLastSkipped(LocalDate.now());
	}

	@Override
	public Void setSkipped(long skipped) {
		VALIDATOR.checkArgumentPositive(plays, "Skipped cannot be negative");
		this.skipped = skipped;
		return null;
	}

	@Override
	public LocalDate getLastSkipped() {
		return lastSkipped;
	}

	@Override
	public Void setLastSkipped(LocalDate lastSkipped) {
		this.lastSkipped = lastSkipped;
		return null;
	}

}
