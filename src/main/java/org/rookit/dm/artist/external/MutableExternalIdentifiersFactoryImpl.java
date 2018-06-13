package org.rookit.dm.artist.external;

import com.google.common.base.MoreObjects;
import com.google.inject.Inject;
import org.dozer.Mapper;
import org.rookit.api.dm.artist.external.ExternalIdentifiers;
import org.rookit.api.dm.artist.external.ExternalIdentifiersFactory;
import org.rookit.api.dm.key.Key;

final class MutableExternalIdentifiersFactoryImpl implements MutableExternalIdentifiersFactory {

    private final ExternalIdentifiersFactory factory;
    private final Mapper mapper;

    @Inject
    MutableExternalIdentifiersFactoryImpl(final ExternalIdentifiersFactory factory,
                                          final Mapper mapper) {
        this.factory = factory;
        this.mapper = mapper;
    }

    private MutableExternalIdentifiers fromExternalIdentifiers(final ExternalIdentifiers identifiers) {
        if (identifiers instanceof  MutableExternalIdentifiers) {
            return (MutableExternalIdentifiers) identifiers;
        }
        final MutableExternalIdentifiers mutableIdentifiers = new MutableExternalIdentifiersImpl(identifiers.getISNI());
        this.mapper.map(identifiers, mutableIdentifiers);
        return mutableIdentifiers;
    }

    @Override
    public MutableExternalIdentifiers create(final String isni) {
        return fromExternalIdentifiers(this.factory.create(isni));
    }

    @Override
    public MutableExternalIdentifiers create(final String isni, final String ipi) {
        return fromExternalIdentifiers(this.factory.create(isni, ipi));
    }

    @Override
    public MutableExternalIdentifiers create(final Key key) {
        return fromExternalIdentifiers(this.factory.create(key));
    }

    @Override
    public MutableExternalIdentifiers createEmpty() {
        return fromExternalIdentifiers(this.factory.createEmpty());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("factory", this.factory)
                .add("mapper", this.mapper)
                .toString();
    }
}
