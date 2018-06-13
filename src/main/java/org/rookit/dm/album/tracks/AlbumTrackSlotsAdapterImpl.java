package org.rookit.dm.album.tracks;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.rookit.api.dm.album.slot.TrackSlot;
import org.rookit.api.dm.album.tracks.AlbumTrackSlotsAdapter;
import org.rookit.api.dm.album.disc.Disc;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

final class AlbumTrackSlotsAdapterImpl implements AlbumTrackSlotsAdapter {

    private final Map<String, ? extends Disc> discs;
    private final int size;

    AlbumTrackSlotsAdapterImpl(final Map<String, ? extends Disc> discs, final int size) {
        this.discs = ImmutableMap.copyOf(discs);
        this.size = size;
    }

    @Override
    public boolean contains(final TrackSlot slot) {
        return false;
    }

    @Override
    public Collection<TrackSlot> asCollection() {
        final List<TrackSlot> trackSlots = Lists.newArrayListWithCapacity(this.size);

        for (final Disc disc : this.discs.values()) {
            trackSlots.addAll(disc.asTrackSlotsCollection());
        }

        return Collections.unmodifiableCollection(trackSlots);
    }

    @Override
    public Collection<TrackSlot> asCollection(final String cd) {
        return this.discs.get(cd).asTrackSlotsCollection();
    }

    @Override
    public TrackSlot getTrack(final String discName, final int number) {
        return this.discs.get(discName).trackAsSlot(number);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("discs", this.discs)
                .add("size", this.size)
                .toString();
    }
}
