package nif.niobject.hkx.reader;

import java.util.Collection;
import java.util.LinkedHashMap;

import nif.niobject.hkx.hkBaseObject;

/**
 * A HKXFile contains all data carried by a HKX File in the form of a list of
 * {@link HKXObject}, in a DOM format.
 * <p>
 * Main methods : {@link #getClassVersion()} / {@link #getContentsVersion()}
 * retrieves the version of the file, used as a description parameter for the
 * contents. {@link #add(HKXObject)} / {@link #addAll(HKXObject...)} adds a
 * single or a collection of {@link HKXObject} to the file.
 * {@link #getContentCollection()} retrieves all linked {@link HKXObject}.
 */
public class HKXContents {
	private final transient String contentsversion;
	private final transient int classversion;
	private final transient LinkedHashMap<Long, hkBaseObject> content;

	/**
	 * Creates a new {@link HKXContents}.
	 * 
	 * @param contentsversion the contents version of this {@link HKXContents}.
	 * @param classversion    the class version of this {@link HKXContents}.
	 */
	// TODO add ways to select between content/class version with a specific class.
	public HKXContents(final String contentsversion, final int classversion) {
		content = new LinkedHashMap<Long, hkBaseObject>();
		this.contentsversion = contentsversion;
		this.classversion = classversion;
	}

	/**
	 * Get this {@link HKXContents}'s contents version.
	 * 
	 * @return the contents version, as a {@link String}.
	 */
	public String getContentsVersion() {
		return contentsversion;
	}

	/**
	 * Get this {@link HKXContents}'s class version.
	 * 
	 * @return the class version, as {@link int}.
	 */
	public int getClassVersion() {
		return classversion;
	}

	/**
	 * Retrieves all base {@link HKXObject}
	 * 
	 * @return
	 */
	public Collection<hkBaseObject> getContentCollection() {
		return content.values();
	}

	/**
	 * Add a {@link HKXObject} as a base element of the file.
	 * 
	 * @param object the {@link HKXObject} to add to the {@link HKXContents}.
	 */
	public void add(long id, final hkBaseObject object) {
		content.put(id, object);
	}
	
	public hkBaseObject get(long id) {
		return content.get(id);
	}
	 
}
