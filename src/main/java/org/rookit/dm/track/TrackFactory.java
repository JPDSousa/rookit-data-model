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
package org.rookit.dm.track;

import static org.rookit.dm.track.DatabaseFields.*;

import org.smof.annnotations.SmofBuilder;
import org.smof.annnotations.SmofParam;

@SuppressWarnings("javadoc")
public class TrackFactory {

	private static TrackFactory singleton;


	public static TrackFactory getDefault(){
		if(singleton == null) {
			singleton = new TrackFactory();
		}
		return singleton;
	}

	private TrackFactory(){}

	public final OriginalTrack createOriginalTrack(String title) {
		return new OriginalTrack(title);
	}

	public final VersionTrack createVersionTrack(TypeVersion versionType, Track original) {
		return new VersionTrack(original, versionType);
	}

	@SmofBuilder
	public final Track createTrack(@SmofParam(name = TYPE) TypeTrack type, 
			@SmofParam(name = TITLE) String title,
			@SmofParam(name = ORIGINAL) Track original,
			@SmofParam(name = VERSION_TOKEN) TypeVersion versionType) {
		if(type == TypeTrack.VERSION) {
			return new VersionTrack(original, versionType);
		}
		return new OriginalTrack(title);
	}
}
