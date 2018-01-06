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

import java.io.Serializable;
import java.util.Map;

import org.rookit.dm.utils.bistream.BiStream;
import org.rookit.dm.utils.bistream.BiStreamFoF;
import org.rookit.dm.utils.bistream.BufferBiStreamFactory;
import org.rookit.dm.utils.factory.RookitFactory;

@SuppressWarnings("javadoc")
public class TrackFactory implements Serializable, BiStreamFoF, RookitFactory<Track> {

	private static final long serialVersionUID = 1L;
	
	private static TrackFactory singleton;

	public static TrackFactory getDefault(){
		if(singleton == null) {
			singleton = new TrackFactory();
		}
		return singleton;
	}

	private RookitFactory<BiStream> biStreamFactory;
	
	private TrackFactory(){
		this.biStreamFactory = new BufferBiStreamFactory();
	}

	public final OriginalTrack createOriginalTrack(String title) {
		return new OriginalTrack(title);
	}

	public final VersionTrack createVersionTrack(TypeVersion versionType, Track original) {
		return new VersionTrack(original, versionType);
	}

	public final Track createTrack(TypeTrack type, String title, Track original, TypeVersion versionType) {
		if(type == TypeTrack.VERSION) {
			return new VersionTrack(original, versionType);
		}
		return new OriginalTrack(title);
	}

	@Override
	public RookitFactory<BiStream> getBiStreamFactory() {
		return biStreamFactory;
	}

	@Override
	public void setBiStreamFactory(RookitFactory<BiStream> factory) {
		this.biStreamFactory = factory;
	}

	@Override
	public Track createEmpty() {
		throw new UnsupportedOperationException("Cannot create an empty track");
	}

	@Override
	public Track create(Map<String, Object> data) {
		final Object type = data.get(TYPE);
		if(type != null && type instanceof TypeTrack) {
			switch((TypeTrack) type) {
			case ORIGINAL:
				final Object title = data.get(TITLE);
				if(title != null && title instanceof String) {
					return createOriginalTrack((String) title);
				}
				break;
			case VERSION:
				final Object original = data.get(ORIGINAL);
				final Object versionType = data.get(VERSION_TYPE);
				if(original != null && original instanceof Track
						&& versionType != null && versionType instanceof TypeVersion) {
					return createVersionTrack((TypeVersion) versionType, (Track) original);
				}
				break;
			default:
				break;
			}
		}
		throw new RuntimeException("Invalid arguments: " + data);
	}
}
