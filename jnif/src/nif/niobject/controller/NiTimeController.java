package nif.niobject.controller;

import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifFlags;
import nif.basic.NifPtr;
import nif.basic.NifRef;
import nif.niobject.NiObject;

public abstract class NiTimeController extends NiObject
{
	/**
	    <niobject name="NiTimeController" abstract="1" inherit="NiObject">
	    A generic time controller object.
	    <add name="Next Controller" type="Ref" template="NiTimeController">Index of the next controller.</add>
	    <add name="Flags" type="Flags">
	        Controller flags (usually 0x000C). Probably controls loops.
	        Bit 0 : Anim type, 0=APP_TIME 1=APP_INIT
	        Bit 1-2 : Cycle type  00=Loop 01=Reverse 10=Loop
	        Bit 3 : Active
	        Bit 4 : Play backwards
	    </add>
	    <add name="Frequency" type="float">Frequency (is usually 1.0).</add>
	    <add name="Phase" type="float">Phase (usually 0.0).</add>
	    <add name="Start Time" type="float">Controller start time.</add>
	    <add name="Stop Time" type="float">Controller stop time.</add>
	    <add name="Target" type="Ptr" template="NiObjectNET" ver1="3.3.0.13" vercond="(Version &lt; 20.2.0.7)">Controller target (object index of the first controllable ancestor of this object).</add>
	    <add name="Target" type="Ptr" template="NiObject" ver1="3.3.0.13" vercond="((Version >= 20.2.0.7) &amp;&amp; (User Version &lt;= 12))">Controller target (object index of the first controllable ancestor of this object).</add>
	    <add name="Unknown Integer" type="uint" ver2="3.1">Unknown integer.</add>
	</niobject>
	 */

	public NifRef nextController;

	public NifFlags flags;

	public float frequency;

	public float phase;

	public float startTime;

	public float stopTime;

	public NifPtr target;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		nextController = new NifRef(NiTimeController.class, stream);
		flags = new NifFlags(stream);
		frequency = ByteConvert.readFloat(stream);
		phase = ByteConvert.readFloat(stream);
		startTime = ByteConvert.readFloat(stream);
		stopTime = ByteConvert.readFloat(stream);
		if (nifVer.LOAD_VER < NifVer.VER_20_2_0_7)
		{
			target = new NifPtr(NiObjectNET.class, stream);
		}
		else
		{
			target = new NifPtr(NiObject.class, stream);
		}

		return success;
	}
}