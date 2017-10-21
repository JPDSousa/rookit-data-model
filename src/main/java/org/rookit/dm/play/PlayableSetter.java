package org.rookit.dm.play;

import java.time.LocalDate;

public interface PlayableSetter<T> {

	T play();
	T setPlays(long plays);

	T setLastPlayed(LocalDate lastPlayed);

	T skip();
	T setSkipped(long skipped);

	T setLastSkipped(LocalDate lastSkipped);

	T setDuration(long duration);
	
}
