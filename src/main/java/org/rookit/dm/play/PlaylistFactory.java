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

import static org.rookit.dm.play.DatabaseFields.*;

import java.io.Serializable;
import java.util.Map;

import org.rookit.dm.utils.DataModelValidator;
import org.rookit.dm.utils.bistream.BiStream;
import org.rookit.dm.utils.bistream.BiStreamFoF;
import org.rookit.dm.utils.bistream.BufferBiStreamFactory;
import org.rookit.dm.utils.factory.RookitFactory;

@SuppressWarnings("javadoc")
public class PlaylistFactory implements Serializable, BiStreamFoF, RookitFactory<Playlist> {

	private static final long serialVersionUID = 1L;

	private static final DataModelValidator VALIDATOR = DataModelValidator.getDefault();

	private static PlaylistFactory singleton;

	public static PlaylistFactory getDefault() {
		if(singleton == null) {
			singleton = new PlaylistFactory();
		}

		return singleton;
	}

	private RookitFactory<BiStream> biStreamFactory;
	
	private PlaylistFactory() {
		biStreamFactory = new BufferBiStreamFactory();
	}

	public StaticPlaylist createPlaylist(TypePlaylist type, String name) {
		VALIDATOR.checkArgumentNotNull(type, "Playlist type cannot be null");
		VALIDATOR.checkArgumentStringNotEmpty(name, "A playlist must have a non-null non-empty name");
		return new StaticPlaylistImpl(name);
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
	public Playlist createEmpty() {
		throw new UnsupportedOperationException("Cannot create an empty playlist");
	}

	@Override
	public Playlist create(Map<String, Object> data) {
		final Object name = data.get(NAME);
		final Object type = data.get(TYPE);
		if(name != null && name instanceof String
				&& type != null && type instanceof TypePlaylist) {
			return createPlaylist((TypePlaylist) type, (String) name);
		}
		throw new RuntimeException("Invalid arguments: " + data);
	}
}
