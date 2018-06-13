package org.rookit.dm.artist.name;

import org.rookit.api.dm.artist.name.ArtistName;

public interface MutableArtistName extends ArtistName {

    void addAlias(String alias);

    void clearAliases();

    void removeAlias(String alias);

}
