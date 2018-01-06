package org.rookit.dm.play;

import java.io.IOException;
import java.util.Collection;
import java.util.StringJoiner;

import org.apache.commons.collections4.CollectionUtils;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.rookit.dm.play.able.AbstractPlayable;
import org.rookit.dm.track.Track;
import org.rookit.dm.utils.bistream.BiStream;

@Entity("Playlist")
abstract class AbstractPlaylist extends AbstractPlayable implements Playlist {

	private static final int DEFAULT_FREEZE_LIMIT = 200;

	private final String name;
	
	private final TypePlaylist type;
	
	@Embedded
	private final BiStream image;
	
	protected AbstractPlaylist(TypePlaylist type, String name) {
		this.type = type;
		this.name = name;
		image = PlaylistFactory.getDefault()
				.getBiStreamFactory()
				.createEmpty();
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
	
	private StaticPlaylist freezeIfNecessary(Playlist playlist) {
		if(playlist instanceof StaticPlaylist) {
			return (StaticPlaylist) playlist;
		}
		else if(playlist instanceof DynamicPlaylist) {
			return ((DynamicPlaylist) playlist).freeze(DEFAULT_FREEZE_LIMIT);
		}
		else {
			throw new RuntimeException("Illegal playlist type: " + playlist.getClass());
		}
	}

	@Override
	public Playlist intersectWith(Playlist other) {
		final StaticPlaylist thisAsStatic = freezeIfNecessary(this);
		final StaticPlaylist otherAsStatic = freezeIfNecessary(other);
		final String name = jointName(other);
		final Collection<Track> intersection = CollectionUtils.intersection(
				thisAsStatic.getTracks(), 
				otherAsStatic.getTracks());
		return StaticPlaylist.fromCollection(name, intersection);
	}

	@Override
	public Playlist joinWith(Playlist other) {
		final StaticPlaylist thisAsStatic = freezeIfNecessary(this);
		final StaticPlaylist otherAsStatic = freezeIfNecessary(other);
		final String name = jointName(other);
		final Collection<Track> union = CollectionUtils.union(
				thisAsStatic.getTracks(), 
				otherAsStatic.getTracks());
		return StaticPlaylist.fromCollection(name, union);
	}
	
	private String jointName(Playlist other) {
		return new StringJoiner("_")
				.add(getName())
				.add(other.getName())
				.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		AbstractPlaylist other = (AbstractPlaylist) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (type != other.type) {
			return false;
		}
		return true;
	}
	
	
	
}
