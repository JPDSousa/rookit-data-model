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
package org.rookit.dm.play.able;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Optional;

import org.rookit.api.dm.play.StaticPlaylist;
import org.rookit.api.dm.play.able.Playable;
import org.rookit.api.storage.DBManager;
import org.rookit.dm.AbstractMetadataHolder;
import org.rookit.utils.VoidUtils;

@SuppressWarnings("javadoc")
public abstract class AbstractPlayable extends AbstractMetadataHolder implements Playable {

	protected static final int LIMIT = 50;
	protected static final long INITIAL_PLAYS = 0;
	protected static final long INITIAL_SKIPS = 0;
	
	private long plays;
	
	private long skipped;
	
	private LocalDate lastSkipped;
	
	private LocalDate lastPlayed;
	
	private Duration duration;
	
	protected AbstractPlayable() {
		plays = INITIAL_PLAYS;
		skipped = INITIAL_SKIPS;
	}

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
	public Void setPlays(final long plays) {
		VALIDATOR.checkArgument().isPositive(plays, "plays");
		this.plays = plays;
		return VoidUtils.returnVoid();
	}
	
	@Override
	public Void setDuration(final Duration duration) {
		VALIDATOR.checkArgument().isNotNull(duration, "duration");
		this.duration = duration;
		return VoidUtils.returnVoid();
	}

	@Override
	public Optional<Duration> getDuration() {
		return Optional.ofNullable(this.duration);
	}
	
	@Override
	public Optional<LocalDate> getLastPlayed() {
		return Optional.ofNullable(this.lastPlayed);
	}

	@Override
	public Void setLastPlayed(final LocalDate lastPlayed) {
		VALIDATOR.checkArgument().isNotNull(lastPlayed, "lastPlayed");
		this.lastPlayed = lastPlayed;
		return VoidUtils.returnVoid();
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
	public Void setSkipped(final long skipped) {
		VALIDATOR.checkArgument().isPositive(plays, "plays");
		this.skipped = skipped;
		return VoidUtils.returnVoid();
	}

	@Override
	public Optional<LocalDate> getLastSkipped() {
		return Optional.ofNullable(this.lastSkipped);
	}

	@Override
	public Void setLastSkipped(final LocalDate lastSkipped) {
		VALIDATOR.checkArgument().isNotNull(lastSkipped, "lastSkipped");
		this.lastSkipped = lastSkipped;
		return VoidUtils.returnVoid();
	}

	@Override
	public StaticPlaylist freeze(final DBManager db) {
		return freeze(db, LIMIT);
	}
	
	

}
