package org.rookit.dm.album.disc;

import org.rookit.api.dm.album.disc.Disc;
import org.rookit.api.dm.track.Track;

public interface MutableDisc extends Disc {

    boolean putIfAbsent(Track track, int number);

    void putNextEmpty(Track track);

    void relocate(int number, int newNumber);

    Track remove(int trackNumber);

    int remove(Track track);

    void clear();
}
