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

import java.util.Set;

import org.rookit.dm.artist.Artist;
import org.rookit.dm.genre.Genreable;
import org.rookit.dm.play.Playable;
import org.smof.annnotations.ForceInspection;
import org.smof.annnotations.SmofIndex;
import org.smof.annnotations.SmofIndexField;
import org.smof.annnotations.SmofIndexes;
import org.smof.element.Element;
import org.smof.gridfs.SmofGridRef;
import org.smof.index.IndexType;

@SuppressWarnings("javadoc")
@SmofIndexes({
	@SmofIndex(fields={@SmofIndexField(name=PATH, type = IndexType.ASCENDING)}, unique = true),
	@SmofIndex(fields={@SmofIndexField(name=TITLE, type = IndexType.TEXT)})
})
@ForceInspection({OriginalTrack.class, VersionTrack.class})
public interface Track extends Playable, Genreable, Element {

	String AUDIO = "audio";
	
	short MAX_BPM = 400;
	String[] SUSPICIOUS_TITLE_CHARSEQS = new String[]{"- ", " -",  "[", "]", "_", "{", "}", "~", "|", "�", " vs ", " vs. "};
	
	TypeTrack getType();
	
	TrackTitle getTitle();
	void setTitle(String title);
	
	void setTitle(TrackTitle title);

	TrackTitle getLongFullTitle();
	TrackTitle getFullTitle();
	
	Iterable<Artist> getMainArtists();
	void setMainArtists(Set<Artist> artists);
	void addMainArtist(Artist artist);
	
	Iterable<Artist> getFeatures();
	void setFeatures(Set<Artist> features);
	void addFeature(Artist artist);
	
	void setHiddenTrack(String hiddenTrack);
	String getHiddenTrack();
	
	void setProducers(Set<Artist> producer);
	Iterable<Artist> getProducers();
	void addProducer(Artist producer);
	
	VersionTrack getAsVersionTrack();
	boolean isVersionTrack();
	
	void setBPM(short bpm);
	short getBPM();
	
	String getLyrics();
	void setLyrics(String lyrics);
	
	void setExplicit(boolean explicit);
	boolean isExplicit();
	
	SmofGridRef getPath();
	
	@Override
	boolean equals(Object track);
	
	@Override
	int hashCode();
}
