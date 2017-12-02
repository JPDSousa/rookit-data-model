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
package org.rookit.dm.album;

import java.util.HashSet;
import java.util.Set;

import org.rookit.dm.artist.Artist;
import org.rookit.dm.track.Track;

class VariousArtistAlbum extends AbstractAlbum {

	public VariousArtistAlbum(String title, TypeRelease type) {
		super(TypeAlbum.VA, title, type, null);
	}

	@Override
	public Set<Artist> getArtists() {
		final Set<Artist> artists = new HashSet<>();
		
		for(TrackSlot track : getTracks()){
			track.getTrack().getMainArtists().forEach(a -> artists.add(a));
		}
		
		return artists;
	}

	@Override
	public String toString() {
		return getFullTitle();
	}

	

}
