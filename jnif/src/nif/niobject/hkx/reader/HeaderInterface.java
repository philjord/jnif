package nif.niobject.hkx.reader;

import java.nio.Buffer;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.byteutils.ByteUtils;

/**
 * Connects to a HKX {@link ByteBuffer} and allows direct access to the header
 * contents.
 */
public class HeaderInterface {
	private static final long ONE_LINE_PADDING = 0x10;
	private transient ByteBuffer file;

	/**
	 * Connect to a {@link ByteBuffer}
	 * 
	 * @param file the {@link ByteBuffer} to connect to.
	 */
	public void connect(final ByteBuffer file) {
		this.file = file;
	}

	/**
	 * Create a new header based on the given {@link HeaderData}
	 * 
	 * @param data the {@link HeaderData} to retrieve the data from.
	 * @throws UnsupportedVersionError if the {@link HeaderData} contains a
	 *                                 non-supported version
	 */
	public void compress(final HeaderData data) throws UnsupportedVersionError {
		if (data.version == HeaderDescriptor_v11.VERSION_11) {
			HeaderDescriptor_v11 descriptor = new HeaderDescriptor_v11();
			((Buffer) file).position(0);
			file.put(descriptor.fileID);
			file.put(descriptor.version);
			file.put(descriptor.extras);
			file.put(descriptor.constants);
			file.put(descriptor.verName);
			file.put(descriptor.constants2);
			file.put(descriptor.extras11);
			if (data.paddingAfter == ONE_LINE_PADDING) {
				file.put(descriptor.padding11);
				file.put(descriptor.padding);
			} else {
				file.put(new byte[] { 0, 0 });
			}
		} else {
			throw new UnsupportedVersionError(data.versionName);
		}
	}

	/**
	 * Extract the {@link HeaderData} from the linked {@link ByteBuffer}
	 * 
	 * @return the extracted {@link HeaderData}
	 */
	public HeaderData extract() {
		//https://github.com/blueskythlikesclouds/TagTools
		//https://github.com/Olganix/LibXenoverse2/blob/master/LibXenoverse/LibXenoverse/Havok.h
		//let's check for havok 2015 or later
		/*  f.seek( 4 )
          
          signature = f.read( 4 )
          if ( signature == "TAG0" ):
              return TagFileType.Object
          elif ( signature == "TCM0" ):
              return TagFileType.Compendium
          else:
              return TagFileType.Invalid*/
		byte[] sigb = new byte[4];
		file.position(4);
		file.get(sigb);
		String signature = new String(sigb);
		if (signature.equals("TAG0") ) {
             //TagFileType.Object
			return null; // FIXME: no support for now
		} else if (signature.equals("TCM0") ) {
             //TagFileType.Compendium
			return null; // FIXME: no support for now
		}

		 
		
		
		
		HeaderData data = new HeaderData();
		((Buffer) file).position(0);
		file.get(data.descriptor.fileID);
		file.get(data.descriptor.version);
		file.get(data.descriptor.extras);
		file.get(data.descriptor.constants);
		file.get(data.descriptor.verName);
		file.get(data.descriptor.constants2);
		file.get(data.descriptor.extras11);
		file.get(data.descriptor.padding11);
		data.version = ByteUtils.getUInt(data.descriptor.version);
		data.versionName = new String(data.descriptor.verName);
		if (data.version == HeaderDescriptor_v11.VERSION_11) {
			data.paddingAfter = ByteUtils.getULong(data.descriptor.padding11);
		} else {
			data.paddingAfter = 0;
		}
		
 
		return data;
	}

	/**
	 * @deprecated {@link ByteBuffer} usage no longer allows nor requires this step
	 */
	public void close() {
		// Deprecated
	}
}
