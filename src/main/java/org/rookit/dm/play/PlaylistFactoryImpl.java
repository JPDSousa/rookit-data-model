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

import static org.rookit.api.dm.play.PlaylistFields.*;

import java.util.Map;
import java.util.Optional;

import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.factory.RookitFactory;
import org.rookit.api.dm.play.DynamicPlaylist;
import org.rookit.api.dm.play.Playlist;
import org.rookit.api.dm.play.StaticPlaylist;
import org.rookit.api.dm.play.TypePlaylist;
import org.rookit.api.dm.play.factory.PlaylistFactory;
import org.rookit.dm.factory.AbstractRookitFactory;
import org.rookit.dm.play.factory.PlaylistBiStream;

import com.google.inject.Inject;

@SuppressWarnings("javadoc")
public class PlaylistFactoryImpl extends AbstractRookitFactory<Playlist> implements PlaylistFactory {

	@Inject
	PlaylistFactoryImpl(@PlaylistBiStream final RookitFactory<BiStream> biStreamFactory) {
		super(Optional.of(biStreamFactory));
	}

	@Override
	public Playlist createEmpty() {
		VALIDATOR.invalidOperation("Cannot create an empty playlist");
		return null;
	}

	@Override
	public Playlist create(Map<String, Object> data) {
		final Object name = data.get(NAME);
		final Object type = data.get(TYPE);
		if(name != null && name instanceof String
				&& type != null && type instanceof TypePlaylist) {
			switch((TypePlaylist) type) {
			case DYNAMIC:
				return createDynamicPlaylist((String) name);
			case STATIC:
				return createStaticPlaylist((String) name);
			default:
				break;
			}
		}
		VALIDATOR.runtimeException("Invalid arguments: " + data);
		return null;
	}

	@Override
	public StaticPlaylist createStaticPlaylist(String name) {
		VALIDATOR.checkArgumentStringNotEmpty(name, "A playlist must have a non-null non-empty name");
		final BiStream image = createEmptyBiStream();
		return new StaticPlaylistImpl(name, image);
	}

	@Override
	public DynamicPlaylist createDynamicPlaylist(String name) {
		VALIDATOR.invalidOperation("This factory implementation does not support dynamic playlists");
		return null;
	}

//	@Override
//	public StaticPlaylist intersect(final Playlist one, final Playlist another) {
//		return merge(one, another, CollectionUtils::intersection);
//	}
//
//	@Override
//	public StaticPlaylist union(final Playlist one, final Playlist another) {
//		return merge(one, another, CollectionUtils::union);
//	}
//	
//	private StaticPlaylist merge(final Playlist one, final Playlist another, 
//			final BiFunction<Collection<Track>, Collection<Track>, Collection<Track>> mergeFunction) {
//		final StaticPlaylist thisAsStatic = freezeIfNecessary(one);
//		final StaticPlaylist otherAsStatic = freezeIfNecessary(another);
//		final String name = jointName(one, another);
//		final Collection<Track> intersection = mergeFunction.apply(thisAsStatic.getTracks(), otherAsStatic.getTracks());
//		final StaticPlaylist intersectStaticPlaylist = createStaticPlaylist(name);
//		intersectStaticPlaylist.addAll(intersection);
//		
//		return intersectStaticPlaylist;
//	}
//	
//	private StaticPlaylist freezeIfNecessary(Playlist playlist) {
//		if(playlist instanceof StaticPlaylist) {
//			return (StaticPlaylist) playlist;
//		}
//		else if(playlist instanceof DynamicPlaylist) {
//			return ((DynamicPlaylist) playlist).freeze(DEFAULT_FREEZE_LIMIT);
//		}
//		else {
//			VALIDATOR.runtimeException("Illegal playlist type: " + playlist.getClass());
//			return null;
//		}
//	}
//	
//	private String jointName(final Playlist one, final Playlist other) {
//		return new StringJoiner("_")
//				.add(one.getName())
//				.add(other.getName())
//				.toString();
//	}
}
