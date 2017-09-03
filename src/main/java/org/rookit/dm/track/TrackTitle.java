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

import java.util.Set;

import org.rookit.dm.artist.Artist;
import org.rookit.dm.utils.PrintUtils;
import org.rookit.utils.print.TypeFormat;

@SuppressWarnings("javadoc")
public class TrackTitle {

	private final String title;

	private String artists;
	private String extras;
	private String feats;

	private String tokens;
	private String hiddenTrack;


	public TrackTitle(final String title) {
		this.title = title;
	}

	public TrackTitle appendHiddenTrack(String hiddenTrack){
		this.hiddenTrack = hiddenTrack;
		return this;
	}

	public TrackTitle appendArtists(Set<Artist> artists){
		this.artists = PrintUtils.getIterableAsString(artists, TypeFormat.TITLE, Artist.UNKNOWN);
		return this;
	}

	public TrackTitle appendExtras(String extras){
		this.extras = extras;
		return this;
	}

	public TrackTitle appendFeats(Set<Artist> artists){
		this.feats = PrintUtils.getIterableAsString(artists, TypeFormat.TITLE);
		return this;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(32);
		//artists
		if(artists != null){
			if(!artists.isEmpty()){
				builder.append(artists);
			}
			else{
				builder.append(Artist.UNKNOWN);
			}
			builder.append(" - ");
		}
		//title
		builder.append(title);
		//hidden track
		if(hiddenTrack != null && !hiddenTrack.isEmpty()){
			builder.append(" (+ ").append(hiddenTrack).append(')');
		}
		//feats
		if(feats != null && !feats.isEmpty()){
			builder.append(" (feat. ");
			builder.append(feats);
			builder.append(')');
		}
		//add tokens
		if(tokens != null && !tokens.isEmpty()){
			builder.append(" (").append(tokens).append(')');
		}
		//extras
		if(extras != null && !extras.isEmpty()){ 
			builder.append(" (");
			builder.append(extras);
			builder.append(')');
		}

		return builder.toString();
	}

	public String getTitle() {
		return title;
	}

	public String getArtists() {
		return artists;
	}

	public String getExtras() {
		return extras;
	}

	public String getFeats() {
		return feats;
	}

	public String getTokens() {
		return tokens;
	}

	public String getHiddenTrack() {
		return hiddenTrack;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((artists == null) ? 0 : artists.hashCode());
		result = prime * result + ((extras == null) ? 0 : extras.hashCode());
		result = prime * result + ((feats == null) ? 0 : feats.hashCode());
		result = prime * result + ((hiddenTrack == null) ? 0 : hiddenTrack.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((tokens == null) ? 0 : tokens.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		TrackTitle other = (TrackTitle) obj;
		return toString().equals(other.toString());
	}

}
