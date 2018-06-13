package org.rookit.dm.artist.external;

import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;
import org.rookit.api.dm.artist.external.ExternalIdentifiers;
import org.rookit.api.dm.artist.external.ExternalIdentifiersFactory;
import org.rookit.api.dm.key.Key;
import org.rookit.dm.utils.DataModelValidator;
import org.rookit.utils.log.validator.Validator;

final class ExternalIdentifiersFactoryImpl implements ExternalIdentifiersFactory {

    private static final Validator VALIDATOR = DataModelValidator.getDefault();
    private static final Logger logger = VALIDATOR.getLogger(ExternalIdentifiersFactoryImpl.class);

    @Inject
    private ExternalIdentifiersFactoryImpl() { }

    @Override
    public ExternalIdentifiers create(final String isni) {
        VALIDATOR.checkArgument().isNotEmpty(isni, "isni");
        return new MutableExternalIdentifiersImpl(isni);
    }

    @Override
    public ExternalIdentifiers create(final String isni, final String ipi) {
        VALIDATOR.checkArgument().isNotEmpty(isni, "isni");
        VALIDATOR.checkArgument().isNotEmpty(ipi, "ipi");
        final MutableExternalIdentifiers externalIdentifiers = new MutableExternalIdentifiersImpl(isni);
        externalIdentifiers.setIpi(isni);
        return externalIdentifiers;
    }

    @Override
    public ExternalIdentifiers create(final Key key) {
        logger.info("Creation by key is not supported, creating empty instead");
        return createEmpty();
    }

    @Override
    public ExternalIdentifiers createEmpty() {
        final String errorMessage = "Cannot create an empty externalIdentifiers as ISNI is required";
        return VALIDATOR.handleException().unsupportedOperation(errorMessage);
    }
}
