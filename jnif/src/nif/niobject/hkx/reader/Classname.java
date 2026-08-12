package nif.niobject.hkx.reader;

/**
 * Represents a ClassName object in the {@literal __classnames__} section of a
 * HKX File.
 */
public class Classname {
	/**
	 * the name of the class
	 */
	public transient String name;
	/**
	 * the UUID of the class.
	 */
	public transient int uuid;
	
	/**
	 * the signature of the class
	 */
	public transient String sig;

	/**
	 * Create a ClassName either to write it to the file or when it was read from a
	 * file.
	 * 
	 * @param classname the class name.
	 * @param i      the class UUID, as defined in the relevant classXML.
	 */
	public Classname(final String classname, final int i) {
		this.name = classname;
		this.uuid = i;
		this.sig = Integer.toHexString(i);
	}
}
