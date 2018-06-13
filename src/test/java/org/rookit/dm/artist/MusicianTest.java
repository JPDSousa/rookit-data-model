
package org.rookit.dm.artist;

import org.rookit.api.dm.artist.Musician;

@SuppressWarnings("javadoc")
public class MusicianTest extends AbstractArtistTest<Musician> {

    @Override
    public Musician createTestResource() {
        return FACTORY.musicians().createRandom();
    }

    @Override
    public Class<Musician> getTestResourceType() {
        return Musician.class;
    }

}
