package org.rookit.dm.album.slot;

import com.google.common.base.MoreObjects;
import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;
import org.rookit.api.dm.album.slot.ImmutableTrackSlot;
import org.rookit.api.dm.album.slot.TrackSlot;
import org.rookit.api.dm.album.slot.TrackSlotFactory;
import org.rookit.api.dm.key.Key;
import org.rookit.api.dm.track.Track;
import org.rookit.dm.album.slot.config.TrackSlotConfig;
import org.rookit.dm.utils.DataModelValidator;
import org.rookit.utils.log.validator.Validator;

final class ImmutableTrackSlotFactory implements TrackSlotFactory {

    private static final Validator VALIDATOR = DataModelValidator.getDefault();
    private static final Logger logger = VALIDATOR.getLogger(ImmutableTrackSlotFactory.class);

    private final TrackSlotConfig config;

    @Inject
    private ImmutableTrackSlotFactory(final TrackSlotConfig config) {
        this.config = config;
    }

    @Override
    public TrackSlot copyOf(final TrackSlot slot) {
        return ImmutableTrackSlot.copyOf(slot);
    }

    @Override
    public TrackSlot createEmpty(final String disc, final int number) {
        return buildEmpty(disc, number)
                .build();
    }

    @SuppressWarnings({"MethodMayBeStatic", "MethodReturnOfConcreteClass"}) // helper method, used to avoid feature envy
    private ImmutableTrackSlot.Builder buildEmpty(final String disc, final int number) {
        return ImmutableTrackSlot.builder()
                .disc(disc)
                .number(number);
    }

    @Override
    public TrackSlot createWithTrack(final String disc, final int number, final Track track) {
        return buildEmpty(disc, number)
                .track(track)
                .build();
    }

    @Override
    public TrackSlot create(final Key key) {
        logger.warn("Creation by key is not supported. Redirecting to createEmpty.");
        return createEmpty();
    }

    @Override
    public TrackSlot createEmpty() {
        return VALIDATOR.handleException()
                .unsupportedOperation("Cannot create an empty track slot. " +
                        "Must at least provide a number and disc");
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("config", this.config)
                .toString();
    }
}
