package org.rookit.dm.factory;

import java.util.Optional;

import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.factory.RookitFactory;
import org.rookit.dm.utils.DataModelValidator;

@SuppressWarnings("javadoc")
public abstract class AbstractRookitFactory<T> implements RookitFactory<T> {

	protected static final DataModelValidator VALIDATOR = DataModelValidator.getDefault();
	
	private final Optional<RookitFactory<BiStream>> biStreamFactory;
	
	protected AbstractRookitFactory(Optional<RookitFactory<BiStream>> biStreamFactory) {
		super();
		this.biStreamFactory = biStreamFactory;
	}
	
	protected final BiStream createEmptyBiStream() {
		if(biStreamFactory.isPresent()) {
			return biStreamFactory.get().createEmpty();
		}
		return biStreamFactory.orElseGet(() -> {
			VALIDATOR.runtimeException("No BiStream factory provided.");
			return null;
		}).createEmpty();
	}
}
