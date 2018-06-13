
package org.rookit.dm.play;

import com.google.common.base.MoreObjects;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.play.Playlist;
import org.rookit.dm.genre.AbstractGenreable;
import org.rookit.dm.play.able.event.MutableEventStatsFactory;
import org.rookit.utils.VoidUtils;

import java.io.IOException;
import java.io.OutputStream;

abstract class AbstractPlaylist extends AbstractGenreable implements Playlist {

    private final String name;

    private final BiStream image;

    AbstractPlaylist(final String name,
                     final BiStream image,
                     final MutableEventStatsFactory eventStatsFactory) {
        super(eventStatsFactory);
        this.name = name;
        this.image = image;
    }

    @Override
    public BiStream image() {
        return this.image;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public Void setImage(final byte[] image) {
        VALIDATOR.checkArgument().isNotNull(image, "image");
        try (final OutputStream output = this.image.writeTo()) {
            output.write(image);
            return VoidUtils.returnVoid();
        } catch (final IOException e) {
            return VALIDATOR.handleException().inputOutputException(e);
        }
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("official", this.name)
                .add("image", this.image)
                .toString();
    }
}
