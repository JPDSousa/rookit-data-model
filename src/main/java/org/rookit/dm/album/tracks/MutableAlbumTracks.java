package org.rookit.dm.album.tracks;

import org.rookit.api.dm.album.slot.TrackSlot;
import org.rookit.api.dm.album.tracks.AlbumTracks;
import org.rookit.api.dm.track.Track;

public interface MutableAlbumTracks extends AlbumTracks {

    void clearTracks();

    void removeTrack(Track track);

    void removeTrack(int number, String disc);

    void relocate(String discName, int number, String newDiscName, int newNumber);

    void addTrackSlot(TrackSlot slot);

    void addTrack(Track track, int number, String discName);

    void addTrackLast(Track track, String discName);
}
