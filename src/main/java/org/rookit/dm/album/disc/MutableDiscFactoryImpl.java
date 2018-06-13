package org.rookit.dm.album.disc;

import com.google.common.base.MoreObjects;
import com.google.inject.Inject;
import org.dozer.Mapper;
import org.rookit.api.dm.album.disc.Disc;
import org.rookit.api.dm.album.disc.DiscFactory;
import org.rookit.api.dm.album.slot.TrackSlotFactory;
import org.rookit.api.dm.key.Key;

final class MutableDiscFactoryImpl implements MutableDiscFactory {

    private final DiscFactory discFactory;
    private final TrackSlotFactory trackSlotFactory;
    private final Mapper mapper;

    @Inject
    private MutableDiscFactoryImpl(final DiscFactory discFactory,
                                   final TrackSlotFactory trackSlotFactory,
                                   final Mapper mapper) {
        this.discFactory = discFactory;
        this.trackSlotFactory = trackSlotFactory;
        this.mapper = mapper;
    }

    @Override
    public MutableDisc create(final String discName) {
        return fromDisc(this.discFactory.create(discName));
    }

    private MutableDisc fromDisc(final Disc disc) {
        if (disc instanceof MutableDisc) {
            return (MutableDisc) disc;
        }
        final MutableDisc mutableDisc = new MutableDiscImpl(disc.name(), this.trackSlotFactory);
        this.mapper.map(disc, mutableDisc);

        return mutableDisc;
    }

    @Override
    public MutableDisc create(final Key key) {
        return fromDisc(this.discFactory.create(key));
    }

    @Override
    public MutableDisc createEmpty() {
        return fromDisc(this.discFactory.createEmpty());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("discFactory", this.discFactory)
                .add("trackSlotFactory", this.trackSlotFactory)
                .add("mapper", this.mapper)
                .toString();
    }
}
