package org.rookit.dm.album.release;

import org.rookit.api.dm.album.TypeRelease;
import org.rookit.api.dm.factory.RookitFactory;
import org.rookit.api.dm.key.Key;

import java.time.LocalDate;

public interface MutableReleaseFactory extends RookitFactory<MutableRelease, Key> {

    MutableRelease releaseOf(TypeRelease release);

    MutableRelease released(TypeRelease type, LocalDate date);

    MutableRelease releasedToday(TypeRelease release);
}
