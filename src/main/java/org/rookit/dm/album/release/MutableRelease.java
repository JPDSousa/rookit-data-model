package org.rookit.dm.album.release;

import org.rookit.api.dm.album.release.Release;

import java.time.LocalDate;

public interface MutableRelease extends Release {

    void setReleaseDate(LocalDate releaseDate);
}
