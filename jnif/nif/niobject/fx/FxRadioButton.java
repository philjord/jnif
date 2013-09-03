package nif.niobject.fx;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifPtr;

public class FxRadioButton extends FxWidget
{
	/**
	 
	 <niobject name="FxRadioButton" abstract="0" inherit="FxWidget" ver1="10.0.1.0">

	 Unknown.
	 
	 <add name="Unknown Int 1" type="uint">Unknown.</add>
	 <add name="Unknown Int  2" type="uint">Unknown.</add>
	 <add name="Unknown Int 3" type="uint">Unknown.</add>
	 <add name="Num Buttons" type="uint">Number of unknown links.</add>
	 <add name="Buttons" type="Ptr" template="FxRadioButton" arr1="Num Buttons">
	 Unknown pointers to other buttons.  Maybe other buttons in a group so they can be switch off if this one is switched on?
	 </add>
	 </niobject>
	 */

	public int unknown2;

	public int unknown3;

	public int unknown4;

	public int numButtons;

	public NifPtr[] buttons;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		unknown2 = ByteConvert.readInt(stream);
		unknown3 = ByteConvert.readInt(stream);
		unknown4 = ByteConvert.readInt(stream);

		numButtons = ByteConvert.readInt(stream);
		buttons = new NifPtr[numButtons];
		for (int i = 0; i < numButtons; i++)
		{
			buttons[i] = new NifPtr(FxRadioButton.class, stream);
		}

		return success;
	}
}