package org.rookit.dm.play;

import static org.rookit.dm.play.DatabaseFields.IMAGE;
import static org.rookit.dm.play.DatabaseFields.NAME;

import java.io.ByteArrayInputStream;
import java.util.StringJoiner;

import org.apache.commons.collections4.CollectionUtils;
import org.smof.annnotations.SmofObject;
import org.smof.annnotations.SmofString;
import org.smof.gridfs.SmofGridRef;
import org.smof.gridfs.SmofGridRefFactory;

abstract class AbstractPlaylist extends AbstractPlayable implements Playlist {

	private static final int DEFAULT_FREEZE_LIMIT = 200;

	@SmofString(name = NAME)
	private final String name;
	
	@SmofObject(name = IMAGE, bucketName = IMAGE_BUCKET, preInsert = false)
	private final SmofGridRef image;
	
	protected AbstractPlaylist(String name) {
		this.name = name;
		image = SmofGridRefFactory.newEmptyRef();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public SmofGridRef getImage() {
		return image;
	}

	@Override
	public Void setImage(byte[] image) {
		this.image.attachByteArray(new ByteArrayInputStream(image));
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
		return StaticPlaylist.fromCollection(name, CollectionUtils.intersection(thisAsStatic, otherAsStatic));
	}

	@Override
	public Playlist joinWith(Playlist other) {
		final StaticPlaylist thisAsStatic = freezeIfNecessary(this);
		final StaticPlaylist otherAsStatic = freezeIfNecessary(other);
		final String name = jointName(other);
		return StaticPlaylist.fromCollection(name, CollectionUtils.union(thisAsStatic, otherAsStatic));
	}
	
	private String jointName(Playlist other) {
		return new StringJoiner("_")
				.add(getName())
				.add(other.getName())
				.toString();
	}
	
}
