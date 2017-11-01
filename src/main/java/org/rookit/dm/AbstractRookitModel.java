package org.rookit.dm;

import java.util.Map;

import org.bson.Document;
import org.smof.annnotations.SmofObject;
import org.smof.element.AbstractElement;
import org.smof.parsers.SmofType;

import com.google.common.collect.Maps;

@SuppressWarnings("javadoc")
public abstract class AbstractRookitModel extends AbstractElement implements RookitModel {
	
	@SmofObject(name = EXTERNAL_META, mapValueType = SmofType.OBJECT)
	protected final Map<String, Document> externalMetadata;
	
	public AbstractRookitModel() {
		externalMetadata = Maps.newHashMap();
	}

	@Override
	public Document getExternalMetadata(String key) {
		return externalMetadata.get(key);
	}

	@Override
	public Map<String, Document> getExternalMetadata() {
		return externalMetadata;
	}

	@Override
	public void putExternalMetadata(String key, Document value) {
		externalMetadata.put(key, value);
	}
	
}
