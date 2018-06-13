package org.rookit.dm.album.disc;

import org.rookit.api.dm.factory.RookitFactory;
import org.rookit.api.dm.key.Key;

public interface MutableDiscFactory extends RookitFactory<MutableDisc, Key> {

    MutableDisc create(String discName);

}
