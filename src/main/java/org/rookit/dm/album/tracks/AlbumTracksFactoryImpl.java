package org.rookit.dm.album.tracks;

import com.google.common.base.MoreObjects;
import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;
import org.rookit.api.dm.album.tracks.AlbumTracks;
import org.rookit.api.dm.album.tracks.AlbumTracksFactory;
import org.rookit.api.dm.key.Key;
import org.rookit.dm.album.disc.MutableDiscFactory;
import org.rookit.dm.utils.DataModelValidator;
import org.rookit.utils.log.validator.Validator;

final class AlbumTracksFactoryImpl implements AlbumTracksFactory {

    private static final Validator VALIDATOR = DataModelValidator.getDefault();
    private static final Logger logger = VALIDATOR.getLogger(AlbumTracksFactoryImpl.class);

    private final MutableDiscFactory discFactory;

    @Inject
    private AlbumTracksFactoryImpl(final MutableDiscFactory discFactory) {
        this.discFactory = discFactory;
    }

    @Override
    public AlbumTracks create(final Key key) {
        logger.warn("Creation by key is not supported for album tracks. Using createEmpty instead.");
        return createEmpty();
    }

    @Override
    public AlbumTracks createEmpty() {
        return new MutableAlbumTracksImpl(this.discFactory);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("discFactory", this.discFactory)
                .toString();
    }
}
