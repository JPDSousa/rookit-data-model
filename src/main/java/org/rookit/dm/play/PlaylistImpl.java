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
package org.rookit.dm.play;

import java.io.ByteArrayInputStream;
import java.util.Set;
import java.util.stream.Stream;

import org.rookit.dm.track.Track;
import org.smof.annnotations.SmofArray;
import org.smof.annnotations.SmofObject;
import org.smof.annnotations.SmofString;
import org.smof.gridfs.SmofGridRef;
import org.smof.gridfs.SmofGridRefFactory;
import org.smof.parsers.SmofType;

import com.google.common.collect.Sets;

import static org.rookit.dm.play.DatabaseFields.*;

class PlaylistImpl extends AbstractPlayable implements Playlist {
	
	@SmofString(name = NAME)
	private final String name;
	
	@SmofArray(name = TRACKS, type = SmofType.OBJECT)
	private final Set<Track> tracks;
	
	@SmofObject(name = IMAGE, bucketName = IMAGE_BUCKET, preInsert = false)
	private final SmofGridRef image;
	
	PlaylistImpl(String name) {
		this.name = name;
		this.tracks = Sets.newLinkedHashSet();
		image = SmofGridRefFactory.newEmptyRef();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Stream<Track> streamTracks() {
		return tracks.stream();
	}

	@Override
	public Iterable<Track> getTracks() {
		return tracks;
	}

	@Override
	public Void addTrack(Track track) {
		VALIDATOR.checkArgumentNotNull(track, "Cannot add a null track");
		tracks.add(track);
		return null;
	}

	@Override
	public boolean removeTrack(Track track) {
		return tracks.remove(track);
	}

	@Override
	public SmofGridRef getImage() {
		return image;
	}

	@Override
	public Void setImage(byte[] image) {
		this.image.attachByteArray(new ByteArrayInputStream(image));
		return null;
	}

}
