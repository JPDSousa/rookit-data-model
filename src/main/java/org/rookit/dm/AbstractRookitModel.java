
package org.rookit.dm;

import com.google.common.base.MoreObjects;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import org.rookit.api.dm.RookitModel;
import org.rookit.dm.utils.DataModelValidator;
import org.rookit.utils.VoidUtils;
import org.rookit.utils.log.validator.Validator;

import java.time.ZoneId;
import java.util.Optional;

@SuppressWarnings("javadoc")
public abstract class AbstractRookitModel implements RookitModel {

    private static final ZoneId ZONE_ID = ZoneId.systemDefault();

    private static final long serialVersionUID = -5485704783547237549L;

    protected static final Validator VALIDATOR = DataModelValidator.getDefault();

    @Id
    private ObjectId _id;

    protected AbstractRookitModel() {
        this(null);
    }

    protected AbstractRookitModel(final ObjectId initialId) {
        this._id = initialId;
    }

    @Override
    public boolean equals(final Object obj) {
        return (this == obj) || ((obj != null) && (getClass() == obj.getClass()));
    }

    @Override
    public Optional<String> getId() {
        return Optional.ofNullable(this._id)
                .map(ObjectId::toHexString);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        final int result = 1;
        return result * prime;
    }

    @Override
    public Void setId(final String id) {
        VALIDATOR.checkArgument().isNotNull(id, "id");
        this._id = new ObjectId(id);
        return VoidUtils.returnVoid();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("_id", this._id)
                .toString();
    }
}
