package org.rookit.dm.artist.external;

import org.rookit.api.dm.factory.RookitFactory;
import org.rookit.api.dm.key.Key;

public interface MutableExternalIdentifiersFactory extends RookitFactory<MutableExternalIdentifiers, Key> {

    MutableExternalIdentifiers create(String isni);

    MutableExternalIdentifiers create(String isni, String ipi);
}
