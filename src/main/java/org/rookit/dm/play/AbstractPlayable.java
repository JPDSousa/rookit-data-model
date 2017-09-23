package org.rookit.dm.play;

import java.time.LocalDate;

import org.rookit.dm.utils.DataModelValidator;
import org.smof.annnotations.SmofDate;
import org.smof.annnotations.SmofNumber;
import org.smof.element.AbstractElement;

@SuppressWarnings("javadoc")
public abstract class AbstractPlayable extends AbstractElement implements Playable {

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
	private long duration;
	
	protected AbstractPlayable() {}

	@Override
	public long getPlays() {
		return plays;
	}

	@Override
	public void play() {
		plays++;
		setLastPlayed(LocalDate.now());
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
	
	@Override
	public LocalDate getLastPlayed() {
		return lastPlayed;
	}

	@Override
	public void setLastPlayed(LocalDate lastPlayed) {
		this.lastPlayed = lastPlayed;
	}

	@Override
	public long getSkipped() {
		return skipped;
	}

	@Override
	public void skip() {
		skipped++;
		setLastSkipped(LocalDate.now());
	}

	@Override
	public void setSkipped(long skipped) {
		VALIDATOR.checkArgumentPositive(plays, "Skipped cannot be negative");
		this.skipped = skipped;
	}

	@Override
	public LocalDate getLastSkipped() {
		return lastSkipped;
	}

	@Override
	public void setLastSkipped(LocalDate lastSkipped) {
		this.lastSkipped = lastSkipped;
	}

}
