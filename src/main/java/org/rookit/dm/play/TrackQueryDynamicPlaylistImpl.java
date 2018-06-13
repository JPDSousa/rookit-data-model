
package org.rookit.dm.play;

import com.google.common.base.MoreObjects;
import one.util.streamex.StreamEx;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.play.DynamicPlaylist;
import org.rookit.api.dm.play.TypePlaylist;
import org.rookit.api.dm.track.Track;
import org.rookit.api.storage.queries.RookitQuery;
import org.rookit.api.storage.queries.TrackQuery;
import org.rookit.dm.play.able.event.MutableEventStatsFactory;

@SuppressWarnings("javadoc")
final class TrackQueryDynamicPlaylistImpl extends AbstractPlaylist implements DynamicPlaylist {

    private final RookitQuery<?, Track> trackQuery;


    TrackQueryDynamicPlaylistImpl(final String name,
                                  final RookitQuery<TrackQuery,Track> trackQuery,
                                  final BiStream picture,
                                  final MutableEventStatsFactory eventStatsFactory) {
        super(name, picture, eventStatsFactory);
        this.trackQuery = trackQuery;
    }

    @Override
    public StreamEx<Track> streamTracks() {
        return this.trackQuery.stream();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("trackQuery", this.trackQuery)
                .toString();
    }

    @Override
    public TypePlaylist type() {
        return TypePlaylist.DYNAMIC;
    }
}
