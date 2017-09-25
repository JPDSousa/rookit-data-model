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

/**
 * Interface that stores database field metadata for Tracks
 * 
 * @author Joao
 *
 */
public interface DatabaseFields {
	String HIDDEN_TRACK = "hidden_track";
	String TYPE = "track_type";
	String PATH = "path";
	String TITLE = "title";
	String MAIN_ARTISTS = "main_artists";
	String FEATURES = "features";
	String PRODUCERS = "producers";
	String VERSION_ARTISTS = "version_artists";
	String VERSION_TOKEN = "version_token";
	String TOKENS = "tokens";
	String BPM = "bpm";
	String LYRICS = "lyrics";
	String DURATION = "duration";
	String ORIGINAL = "original";
	String VERSION_TYPE = "version_type";
	String EXPLICIT = "explicit";
}
