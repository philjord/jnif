package nif.niobject.particle;

import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.compound.NifVector3;
import nif.niobject.NiTriBasedGeom;

public class NiPSysMeshEmitter extends NiPSysEmitter
{
	/**
	 <niobject name="NiPSysMeshEmitter" abstract="0" inherit="NiPSysEmitter" ver1="20.0.0.5">

	 Particle emitter that uses points on a specified mesh to emit from.
	 
	 <add name="Num Emitter Meshes" type="uint">
	 The number of references to emitter meshes that follow.
	 </add>
	 <add name="Emitter Meshes" type="Ref" template="NiTriBasedGeom" arr1="Num Emitter Meshes">Links to meshes used for emitting.</add>
	 <add name="Initial Velocity Type" type="VelocityType">
	 The way the particles get their initial direction and speed.
	 </add>
	 <add name="Emission Type" type="EmitFrom">
	 The parts of the mesh that the particles emit from.
	 </add>
	 <add name="Emission Axis" type="Vector3">The emission axis.</add>
	 </niobject>
	 
	EMIT_FROM_VERTICES  	Emit from a random vertex in the mesh.
	EMIT_FROM_FACE_CENTER 	Emit from the center of a random triangle in the mesh.
	EMIT_FROM_EDGE_CENTER 	Emit from the center of a random edge of a random triangle in the mesh.
	EMIT_FROM_FACE_SURFACE 	Emit from a random position inside a random triangle in the mesh.
	EMIT_FROM_EDGE_SURFACE 	Emit from a random position along a random edge of a random triangle in the mesh.
	EMIT_MAX 				An invalid value indicating the maximum number of defined MeshEmissionType enumerations. 

	VELOCITY_USE_NORMALS  	Use the vertex/triangle normals to determine the velocity direction.
	VELOCITY_USE_RANDOM 	Use a random velocity direction.
	VELOCITY_USE_DIRECTION 	Use the direction specified in the base emitter as the velocity direction.
	VELOCITY_MAX 			An invalid value indicating the maximum number of defined InitialVelocityType enumerations. 

	 */

	public int EMIT_FROM_VERTICES = 0;

	public int EMIT_FROM_FACE_CENTER = 1;

	public int EMIT_FROM_EDGE_CENTER = 2;

	public int EMIT_FROM_FACE_SURFACE = 3;

	public int EMIT_FROM_EDGE_SURFACE = 4;

	public int VELOCITY_USE_NORMALS = 0;

	public int VELOCITY_USE_RANDOM = 1;

	public int VELOCITY_USE_DIRECTION = 2;

	public int numEmitterMeshes;

	public NifRef[] emitterMeshes;

	public int initialVelocityType;

	public int emissionType;

	public NifVector3 emissionAxis;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		numEmitterMeshes = ByteConvert.readInt(stream);
		emitterMeshes = new NifRef[numEmitterMeshes];
		for (int i = 0; i < numEmitterMeshes; i++)
		{
			emitterMeshes[i] = new NifRef(NiTriBasedGeom.class, stream);
		}
		initialVelocityType = ByteConvert.readInt(stream);
		emissionType = ByteConvert.readInt(stream);
		emissionAxis = new NifVector3(stream);

		return success;
	}
}
