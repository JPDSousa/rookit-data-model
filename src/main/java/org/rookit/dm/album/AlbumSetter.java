package org.rookit.dm.album;

import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Set;

import org.rookit.dm.artist.Artist;
import org.rookit.dm.track.Track;

/**
 * An interface with the album setters.
 * 
 * @author Joao
 * @param <T> return type
 *
 */
public interface AlbumSetter<T> {

	/**
	 * Sets a new title for the album.
	 * 
	 * @param title new title for the album.
	 * @return object to return
	 */
	T setTitle(String title);

	/**
	 * Adds the artist passed by parameter to the set of album authors.
	 * 
	 * @param artist artists to be added to the set of album authors
	 * @return object to return
	 */
	T addArtist(Artist artist);
	
	/**
	 * Sets the set of album author's, overwriting any previous data.
	 * 
	 * @param artists album author's
	 * @return object to return
	 */
	T setArtists(Set<Artist> artists);

	/**
	 * Adds a track to the list of tracks released through this album.
	 * <p> Tracks added through this method can be accessed through {@link Album#getTracks()}.
	 *
	 * Throws {@link IllegalArgumentException} if the album contains more that one disc.
	 * 
	 * @param track track object to be added to the album track list.
	 * @param number the index of the track
	 * @return object to return
	 */
	T addTrack(Track track, Integer number);

	T addTrack(TrackSlot slot);

	T addTrack(Track track, Integer i, String discName);
	
	/**
	 * Adds a track to the last position in the track list.
	 * 
	 * @param track track number in the disc
	 * @see Album#addTrack(Track, Integer)
	 * @return object to return
	 */
	T addTrackLast(Track track);
	
	T addTrackLast(Track track, String discName);
	
	/**
	 * Sets a new release date for the album. The old
	 * date assigned will be overwritten.
	 * @param date new date to be assigned
	 * @return object to return
	 */
	T setReleaseDate(LocalDate date);
	
	/**
	 * Sets a new cover art for the album. The cover art is represented
	 * as a byte array so, in order to read from a JPEG file one should
	 * use {@link Files#readAllBytes(java.nio.file.Path)} as so:
	 * <pre>Files.readAllBytes(Paths.get("jpeg-path-as-string");</pre>
	 * <p>In order to download an image into a byte array:</p>
	 * <pre> 
	 * 	URL u = new URL("http://localhost:8080/images/anImage.jpg");
	 * 	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	 * 	try {
	 * 		byte[] chunk = new byte[4096];
	 * 		int bytesRead;
	 * 		InputStream stream = toDownload.openStream();
	 * 
	 * 		while ((bytesRead = stream.read(chunk)) > 0) {
	 * 			outputStream.write(chunk, 0, bytesRead);
	 * 		}
	 * 
	 * 	} catch (IOException e) {
	 * 		e.printStackTrace();
	 * 	}
	 * 	outputStream.toByteArray();//image bytes
	 * </pre>
	 * @param image byte array that represents the image to be set as cover
	 * @return object to return
	 */
	T setCover(byte[] image);

	/**
	 * <b>This method is only used by {@link Track} to relocate tracks when its number and/or disc
	 * are changed. Do not use this method otherwise.</b>
	 * 
	 * This method relocates the track in the album's track list to the disc and number passed as parameter.
	 * 
	 * @param discName source disc name 
	 * @param number source number
	 * @param newDiscName target disc name
	 * @param newNumber target track number
	 * 
	 */
	void relocate(String discName, Integer number, String newDiscName, Integer newNumber);

}
