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

import static org.rookit.api.dm.artist.ArtistFields.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.artist.TypeArtist;
import org.rookit.api.dm.artist.factory.ArtistFactory;
import org.rookit.api.dm.factory.RookitFactory;
import org.rookit.dm.artist.factory.ArtistBiStream;
import org.rookit.dm.factory.AbstractRookitFactory;
import org.rookit.utils.print.PrintUtils;
import org.rookit.utils.print.TypeFormat;
import org.rookit.utils.resource.Resources;

import com.google.common.collect.Sets;
import com.google.inject.Inject;

/**
 * Default implementation for {@link ArtistFactory}. This class implements
 * the factory pattern.
 *  
 * @author Joao Sousa (jpd.sousa@campus.fct.unl.pt)
 *
 */
public class ArtistFactoryImpl extends AbstractRookitFactory<Artist> implements ArtistFactory {
	
	private static final String SPLIT_REGEX = "\\s&\\s|,|\\sx\\s|\\sX\\s|\\svs\\s|\\sVs.\\s|\\sVs\\s|\\svs.\\s";

	private static final Path SPECIAL_PATH = Resources.RESOURCES_MAIN
			.resolve("artist")
			.resolve("special.txt");

	private final Set<String> exceptionsSplit;
	
	@Inject
	ArtistFactoryImpl(@ArtistBiStream final RookitFactory<BiStream> biStreamFactory){
		super(Optional.ofNullable(biStreamFactory));
		try {
			// TODO make this customizable
			final Path exceptionsSplitPath = SPECIAL_PATH;
			exceptionsSplit = Files.lines(exceptionsSplitPath)
					.collect(Collectors.toSet());
		} catch (IOException e) {
			VALIDATOR.handleIOException(e);
			throw new RuntimeException("dead code");
		}
	}
	
	@Override
	public Artist createArtist(TypeArtist type, String artistName) {
		VALIDATOR.checkArgument().isNotEmpty(artistName, "artist name");
		VALIDATOR.checkArgument().isNotNull(type, "artist type");
		final BiStream picture = createEmptyBiStream();
		switch(type) {
		case GROUP:
			return new GroupArtistImpl(artistName, picture);
		case MUSICIAN:
			return new MusicianImpl(artistName, picture);
		default:
			return VALIDATOR.checkState().is(false, "%s is not a valid artist type", type);
		}
	}
	
	@Override
	public Set<Artist> createFeatArtists(String artistTag, String albumArtistsTag){
		final Set<Artist> feats = getArtistsFromTag(artistTag);
		feats.removeAll(getArtistsFromTag(albumArtistsTag));

		return feats;
	}

	@Override
	public Set<Artist> createArtists(final String[] artistsName){
		final Set<Artist> artists = Sets.newLinkedHashSetWithExpectedSize(artistsName.length);
	
		for(String name : artistsName){
			artists.add(createArtist(TypeArtist.GROUP, name));
		}
	
		return artists;
	}
	
	@Override
	public String changeFormat(TypeFormat source, TypeFormat target, String value){
		final Set<Artist> artists;
		
		switch(source){
		case TAG:
			artists = getArtistsFromTag(value);
			break;
		case TITLE:
			artists = getArtistsFromFormat(value);
			break;
		default:
			artists = Collections.emptySet();
			break;
		}
		
		return PrintUtils.getIterableAsString(artists, target);
	}

	@Override
	public Set<Artist> getArtistsFromTag(final String artistTag){
		final List<String> artistNames = new ArrayList<>(Arrays.asList(artistTag.split(TypeFormat.TAG.getSeparator() + "|/")));
	
		for(int i = 0; i < artistNames.size(); i++){
			artistNames.set(i, artistNames.get(i).trim());
			if(artistNames.get(i).length()==0){
				artistNames.remove(i);
			}
		}
	
		return createArtists(artistNames.toArray(new String[artistNames.size()]));	
	}
	
	@Override
	public Set<Artist> getArtistsFromFormat(final String rawArtists){
		return createArtists(splitArtists(rawArtists));
	}
	
	private final String[] splitArtists(final String raw){
		final Set<String> artists = new HashSet<>();
		String str = raw;
		int index;
		StringBuilder builder;

		for(String special : exceptionsSplit){
			if(StringUtils.containsIgnoreCase(str, special) || StringUtils.containsIgnoreCase(str, special.replace(" & ", "&"))){
				artists.add(special);
				index = StringUtils.indexOfIgnoreCase(str, special);

				if(index < 0){
					special = special.replace(" & ", "&");
					index = StringUtils.indexOfIgnoreCase(str, special);
				}

				builder = new StringBuilder(32);
				builder.append(str.substring(0, index));
				builder.append(str.substring(index+special.length()));
				str = builder.toString();
			}
		}

		for(String artist : str.split(SPLIT_REGEX)){
			if(!artist.equals("") && !artist.equalsIgnoreCase(Artist.UNKNOWN_ARTISTS)){
				artists.add(artist.trim());
			}
		}

		return artists.toArray(new String[artists.size()]);
	}

	@Override
	public Artist createEmpty() {
		VALIDATOR.invalidOperation("Cannot create an empty artist");
		return null;
	}

	@Override
	public Artist create(Map<String, Object> data) {
		final Object type = data.get(TYPE);
		final Object name = data.get(NAME);
		if(type != null && type instanceof TypeArtist 
				&& name != null && name instanceof String) {
			return createArtist((TypeArtist) type, (String) name);
		}
		VALIDATOR.runtimeException("Invalid arguments: " + data);
		return null;
	}
}
