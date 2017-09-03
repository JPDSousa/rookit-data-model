package utils;

import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.Logger;
import org.rookit.utils.log.Errors;
import org.rookit.utils.log.Logs;
import org.rookit.utils.log.Validator;

import album.AbstractAlbum.Disc;
import artist.Artist;

@SuppressWarnings("javadoc")
public class CoreValidator extends Validator {
	
	private static final CoreValidator SINGLETON = new CoreValidator(Logs.CORE.getLogger());
	
	public static CoreValidator getDefault() {
		return SINGLETON;
	}

	private CoreValidator(Logger logger) {
		super(logger);
	}
	
	/**
	 * Validates a disc. This method must be used before accessing a disc.
	 * 
	 * This method will throw an exception ({@link NoSuchDiscException} wrapped in a {@link MlmException}) if:
	 * <ul>
	 * <li>The disc is null
	 * </ul>
	 * 
	 * @param disc disc instance
	 * @param discName disc name that is being accessed
	 * @param albumTitle album title
	 */
	public void checkDiscNotNull(Disc disc, String discName, String albumTitle) {
		checkArgumentNotNull(disc, "The disc " + discName + " was not found in album " + albumTitle);
	}
	
	public void checkValidFeatures(Iterable<Artist> features, Iterable<Artist> mainArtists) {
		checkArgumentNotNull(features, "The feature artists set cannot be null");
		final Collection<Artist> intersection = CollectionUtils.intersection(features, mainArtists);
		checkArgumentEmptyCollection(intersection, "Artists " + intersection.toString() + " are already defined as main artist");
	}
	
	public void handleException(RuntimeException e) {
		Errors.handleException(e, Logs.CORE);
	}

}
