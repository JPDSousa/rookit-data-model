package org.rookit.dm;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;

@SuppressWarnings("javadoc")
public interface RookitModel {
	
	String ID = "_id";

	ObjectId getId();

	String getIdAsString();

	void setId(final ObjectId id);

	LocalDateTime getStorageTime();

}
