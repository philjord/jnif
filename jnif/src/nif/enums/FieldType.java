package nif.enums;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class FieldType
{
	/**
	 * The force field's type.
	 */
	public static final int FIELD_WIND = 0;// Wind (fixed direction)</option>

	public static final int FIELD_POINT = 1;// Point (fixed origin)

	public int type;

	public FieldType(InputStream stream) throws IOException
	{
		type = ByteConvert.readInt(stream);
	}
}
