package nif.niobject.controller;

import java.nio.ByteBuffer;

import nif.NifVer;

public class NiBSBoneLODController extends NiBoneLODController
{
	/**
	 <niobject name="NiBSBoneLODController" abstract="0" inherit="NiBoneLODController">
	 A simple LOD controller for bones.
	 </niobject>
	 */

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		return success;
	}
}