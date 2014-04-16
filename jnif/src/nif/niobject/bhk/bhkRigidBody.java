package nif.niobject.bhk;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.compound.NifInertialMatrix;
import nif.compound.NifQuaternionXYZW;
import nif.compound.NifVector4;
import nif.enums.DeactivatorType;
import nif.enums.MotionQuality;
import nif.enums.MotionSystem;
import nif.enums.OblivionLayer;
import nif.enums.SolverDeactivation;
import nif.enums.hkResponseType;

public class bhkRigidBody extends bhkEntity
{
	/**
	 
	 

	 <niobject name="bhkRigidBody" abstract="0" inherit="bhkEntity">
	    This is the default body type for all "normal" usable and static world objects. The "T" suffix
	    marks this body as active for translation and rotation, a normal bhkRigidBody ignores those
	    properties. Because the properties are equal, a bhkRigidBody may be renamed
	    into a bhkRigidBodyT and vice-versa.
	    <add name="Unknown Int 1" type="int">Unknown. Could be 2 shorts corresponding to Unknown 7 Shorts[1] and [2].</add>
	    <add name="Unknown Int 2" type="int" default="0x00000001">Unknown.</add>
	    <add name="Unknown 3 Ints" type="int" arr1="3" default="0 0 0x80000000">Unknown. Could be 3 floats.</add>
	    <add name="Collision Response?" type="hkResponseType" default="RESPONSE_SIMPLE_CONTACT">The collision response. See hkResponseType for hkpWorld default implementations.</add>
	    <add name="Unknown Byte" type="byte" default="0xbe">Unknown</add>
	    <add name="Process Contact Callback Delay?" type="ushort" default="0xffff">Lowers the frequency for processContactCallbacks. A value of 5 means that a callback is raised every 5th frame.</add>
	    <add name="Unknown 2 Shorts" type="ushort" arr1="2" default="35899 16336">Unknown.</add>
	    <add name="Layer Copy" type="OblivionLayer" default="OL_STATIC">Copy of Layer value?</add>
	    <add name="Col Filter Copy" type="byte" default="0">Copy of Col Filter value?</add>
	    <add name="Unknown 7 Shorts" type="ushort" arr1="7" default="0 21280 2481 62977 65535 44 0">Unknown.</add>
	    <add name="Translation" type="Vector4"> A vector that moves the body by the specified amount. Only enabled in bhkRigidBodyT objects.</add>
	    <add name="Rotation" type="QuaternionXYZW">The rotation Yaw/Pitch/Roll to apply to the body. Only enabled in bhkRigidBodyT objects.</add>
	    <add name="Linear Velocity" type="Vector4">Linear velocity.</add>
	    <add name="Angular Velocity" type="Vector4">Angular velocity.</add>
	    <add name="Inertia Tensors" type="InertiaMatrix">Defines how the mass is distributed among the body.</add>
	    <add name="Center" type="Vector4"> This seems to be used to relocate the object's center of mass. Useful for balancing objects in contraints.</add>
	    <add name="Mass" type="float" default="1.0">The body's mass in kg. A mass of zero represents an immovable object.</add>
	    <add name="Linear Damping" type="float" default="0.1"> Damping value for linear movement. A value that is too small fixes the object in place.</add>
	    <add name="Angular Damping" type="float" default="0.05"> Damping value for angular movement.</add>
	    <add name="TimeFactor or GravityFactor?" type="float" vercond="(User Version >= 12)" />
	    <add name="TimeFactor or GravityFactor?" type="float" vercond="(User Version >= 12)" />
	    <add name="Friction" type="float" default="0.3">The body&#039;s friction.</add>
	    <add name="RollingFrictionMultiplier?" type="float" vercond="(User Version >= 12)" />
	    <add name="Restitution" type="float" default="0.3">
	        The body&#039;s restitution (elasticity).
	        If the restitution is not 0.0 the object will need extra CPU for all new collisions.
	        Try to set restitution to 0 for maximum performance (e.g. collapsing buildings)
	    </add>
	    <add name="Max Linear Velocity" type="float" default="250.0">Maximal linear velocity.</add>
	    <add name="Max Angular Velocity" type="float" default="31.4159">Maximal angular velocity. Pi x 10?</add>
	    <add name="Penetration Depth" type="float" default="0.15">
	        The maximum allowed penetration for this object.
	        This is a hint to the engine to see how much CPU the engine should invest to keep this object from penetrating.
	        A good choice is 5% - 20% of the smallest diameter of the object.
	    </add>
	    <add name="Motion System" type="MotionSystem" default="MO_SYS_DYNAMIC">Motion system? Overrides Quality when on Keyframed?</add>
	    <add name="Deactivator Type" type="DeactivatorType" default="DEACTIVATOR_NEVER">The initial deactivator type of the body.</add>
	    <add name="Solver Deactivation" type="SolverDeactivation" default="SOLVER_DEACTIVATION_OFF">Usually set to 1 for fixed objects, or set to 2 for moving ones. Seems to always be same as Unknown Byte 1.</add>
	    <add name="Quality Type" type="MotionQuality" default="MO_QUAL_FIXED">The motion type. Determines quality of motion?</add>
	    <add name="Unknown Int 6" type="uint" default="512">Unknown.</add>
	    <add name="Unknown Int 7" type="uint" default="160">Unknown.</add>
	    <add name="Unknown Int 8" type="uint" default="161">Unknown.</add>
	    <add name="Unknown Int 81" type="uint" vercond="(User Version >= 12)">Unknown. Skyrim only.</add>
	    <add name="Num Constraints" type="uint"> The number of constraints this object is bound to.</add>
	    <add name="Constraints" type="Ref" template="bhkSerializable" arr1="Num Constraints">Unknown.</add>
	    <add name="Unknown Int 9" type="uint" vercond="(User Version &lt;= 11)">0 = do not respond to wind, 1 = respond to wind (?)</add>
	    <add name="Unknown Int 91" type="ushort" vercond="(User Version >= 12)"></add>
	</niobject>

	 
	 */

