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

import static org.rookit.api.dm.track.TrackFields.*;

import java.util.Map;
import java.util.Optional;

import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.factory.RookitFactory;
import org.rookit.api.dm.track.Track;
import org.rookit.api.dm.track.TypeTrack;
import org.rookit.api.dm.track.TypeVersion;
import org.rookit.api.dm.track.factory.TrackFactory;
import org.rookit.dm.factory.AbstractRookitFactory;
import org.rookit.dm.track.factory.TrackBiStream;

import com.google.inject.Inject;

@SuppressWarnings("javadoc")
public class TrackFactoryImpl extends AbstractRookitFactory<Track> implements TrackFactory {

	@Inject
	private TrackFactoryImpl(@TrackBiStream final RookitFactory<BiStream> biStreamFactory){
		super(Optional.of(biStreamFactory));
	}

	@Override
	public final OriginalTrackImpl createOriginalTrack(String title) {
		final BiStream path = createEmptyBiStream();
		return new OriginalTrackImpl(title, path);
	}

	@Override
	public final VersionTrackImpl createVersionTrack(TypeVersion versionType, Track original) {
		final BiStream path = createEmptyBiStream();
		return new VersionTrackImpl(original, versionType, path);
	}

	@Override
	public final Track createTrack(TypeTrack type, String title, Track original, TypeVersion versionType) {
		final BiStream path = createEmptyBiStream();
		if(type == TypeTrack.VERSION) {
			return new VersionTrackImpl(original, versionType, path);
		}
		return new OriginalTrackImpl(title, path);
	}

	@Override
	public Track createEmpty() {
		VALIDATOR.invalidOperation("Cannot create an empty track");
		return null;
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
		VALIDATOR.runtimeException("Invalid arguments: " + data);
		return null;
	}
}
