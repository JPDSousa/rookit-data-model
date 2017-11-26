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

import static org.rookit.dm.artist.DatabaseFields.*;

import java.time.LocalDate;
import java.util.Collection;

import org.rookit.dm.genre.Genreable;
import org.rookit.dm.play.Playable;
import org.smof.annnotations.ForceInspection;
import org.smof.annnotations.SmofIndex;
import org.smof.annnotations.SmofIndexField;
import org.smof.annnotations.SmofIndexes;
import org.smof.gridfs.SmofGridRef;
import org.smof.index.IndexType;


/**
 * Represents a musical artist.
 * 
 * A musical artist is the entity that creates both albums and tracks, being related to albums as
 * an album artist (author) or to tracks as main artist, feature artist, or even as remix artist or
 * cover artist.
 */
@SmofIndexes({
	@SmofIndex(fields = {
			@SmofIndexField(name = NAME, type = IndexType.ASCENDING),
			@SmofIndexField(name = TYPE, type = IndexType.ASCENDING)}, 
			unique=true),
	@SmofIndex(fields = {@SmofIndexField(name = NAME, type = IndexType.TEXT)}),
})
@ForceInspection({MusicianImpl.class, GroupArtistImpl.class})
public interface Artist extends Genreable, Playable, Comparable<Artist>, ArtistSetter<Void> {
	
	/**
	 * String representation of an unknown artist. This constant may be used only
	 * when the artist of a track or album is required but unknown.
	 */
	String UNKNOWN_ARTISTS = "Unknown Artists";
	
	/**
	 * TODO: Move this constant to a suitable place
	 */
	public static final String[] SUSPICIOUS_NAME_CHARSEQS = new String[]{"- ", " -",  "[", "]", "{", "}", "~", "|", "�", ")", "("};
	
	public TypeArtist getType();
	
	/**
	 * Returns the artist name
	 * 
	 * @return artist name as a string.
	 */
	public String getName();
	
	/**
	 * Returns the set of artists related to this artist
	 * 
	 * @return set of artists related to this artist
	 */
	public Iterable<Artist> getRelatedArtists();
	
	/**
	 * Returns the origin of this artist (location where the artist came from)
	 * 
	 * @return the artist's origin
	 */
	public String getOrigin();
	
	public Collection<String> getAliases();

	public LocalDate getBeginDate();
	
	public LocalDate getEndDate();
	
	public String getIPI();
	
	public String getISNI();
	
	public SmofGridRef getPicture();
}
