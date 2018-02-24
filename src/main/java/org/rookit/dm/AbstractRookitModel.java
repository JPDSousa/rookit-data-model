package org.rookit.dm;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import org.rookit.api.dm.RookitModel;
import org.rookit.dm.utils.DataModelValidator;

@SuppressWarnings("javadoc")
public abstract class AbstractRookitModel implements RookitModel {

	protected static final DataModelValidator VALIDATOR = DataModelValidator.getDefault();
	
	@Id
	private ObjectId _id;
	
	protected AbstractRookitModel() {
		this(new ObjectId());
	}

	protected AbstractRookitModel(final ObjectId initialID) {
		this._id = initialID;
	}

	@Override
	public ObjectId getId() {
		return _id;
	}

	@Override
	public void setId(final ObjectId id) {
		VALIDATOR.checkArgumentNotNull(id, "The id cannot be null");
		this._id = id;
	}

	@Override
	public String getIdAsString() {
		return getId().toHexString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		return result * prime;
	}

	@Override
	public boolean equals(Object obj) {
		return this == obj || obj != null && getClass() == obj.getClass();
	}

	@Override
	public LocalDateTime getStorageTime() {
		return LocalDateTime.ofInstant(getId().getDate().toInstant(), ZoneId.systemDefault());
	}	
	
}
