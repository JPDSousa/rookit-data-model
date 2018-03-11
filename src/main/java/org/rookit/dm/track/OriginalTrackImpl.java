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

import static org.rookit.api.dm.track.TrackFields.MAIN_ARTISTS;
import static org.rookit.api.dm.track.TrackFields.TITLE;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Generated;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.track.Track;
import org.rookit.api.dm.track.TrackTitle;
import org.rookit.api.dm.track.TypeTrack;
import org.rookit.api.dm.track.VersionTrack;
import org.rookit.utils.VoidUtils;

import com.google.common.collect.Sets;

@SuppressWarnings("javadoc")
@Entity("Track")
public final class OriginalTrackImpl extends AbstractTrack implements Track {

	@Property(TITLE)
	private String title;
	
	@Reference(value = MAIN_ARTISTS, idOnly = true)
	private Set<Artist> mainArtists;

	@Deprecated
	private OriginalTrackImpl() {
		super(TypeTrack.ORIGINAL, null);
		mainArtists = Collections.emptySet();
	}
	
	OriginalTrackImpl(String title, BiStream biStream) {
		super(TypeTrack.ORIGINAL, biStream);
		mainArtists = Collections.synchronizedSet(Sets.newLinkedHashSetWithExpectedSize(3));
		setTitle(title);
	}

	@Override
	public TrackTitle getLongFullTitle() {
		return getFullTitle().appendArtists(getMainArtists());
	}

	@Override
	public TrackTitle getFullTitle() {
		return getTitle().appendFeats(getFeatures());
	}

	@Override
	public Collection<Artist> getMainArtists() {
		return Collections.unmodifiableSet(mainArtists);
	}

	@Override
	public Void setMainArtists(final Collection<Artist> mainArtists) {
		VALIDATOR.checkArgument().isNotEmpty(mainArtists, "mainArtists");
		VALIDATOR.checkArgument().isNotIntersecting(mainArtists, getFeatures(), "mainArtists", "features");
		VALIDATOR.checkArgument().isNotIntersecting(mainArtists, getProducers(), "mainArtists", "producers");
		this.mainArtists = Collections.synchronizedSet(Sets.newLinkedHashSet(mainArtists));
		return VoidUtils.returnVoid();
	}

	@Override
	public Void addMainArtist(final Artist artist) {
		VALIDATOR.checkArgument().isNotNull(artist, "artist");
		VALIDATOR.checkArgument().isNotContainedIn(artist, getProducers(), "mainArtist");
		VALIDATOR.checkArgument().isNotContainedIn(artist, getFeatures(), "mainArtist");
		this.mainArtists.add(artist);
		return VoidUtils.returnVoid();
	}

	@Override
	public Optional<VersionTrack> getAsVersionTrack() {
		return Optional.empty();
	}

	@Override
	public boolean isVersionTrack() {
		return false;
	}
	
	@Override
	public TrackTitle getTitle() {
		final TrackTitle titleOnly = new TrackTitle(this.title);
		return getHiddenTrack()
				.map(titleOnly::appendHiddenTrack)
				.orElse(titleOnly);
	}

	@Override
	public Void setTitle(final TrackTitle title) {
		VALIDATOR.checkArgument().isNotNull(title, "title");
		setTitle(title.getTitle());
		if(title.getHiddenTrack() != null){
			setHiddenTrack(title.getHiddenTrack());
		}
		return VoidUtils.returnVoid();
	}
	
	@Override
	public Void setTitle(final String title) {
		VALIDATOR.checkArgument().isNotEmpty(title, "title");
		this.title = title;
		return VoidUtils.returnVoid();
	}

	@Override
	@Generated(value = "GuavaEclipsePlugin")
	public int hashCode() {
		return Objects.hash(super.hashCode(), title, mainArtists);
	}

	@Override
	@Generated(value = "GuavaEclipsePlugin")
	public boolean equals(Object object) {
		if (object instanceof OriginalTrackImpl) {
			if (!super.equals(object))
				return false;
			OriginalTrackImpl that = (OriginalTrackImpl) object;
			return Objects.equals(this.title, that.title) 
					&& Objects.equals(this.mainArtists, that.mainArtists);
		}
		return false;
	}

	@Override
	public String toString() {
		return super.toString();
	}

	@Override
	public Void addMainArtists(final Collection<Artist> artists) {
		VALIDATOR.checkArgument().isNotNull(artists, "artists");
		artists.forEach(this::addMainArtist);
		return VoidUtils.returnVoid();
	}

	@Override
	public Void removeMainArtist(final Artist artist) {
		VALIDATOR.checkArgument().isNotNull(artist, "artist");
		this.mainArtists.remove(artist);
		return VoidUtils.returnVoid();
	}

	@Override
	public Void removeMainArtists(final Collection<Artist> artists) {
		VALIDATOR.checkArgument().isNotNull(artists, "artists");
		this.mainArtists.removeAll(artists);
		return VoidUtils.returnVoid();
	}
	
}
