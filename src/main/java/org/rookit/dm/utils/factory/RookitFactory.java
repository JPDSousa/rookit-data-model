package org.rookit.dm.utils.factory;

import java.util.Map;

@SuppressWarnings("javadoc")
public interface RookitFactory<T> {

	T createEmpty();
	
	T create(Map<String, Object> data);
	
}
