package org.rookit.dm.play;

import static org.rookit.api.dm.play.PlaylistFields.*;

import java.io.IOException;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.play.Playlist;
import org.rookit.api.dm.play.TypePlaylist;
import org.rookit.dm.play.able.AbstractPlayable;
import java.util.Objects;
import javax.annotation.Generated;

@Entity("Playlist")
abstract class AbstractPlaylist extends AbstractPlayable implements Playlist {

	@Property(NAME)
	private final String name;
	
	@Property(TYPE)
	private final TypePlaylist type;
	
	@Embedded(IMAGE)
	private final BiStream image;
	
	protected AbstractPlaylist(TypePlaylist type, String name, BiStream image) {
		this.type = type;
		this.name = name;
		this.image = image;
	}
	
	@Override
	public TypePlaylist getType() {
		return type;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public BiStream getImage() {
		return image;
	}

	@Override
	public Void setImage(byte[] image) {
		try {
			this.image.toOutput().write(image);
		} catch (IOException e) {
			VALIDATOR.handleIOException(e);
		}
		return null;
	}

	@Override
	@Generated(value = "GuavaEclipsePlugin")
	public int hashCode() {
		return Objects.hash(super.hashCode(), name, type);
	}

	@Override
	@Generated(value = "GuavaEclipsePlugin")
	public boolean equals(Object object) {
		if (object instanceof AbstractPlaylist) {
			if (!super.equals(object))
				return false;
			AbstractPlaylist that = (AbstractPlaylist) object;
			return Objects.equals(this.name, that.name) && Objects.equals(this.type, that.type);
		}
		return false;
	}
	
	
	
}
