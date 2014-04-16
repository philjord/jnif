package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.niobject.controller.NiTimeController;

public class BSLagBoneController extends NiTimeController
{
	/**
	 	<niobject name="BSLagBoneController" inherit="NiTimeController">
	    A controller that trails a bone behind an actor.
			<add name="Linear Velocity" type="float">How long it takes to rotate about an actor back to rest position.</add>
			<add name="Linear Rotation" type="float">How the bone lags rotation</add>
			<add name="Maximum Distance" type="float">How far bone will tail an actor.</add>
		</niobject>
	 */
	public float LinearVelocity;

	public float LinearRotation;

	public float MaximumDistance;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		LinearVelocity = ByteConvert.readFloat(stream);
		LinearRotation = ByteConvert.readFloat(stream);
		MaximumDistance = ByteConvert.readFloat(stream);

		return success;
	}
}
