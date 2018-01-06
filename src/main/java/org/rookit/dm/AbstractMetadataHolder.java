package org.rookit.dm;

import java.util.Map;

import org.mongodb.morphia.annotations.Embedded;

import com.google.common.collect.Maps;

@SuppressWarnings("javadoc")
public abstract class AbstractMetadataHolder extends AbstractRookitModel implements MetadataHolder {

	@Embedded
	private final Map<String, Map<String, Object>> externalMetadata;

	public AbstractMetadataHolder() {
		externalMetadata = Maps.newHashMap();
	}

	@Override
	public Map<String, Object> getExternalMetadata(String key) {
		return externalMetadata.get(key);
	}

	@Override
	public Map<String, Map<String, Object>> getExternalMetadata() {
		return externalMetadata;
	}

	@Override
	public void putExternalMetadata(String key, Map<String, Object> value) {
		externalMetadata.put(key, value);
	}
}
