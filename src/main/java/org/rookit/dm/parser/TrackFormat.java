/*******************************************************************************
 * Copyright (C) 2017 Joao Sousa
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
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

@SmofIndexes({
	@SmofIndex(fields = {@SmofIndexField(name = "value", type = IndexType.ASCENDING)}, unique = true),
})
@SuppressWarnings("javadoc")
public class TrackFormat extends AbstractElement implements Element {
	
	public static final String VALUE = "value";
	public static final String OCCURRENCES = "occurrences";

	public static TrackFormat create(String value) {
		return new TrackFormat(value.toLowerCase(), 1);
	}

	@SmofString(name=VALUE, indexKey = "main", indexType = IndexType.TEXT)
	private final String value;
	@SmofNumber(name=OCCURRENCES)
	private final int occurrences;

	@SmofBuilder
	private TrackFormat(@SmofParam(name=VALUE) String value, 
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
