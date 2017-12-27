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

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexed;
import org.rookit.dm.AbstractRookitModel;
import org.rookit.dm.RookitModel;

//@SmofIndexes({
//	@SmofIndex(fields = {@SmofIndexField(name = "value", type = IndexType.ASCENDING)}, unique = true),
//})
@Entity
@SuppressWarnings("javadoc")
public class TrackFormat extends AbstractRookitModel implements RookitModel {
	
	public static final String VALUE = "value";
	public static final String OCCURRENCES = "occurrences";

	public static TrackFormat create(String value) {
		return new TrackFormat(value.toLowerCase(), 1);
	}

	@Indexed(options = @IndexOptions(unique = true))
	private final String value;

	private final int occurrences;

	private TrackFormat(String value, Integer occurrences) {
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
