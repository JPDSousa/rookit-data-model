package org.rookit.dm.album.release;

import com.google.common.base.MoreObjects;
import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;
import org.rookit.api.dm.album.TypeRelease;
import org.rookit.api.dm.album.release.Release;
import org.rookit.api.dm.album.release.ReleaseFactory;
import org.rookit.api.dm.key.Key;
import org.rookit.dm.album.release.config.ReleaseConfig;
import org.rookit.dm.utils.DataModelValidator;
import org.rookit.utils.log.validator.Validator;

import java.time.LocalDate;

final class ReleaseFactoryImpl implements ReleaseFactory {

    private static final Validator VALIDATOR = DataModelValidator.getDefault();
    private static final Logger logger = VALIDATOR.getLogger(ReleaseFactoryImpl.class);

    private final ReleaseConfig config;

    @Inject
    private ReleaseFactoryImpl(final ReleaseConfig config) {
        this.config = config;
    }

    @Override
    public Release releaseOf(final TypeRelease release) {
        return new MutableReleaseImpl(release);
    }

    @Override
    public Release released(final TypeRelease type, final LocalDate date) {
        return new MutableReleaseImpl(type, date);
    }

    @Override
    public Release releasedToday(final TypeRelease type) {
        return released(type, LocalDate.now());
    }

    @Override
    public Release create(final Key key) {
        logger.warn("Creation by key is not supported. Falling back to empty creation.");
        return createEmpty();
    }

    @Override
    public Release createEmpty() {
        return this.config.defaultType()
                .map(this::releaseOf)
                .orElseThrow(() -> VALIDATOR.handleException()
                        .runtimeException("Cannot create an empty Release as no default type is set"));
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("config", this.config)
                .toString();
    }
}
