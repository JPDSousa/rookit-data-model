package org.rookit.dm.play;

import static org.rookit.dm.genre.DatabaseFields.NAME;

import org.rookit.dm.utils.DataModelValidator;
import org.smof.annnotations.SmofBuilder;
import org.smof.annnotations.SmofParam;

@SuppressWarnings("javadoc")
public class PlaylistFactory {

	private static final DataModelValidator VALIDATOR = DataModelValidator.getDefault();
	
	private static PlaylistFactory singleton;
	
	public static PlaylistFactory getDefault() {
		if(singleton == null) {
			singleton = new PlaylistFactory();
		}
		
		return singleton;
	}
		
	private PlaylistFactory() {}
	
	@SmofBuilder
	public Playlist createPlaylist(@SmofParam(name = NAME) String name) {
		VALIDATOR.checkArgumentStringNotEmpty(name, "A playlist must have a non-null non-empty name");
		return new PlaylistImpl(name);
	}
}
