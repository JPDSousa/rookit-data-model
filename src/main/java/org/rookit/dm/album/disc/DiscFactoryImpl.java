package org.rookit.dm.album.disc;

import com.google.common.base.MoreObjects;
import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;
import org.rookit.api.dm.album.disc.Disc;
import org.rookit.api.dm.album.disc.DiscFactory;
import org.rookit.api.dm.album.slot.TrackSlotFactory;
import org.rookit.api.dm.key.Key;
import org.rookit.dm.album.disc.config.DiscConfig;
import org.rookit.dm.utils.DataModelValidator;
import org.rookit.utils.log.validator.Validator;

final class DiscFactoryImpl implements DiscFactory {

    private static final Validator VALIDATOR = DataModelValidator.getDefault();
    private static final Logger logger = VALIDATOR.getLogger(DiscFactoryImpl.class);

    private final TrackSlotFactory trackSlotFactory;
    private final DiscConfig discConfig;

    @Inject
    private DiscFactoryImpl(final TrackSlotFactory trackSlotFactory,
                            final DiscConfig discConfig) {
        this.trackSlotFactory = trackSlotFactory;
        this.discConfig = discConfig;
    }

    @Override
    public Disc create(final String name) {
        return new MutableDiscImpl(name, this.trackSlotFactory);
    }

    @Override
    public Disc create(final Key key) {
        logger.warn("Key creation is not supported for discs. Creating empty instead");
        return createEmpty();
    }

    @Override
    public Disc createEmpty() {
        return this.discConfig.defaultName()
                .map(this::create)
                .orElseThrow(() -> VALIDATOR.handleException()
                        .runtimeException("Cannot create an empty Disc as no default name is set."));
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("trackSlotFactory", this.trackSlotFactory)
                .add("discConfig", this.discConfig)
                .toString();
    }
}
