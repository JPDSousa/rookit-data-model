package org.rookit.dm.album.release;

import com.google.common.base.MoreObjects;
import org.rookit.api.dm.album.TypeRelease;
import org.rookit.dm.utils.DataModelValidator;
import org.rookit.utils.log.validator.Validator;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Optional;

final class MutableReleaseImpl implements MutableRelease {

    private static final Validator VALIDATOR = DataModelValidator.getDefault();
    private static final long serialVersionUID = -8787468551172246738L;

    private final TypeRelease type;

    @Nullable
    private LocalDate date;

    MutableReleaseImpl(final TypeRelease type) {
        this(type, null);
    }

    MutableReleaseImpl(final TypeRelease type, final LocalDate date) {
        this.type = type;
        this.date = date;
    }

    @Override
    public void setReleaseDate(final LocalDate releaseDate) {
        VALIDATOR.checkArgument().isNotNull(releaseDate, "releaseDate");
        this.date = releaseDate;
    }

    @Override
    public TypeRelease type() {
        return this.type;
    }

    @Override
    public Optional<LocalDate> date() {
        return Optional.ofNullable(this.date);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("releaseType", this.type)
                .add("date", Optional.ofNullable(this.date))
                .toString();
    }
}
