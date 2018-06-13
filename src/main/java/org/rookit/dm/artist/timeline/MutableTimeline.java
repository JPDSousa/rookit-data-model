package org.rookit.dm.artist.timeline;

import org.rookit.api.dm.artist.timeline.Timeline;

import java.time.LocalDate;

public interface MutableTimeline extends Timeline {

    void setBegin(LocalDate date);

    void setEnd(LocalDate date);

}
