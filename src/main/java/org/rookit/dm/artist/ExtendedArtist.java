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
package org.rookit.dm.artist;

import org.rookit.dm.album.Album;
import org.rookit.dm.track.Track;

/**
 * This interface offers an extended set of methods related to the artist definition. 
 * This methods were separated from the original interface as they allow for data loop holes. 
 * 
 * @author Joao
 *
 */
public interface ExtendedArtist extends Artist {
	
	/**
	 * Returns the list of albums which this artist was author
	 * 
	 * @return the set of albums which this artist co-wrote
	 */
	public Iterable<Album> getAlbuns();
	
	/**
	 * Returns all the tracks this artist participated in.
	 * 
	 * @return the set of tracks this artist participated in.
	 */
	public Iterable<Track> getTracks();

}
