package nif.niobject.hkx.reader;

/**
 * A data position descriptor aimed at the current section.
 */
public class DataInternal {
	/**
	 * The data parent, in the current section.
	 */
	public long from;

	/**
	 * The data position, in the current section.
	 */
	public long to;
}
