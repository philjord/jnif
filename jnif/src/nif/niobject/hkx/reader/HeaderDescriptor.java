package nif.niobject.hkx.reader;

/**
 * A general purpose Header descriptor, designed based on the v8 and v11
 * headers.
 * 
 * More detail can be found here
 *  https://github.com/SARDONYX-sard/serde-hkx/tree/0.1.0/docs/handson_hex_dump/defaultmale
 */
public class HeaderDescriptor {
	/**
	 * The file id, same for all hkx files (I assume says that it's a hkx file)
	 */
	public byte[] fileID = new byte[] { 87, -32, -32, 87, 16, -64, -64, 16, 0, 0, 0, 0 };
	/**
	 * The file version, over 4 bytes. See docs for what versions are what.
	 */
	public byte[] version = new byte[4];
	/**
	 * Extra data. 
	 * ptr_size: 8 -> 8bytes -> This hkx file is 64bit.
     * endian: 1 -> little endian
     * padding_option: 0
     * base_class: 1
	 */
	public byte[] extras = new byte[4];
	/**
	 * Some constant data. No idea what it is for.
	 *  section_count: 3
     *  contents_section_index: 2
     *  contents_contents_section_offset: 0
     *  contents_class_name_section_index: 0
     *  contents_class_name_section_offset: 0x4b(75)
	 */
	public byte[] constants = new byte[] { 3, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 75, 0, 0, 0 };
	/**
	 * A string containing the version name.
	 */
	public byte[] verName = new byte[14];
	/**
	 * Some more constants. Firt 2 are version string null and then speerator of 0xff
	 */
	public byte[] constants2 = new byte[] { 0, -1, 0, 0, 0, 0 };
	/**
	 * This is either FF on version 8, or some data on version 11.
	 * max_predicate: 0xffff -> -1
	 */
	public byte[] extras11 = new byte[2];
	/**
	 * This appears to be a padding before the next data chunk for version 11.
	 * section_offset: 0xffff -> -1
	 */
	public byte[] padding11 = new byte[2];
}
