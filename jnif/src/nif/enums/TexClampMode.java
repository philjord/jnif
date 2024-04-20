package nif.enums;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public enum TexClampMode
{
	/**
	 <enum name="TexClampMode" storage="uint">
        Describes the availiable texture clamp modes, i.e. the behavior of UV mapping outside the [0,1] range.
        <option value="0" name="CLAMP_S_CLAMP_T">Clamp in both directions.</option>
        <option value="1" name="CLAMP_S_WRAP_T">Clamp in the S(U) direction but wrap in the T(V) direction.</option>
        <option value="2" name="WRAP_S_CLAMP_T">Wrap in the S(U) direction but clamp in the T(V) direction.</option>
        <option value="3" name="WRAP_S_WRAP_T">Wrap in both directions.</option>
    </enum>
	 */
	CLAMP_S_CLAMP_T (0),// Clamp in both directions.</option>

	CLAMP_S_WRAP_T (1),// Clamp in the S(U) direction but wrap in the T(V) direction.

	WRAP_S_CLAMP_T (2),// Wrap in the S(U) direction but clamp in the T(V) direction.

	WRAP_S_WRAP_T (3),// Wrap in both directions.</option> 

	MIRRORED_S_MIRRORED_T (0xFF00);

	public int mode;

	private TexClampMode(int mode) {
		this.mode = mode;
	}
	public static TexClampMode load(ByteBuffer stream) throws IOException
	{
		int m = ByteConvert.readInt(stream);
		if(m < 4)
			return TexClampMode.values()[m];
		
		// fallback for 0xFF0
		for (TexClampMode tcm : TexClampMode.values())
			if (tcm.mode == m)
				return tcm;
		
		System.out.println("Bad TexClampMode " + m);
		return WRAP_S_WRAP_T;
	}

}
