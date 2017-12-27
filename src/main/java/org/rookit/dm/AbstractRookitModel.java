package org.rookit.dm;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

@SuppressWarnings("javadoc")
public abstract class AbstractRookitModel implements RookitModel {

	@Id
	private ObjectId _id;
	
	protected AbstractRookitModel() {
		this(new ObjectId());
	}

	protected AbstractRookitModel(final ObjectId initialID) {
		this(initialID, false);
	}
	
	protected AbstractRookitModel(ObjectId id, boolean allowInitialNullId) {
		if(!allowInitialNullId && id == null) {
			throw new IllegalArgumentException("Id cannot be null");
		}
		this._id = id;
	}

	@Override
	public ObjectId getId() {
		return _id;
	}

	@Override
	public void setId(final ObjectId id) {
		if(id == null) {
			throw new IllegalArgumentException("Id cannot be null");
		}
		this._id = id;
	}

	@Override
	public String getIdAsString() {
		return _id.toHexString();
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
		return LocalDateTime.ofInstant(_id.getDate().toInstant(), ZoneId.systemDefault());
	}	
	
}
