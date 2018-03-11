package org.rookit.dm.inject;

import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.album.factory.AlbumFactory;
import org.rookit.api.dm.artist.factory.ArtistFactory;
import org.rookit.api.dm.factory.RookitFactories;
import org.rookit.api.dm.factory.RookitFactory;
import org.rookit.api.dm.genre.factory.GenreFactory;
import org.rookit.api.dm.play.factory.PlaylistFactory;
import org.rookit.api.dm.track.factory.TrackFactory;
import org.rookit.dm.album.AlbumFactoryImpl;
import org.rookit.dm.album.factory.AlbumBiStream;
import org.rookit.dm.artist.ArtistFactoryImpl;
import org.rookit.dm.artist.factory.ArtistBiStream;
import org.rookit.dm.factory.RookitFactoriesImpl;
import org.rookit.dm.genre.GenreFactoryImpl;
import org.rookit.dm.play.PlaylistFactoryImpl;
import org.rookit.dm.play.factory.PlaylistBiStream;
import org.rookit.dm.track.TrackFactoryImpl;
import org.rookit.dm.track.factory.TrackBiStream;
import org.rookit.dm.utils.bistream.BufferBiStreamFactory;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;

@SuppressWarnings("javadoc")
public class DMFactoriesModule extends AbstractModule {

	@Override
	protected void configure() {
		// artist
		bind(new TypeLiteral<RookitFactory<BiStream>>() {})
		.annotatedWith(ArtistBiStream.class)
		.to(BufferBiStreamFactory.class);
		bind(ArtistFactory.class).to(ArtistFactoryImpl.class).asEagerSingleton();

		// album
		bind(new TypeLiteral<RookitFactory<BiStream>>() {})
		.annotatedWith(AlbumBiStream.class)
		.to(BufferBiStreamFactory.class);
		bind(AlbumFactory.class).to(AlbumFactoryImpl.class).asEagerSingleton();

		// track
		bind(new TypeLiteral<RookitFactory<BiStream>>() {})
		.annotatedWith(TrackBiStream.class)
		.to(BufferBiStreamFactory.class);
		bind(TrackFactory.class).to(TrackFactoryImpl.class).asEagerSingleton();

		// genre
		bind(GenreFactory.class).to(GenreFactoryImpl.class).asEagerSingleton();

		// playlist
		bind(new TypeLiteral<RookitFactory<BiStream>>() {})
		.annotatedWith(PlaylistBiStream.class)
		.to(BufferBiStreamFactory.class);
		bind(PlaylistFactory.class).to(PlaylistFactoryImpl.class).asEagerSingleton();

		bind(RookitFactories.class).to(RookitFactoriesImpl.class).asEagerSingleton();
	}

}