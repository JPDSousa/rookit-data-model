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

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.rookit.dm.utils.DataModelValidator;
import org.rookit.dm.utils.PrintUtils;
import org.rookit.dm.utils.bistream.BiStream;
import org.rookit.dm.utils.bistream.BiStreamFoF;
import org.rookit.dm.utils.bistream.BufferBiStreamFactory;
import org.rookit.dm.utils.factory.RookitFactory;
import org.rookit.utils.print.TypeFormat;
import org.rookit.utils.resource.Resources;

import com.google.common.collect.Sets;

/**
 * Class that provides methods for creating {@link Artist} objects. This class implements
 * the factory pattern.
 *  
 * @author Joao
 *
 */
public class ArtistFactory implements RookitFactory<Artist>, Serializable, BiStreamFoF {

	private static final long serialVersionUID = 1L;
	
	private static final String SPLIT_REGEX = "\\s&\\s|,|\\sx\\s|\\sX\\s|\\svs\\s|\\sVs.\\s|\\sVs\\s|\\svs.\\s";

	private static final DataModelValidator VALIDATOR = DataModelValidator.getDefault();
	
	private static final Path SPECIAL_PATH = Resources.RESOURCES_MAIN
			.resolve("artist")
			.resolve("special.txt");

	private static ArtistFactory factory;
	
	/**
	 * Returns the default album factory.
	 * 
	 * @return default album factory
	 */
	public static ArtistFactory getDefault(){
		if(factory == null){
			factory = new ArtistFactory(SPECIAL_PATH);
		}
		return factory;
	}
	
	private final Set<String> exceptionsSplit;
	private RookitFactory<BiStream> biStreamFactory;
	
	private ArtistFactory(Path exceptionsSplitPath){
		try {
			exceptionsSplit = Files.lines(exceptionsSplitPath)
					.collect(Collectors.toSet());
			biStreamFactory = new BufferBiStreamFactory();
		} catch (IOException e) {
			VALIDATOR.handleIOException(e);
			throw new RuntimeException("dead code");
		}
	}
	
	/**
	 * Creates a new artist with the specified name.
	 * 
	 * @param type artist type
	 * @param artistName artist name
	 * @return a new artist with the name passed as parameter
	 */
	public Artist createArtist(TypeArtist type, String artistName) {
		final String errMsg = String.valueOf(type) + " is not a valid artist type";
		VALIDATOR.checkArgumentStringNotEmpty(artistName, "Must specify an artist name");
		VALIDATOR.checkArgumentNotNull(type, errMsg);
		switch(type) {
		case GROUP:
			return new GroupArtistImpl(artistName);
		case MUSICIAN:
			return new MusicianImpl(artistName);
		default:
			throw new IllegalArgumentException(errMsg);		
		}
	}
	
	/**
	 * Creates a set of feature artists based on the artist tag (ID3v1-2) and album artists tag (ID3v2)
	 * This method assumes that the artists are separated by a semi-colon
	 * 
	 * The method returns the all the artists from the artists' tag that are not in the albumArtists' tag.
	 * 
	 * @param artistTag artist tag (ID3v1) (ex. art1;art2)
	 * @param albumArtistsTag album artists tag (ID3v2) (ex. art1;art2)
	 * @return the set of feature artists
	 */
	public Set<Artist> createFeatArtists(String artistTag, String albumArtistsTag){
		final Set<Artist> feats = getArtistsFromTag(artistTag);
		feats.removeAll(getArtistsFromTag(albumArtistsTag));

		return feats;
	}

	public Set<Artist> createArtists(final String[] artistsName){
		final Set<Artist> artists = Sets.newLinkedHashSetWithExpectedSize(artistsName.length);
	
		for(String name : artistsName){
			artists.add(createArtist(TypeArtist.GROUP, name));
		}
	
		return artists;
	}
	
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
			artists = new LinkedHashSet<>();
			break;
		}
		
		return PrintUtils.getIterableAsString(artists, target);
	}

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
	public RookitFactory<BiStream> getBiStreamFactory() {
		return biStreamFactory;
	}

	@Override
	public void setBiStreamFactory(RookitFactory<BiStream> factory) {
		this.biStreamFactory = factory;
	}

	@Override
	public Artist createEmpty() {
		throw new UnsupportedOperationException("Cannot create an empty artist");
	}

	@Override
	public Artist create(Map<String, Object> data) {
		final Object type = data.get(TYPE);
		final Object name = data.get(NAME);
		if(type != null && type instanceof TypeArtist 
				&& name != null && name instanceof String) {
			return createArtist((TypeArtist) type, (String) name);
		}
		throw new RuntimeException("Invalid arguments: " + data);
	}
}
