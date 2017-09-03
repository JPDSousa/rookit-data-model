package org.rookit.dm.parser;

import org.smof.annnotations.SmofBuilder;
import org.smof.annnotations.SmofIndex;
import org.smof.annnotations.SmofIndexField;
import org.smof.annnotations.SmofIndexes;
import org.smof.annnotations.SmofNumber;
import org.smof.annnotations.SmofParam;
import org.smof.annnotations.SmofString;
import org.smof.element.AbstractElement;
import org.smof.element.Element;
import org.smof.index.IndexType;

@SuppressWarnings("javadoc")
@SmofIndexes({
	@SmofIndex(fields = {@SmofIndexField(name = "value", type = IndexType.TEXT)}, unique = true)
})
public class IgnoreField extends AbstractElement implements Element {

	public static final String VALUE = "value";
	public static final String OCCURRENCES = "occurrences";

	public static IgnoreField create(String value) {
		return new IgnoreField(value.toLowerCase(), 1);
	}

	@SmofString(name=VALUE, indexKey = "main", indexType = IndexType.TEXT)
	private final String value;
	@SmofNumber(name=OCCURRENCES)
	private final int occurrences;

	@SmofBuilder
	private IgnoreField(@SmofParam(name=VALUE) String value, 
			@SmofParam(name=OCCURRENCES) Integer occurrences) {
		this.value = value;
		this.occurrences = occurrences;
	}

	public int getOccurrences() {
		return occurrences;
	}

	public String getValue() {
		return value;
	}
}
