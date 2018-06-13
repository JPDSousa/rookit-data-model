package org.rookit.dm.artist.external;

import com.google.common.base.MoreObjects;
import org.rookit.dm.utils.DataModelValidator;
import org.rookit.utils.log.validator.Validator;

import javax.annotation.Nullable;
import java.util.Optional;

final class MutableExternalIdentifiersImpl implements MutableExternalIdentifiers {

    private static final Validator VALIDATOR = DataModelValidator.getDefault();

    private final String isni;

    @Nullable
    private String ipi;

    MutableExternalIdentifiersImpl(final String isni) {
        this.isni = isni;
    }

    @Override
    public void setIpi(final String ipi) {
        VALIDATOR.checkArgument().isNotEmpty(ipi, "ipi");
        this.ipi = ipi;
    }

    @Override
    public Optional<String> getIPI() {
        return Optional.ofNullable(this.ipi);
    }

    @Override
    public String getISNI() {
        return this.isni;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("isni", this.isni)
                .add("ipi", Optional.ofNullable(this.ipi))
                .toString();
    }
}
