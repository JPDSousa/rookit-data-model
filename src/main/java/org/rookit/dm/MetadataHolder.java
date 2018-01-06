package org.rookit.dm;

import java.util.Map;

@SuppressWarnings("javadoc")
public interface MetadataHolder extends RookitModel {
	
	String EXTERNAL_META = "external_meta";

	Map<String, Object> getExternalMetadata(String key);
	void putExternalMetadata(String key, Map<String, Object> value);

	Map<String, Map<String, Object>> getExternalMetadata();
	
}