	public int unknownInt1;

	public int unknownInt2;

	public int[] unknown3Ints;

	public hkResponseType collisionResponse;

	public byte unknownByte;

	public short processContactCallbackDelay;

	public short[] unknown2Shorts;

	public OblivionLayer layerCopy;

	public byte colFilterCopy;

	public short[] unknown7Shorts;

	public NifVector4 translation;

	public NifQuaternionXYZW rotation;

	public NifVector4 linearVelocity;

	public NifVector4 angularVelocity;

	public NifInertialMatrix inertia;

	public NifVector4 center;

	public float mass;

	public float linearDamping;

	public float angularDamping;

	public float TimeFactororGravityFactor1;

	public float TimeFactororGravityFactor2;

	public float friction;

	public float RollingFrictionMultiplier;

	public float restitution;

	public float maxLinearVelocity;

	public float maxAngularVelocity;

	public float penetrationDepth;

	public MotionSystem motionSystem;

	public DeactivatorType deactivatorType;

	public SolverDeactivation solverDeactivation;

	public MotionQuality qualityType;

	public int autoRemoveLevel;

	public int userDatasInContactPointProperties;

	public int forceCollideOntoPpu;

	public int unknownInt81;

	public int numConstraints;

	public NifRef[] constraints;

	public int unknownInt9;

	public short unknownInt91;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		unknownInt1 = ByteConvert.readInt(stream);
		unknownInt2 = ByteConvert.readInt(stream);
		unknown3Ints = ByteConvert.readInts(3, stream);
		collisionResponse = new hkResponseType(stream);
		unknownByte = ByteConvert.readByte(stream);
		processContactCallbackDelay = ByteConvert.readShort(stream);
		unknown2Shorts = ByteConvert.readShorts(2, stream);
		layerCopy = new OblivionLayer(stream);
		colFilterCopy = ByteConvert.readByte(stream);
		unknown7Shorts = ByteConvert.readShorts(7, stream);
		translation = new NifVector4(stream);
		rotation = new NifQuaternionXYZW(stream);
		linearVelocity = new NifVector4(stream);
		angularVelocity = new NifVector4(stream);
		inertia = new NifInertialMatrix(stream);
		center = new NifVector4(stream);
		mass = ByteConvert.readFloat(stream);
		linearDamping = ByteConvert.readFloat(stream);
		angularDamping = ByteConvert.readFloat(stream);
		
		if (nifVer.LOAD_VER == NifVer.VER_20_2_0_7 && nifVer.LOAD_USER_VER >= 12)
		{
			TimeFactororGravityFactor1 = ByteConvert.readFloat(stream);
		}
		if (nifVer.LOAD_VER == NifVer.VER_20_2_0_7 && nifVer.LOAD_USER_VER >= 12)
		{
			TimeFactororGravityFactor2 = ByteConvert.readFloat(stream);
		}

		friction = ByteConvert.readFloat(stream);
		
		if (nifVer.LOAD_VER == NifVer.VER_20_2_0_7 && nifVer.LOAD_USER_VER >= 12)
		{
			RollingFrictionMultiplier = ByteConvert.readFloat(stream);
		}

		restitution = ByteConvert.readFloat(stream);
		maxLinearVelocity = ByteConvert.readFloat(stream);
		maxAngularVelocity = ByteConvert.readFloat(stream);
		penetrationDepth = ByteConvert.readFloat(stream);
		motionSystem = new MotionSystem(stream);
		deactivatorType = new DeactivatorType(stream);
		solverDeactivation = new SolverDeactivation(stream);
		qualityType = new MotionQuality(stream);
		autoRemoveLevel = ByteConvert.readInt(stream);
		userDatasInContactPointProperties = ByteConvert.readInt(stream);
		forceCollideOntoPpu = ByteConvert.readInt(stream);
		if (nifVer.LOAD_VER == NifVer.VER_20_2_0_7 && nifVer.LOAD_USER_VER >= 12)
		{
			unknownInt81 = ByteConvert.readInt(stream);
		}
		numConstraints = ByteConvert.readInt(stream);
		constraints = new NifRef[numConstraints];
		for (int i = 0; i < numConstraints; i++)
		{
			constraints[i] = new NifRef(bhkSerializable.class, stream);
		}
		if ( nifVer.LOAD_USER_VER <= 11)
		{
			unknownInt9 = ByteConvert.readInt(stream);

		}
		if (nifVer.LOAD_VER == NifVer.VER_20_2_0_7 && nifVer.LOAD_USER_VER >= 12)
		{
			unknownInt91 = ByteConvert.readShort(stream);
		}
		return success;
	}
}