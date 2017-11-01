package org.rookit.dm;

import java.util.Map;

import org.bson.Document;
import org.smof.element.Element;

@SuppressWarnings("javadoc")
public interface RookitModel extends Element {
	
	String EXTERNAL_META = "external_meta";
	
	Document getExternalMetadata(String key);
	void putExternalMetadata(String key, Document value);
	
	Map<String, Document> getExternalMetadata();

}
