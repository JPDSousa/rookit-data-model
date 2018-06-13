package org.rookit.dm.bistream;

import com.google.common.base.MoreObjects;
import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.bistream.BiStreamFactory;
import org.rookit.api.dm.key.Key;
import org.rookit.dm.utils.DataModelValidator;
import org.rookit.utils.log.validator.Validator;

final class GenericFileBiStreamFactoryAdapter implements BiStreamFactory<Key> {

    private static final Validator VALIDATOR = DataModelValidator.getDefault();
    private static final Logger logger = VALIDATOR.getLogger(GenericFileBiStreamFactoryAdapter.class);

    private final FileBiStreamFactory factory;

    @Inject
    private GenericFileBiStreamFactoryAdapter(final FileBiStreamFactory factory) {
        this.factory = factory;
    }

    @Override
    public BiStream create(final Key key) {
        if (key instanceof FileBiStreamKey) {
            return this.factory.create((FileBiStreamKey) key);
        }

        logger.warn("Received key {}, which is not suitable for this factory. Creating empty.", key);
        return createEmpty();
    }

    @Override
    public BiStream createEmpty() {
        return this.factory.createEmpty();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("factory", factory)
                .toString();
    }
}
