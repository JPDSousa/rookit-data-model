package org.rookit.dm.track.audio;

import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;
import org.rookit.api.dm.key.Key;
import org.rookit.api.dm.track.audio.AudioFeature;
import org.rookit.api.dm.track.audio.AudioFeatureFactory;
import org.rookit.dm.utils.DataModelValidator;
import org.rookit.utils.log.validator.Validator;

@SuppressWarnings("javadoc")
final class AudioFeatureFactoryImpl implements AudioFeatureFactory {

    private static final Validator VALIDATOR = DataModelValidator.getDefault();
    
    /**
     * Logger for AudioFeatureFactoryImpl.
     */
    private static final Logger logger = VALIDATOR.getLogger(AudioFeatureFactoryImpl.class);
    
    @Inject
    private AudioFeatureFactoryImpl() {}

    @Override
    public AudioFeature create(final Key key) {
        logger.warn("{} creation through {}#create(Key) is ignored for key {}. Creating empty instead.",
                AudioFeature.class, getClass(), key);
        return createEmpty();
    }

    @Override
    public AudioFeature createEmpty() {
        return new AudioFeatureImpl();
    }

}
