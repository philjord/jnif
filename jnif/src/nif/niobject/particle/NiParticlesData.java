package nif.niobject.particle;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifQuaternion;
import nif.compound.NifVector3;
import nif.compound.NifVector4;
import nif.niobject.NiGeometryData;
 

public class NiParticlesData extends NiGeometryData
{

	/**
	
	 <niobject name="NiParticlesData" inherit="NiGeometryData" module="NiMain">
        Generic rotating particles data object.
        Bethesda 20.2.0.7 NIFs: NiParticlesData no longer inherits from NiGeometryData and inherits NiObject directly.
        <field name="Num Particles" type="ushort" until="4.0.0.2">The maximum number of particles (matches the number of vertices).</field>
        <field name="Particle Radius" type="float" until="10.0.1.0">The particles' size.</field>
        <field name="Has Radii" type="bool" since="10.1.0.0">Is the particle size array present?</field>
        <field name="Radii" type="float" length="Num Vertices" cond="Has Radii" since="10.1.0.0" vercond="!#BS202#">The individual particle sizes.</field>
        <field name="Num Active" type="ushort">The number of active particles at the time the system was saved. This is also the number of valid entries in the following arrays.</field>
        <field name="Has Sizes" type="bool">Is the particle size array present?</field>
        <field name="Sizes" type="float" length="Num Vertices" cond="Has Sizes" vercond="!#BS202#">The individual particle sizes.</field>
        <field name="Has Rotations" type="bool" since="10.0.1.0">Is the particle rotation array present?</field>
        <field name="Rotations" type="Quaternion" length="Num Vertices" cond="Has Rotations" since="10.0.1.0" vercond="!#BS202#">The individual particle rotations.</field>
        <field name="Has Rotation Angles" type="bool" since="20.0.0.4">Are the angles of rotation present?</field>
        <field name="Rotation Angles" type="float" length="Num Vertices" cond="Has Rotation Angles" vercond="!#BS202#">Angles of rotation</field>
        <field name="Has Rotation Axes" type="bool" since="20.0.0.4">Are axes of rotation present?</field>
        <field name="Rotation Axes" type="Vector3" length="Num Vertices" cond="Has Rotation Axes" since="20.0.0.4" vercond="!#BS202#">Axes of rotation.</field>

        <field name="Has Texture Indices" type="bool" vercond="#BS202#" />
        <field name="Num Subtexture Offsets" type="uint" since="20.2.0.7" until="20.2.0.7" vercond="#BS_GT_FO3#">How many quads to use in BSPSysSubTexModifier for texture atlasing</field>
        <field name="Num Subtexture Offsets" type="byte" since="20.2.0.7" until="20.2.0.7" vercond="#BSSTREAM# #AND# #NI_BS_LTE_FO3#">2,4,8,16,32,64 are potential values. If "Has" was no then this should be 256, which represents a 16x16 framed image, which is invalid</field>
        <field name="Subtexture Offsets" type="Vector4" length="Num Subtexture Offsets" vercond="#BS202#">Defines UV offsets</field>
        <field name="Aspect Ratio" type="float" since="20.2.0.7" until="20.2.0.7" vercond="#BS_GT_FO3#">Sets aspect ratio for Subtexture Offset UV quads</field>
        <field name="Aspect Flags" type="AspectFlags" since="20.2.0.7" until="20.2.0.7" vercond="#BS_GT_FO3#" />
        <field name="Speed to Aspect Aspect 2" type="float" since="20.2.0.7" until="20.2.0.7" vercond="#BS_GT_FO3#" />
        <field name="Speed to Aspect Speed 1" type="float" since="20.2.0.7" until="20.2.0.7" vercond="#BS_GT_FO3#" />
        <field name="Speed to Aspect Speed 2" type="float" since="20.2.0.7" until="20.2.0.7" vercond="#BS_GT_FO3#" />
    </niobject>

	 */

	public short numParticles;

	public float particlesRadius;

	public boolean hasRadii;

	public float[] radii;

	public short numActive;

	public boolean hasSizes;

	public float[] sizes;

	public boolean hasRotations1;

	public NifQuaternion[] rotations1;

	public boolean hasRotationAngles;

	public float[] rotationAngles;

	public boolean hasRotationAxes;

	public NifVector3[] rotationAxes;

	public boolean HasTextureIndices;	

	public int NumSubtextureOffsetUVs;

	public float AspectRatio;

	public NifVector4[] SubtextureOffsetUVs;
	
	public int AspectFlags;

	public float SpeedtoAspectAspect2;

	public float SpeedtoAspectSpeed1;

	public float SpeedtoAspectSpeed2;


	@Override
	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
        
		//<field name="Num Particles" type="ushort" until="4.0.0.2">The maximum number of particles (matches the number of vertices).</field>
        if (nifVer.LOAD_VER <= NifVer.VER_4_0_0_2)
			numParticles = ByteConvert.readShort(stream);
        
        //<field name="Particle Radius" type="float" until="10.0.1.0">The particles' size.</field>        
		if (nifVer.LOAD_VER <= NifVer.VER_10_0_1_0)
			particlesRadius = ByteConvert.readFloat(stream);


		if (nifVer.LOAD_VER >= NifVer.VER_10_0_1_0) {
			//<field name="Has Radii" type="bool" since="10.1.0.0">Is the particle size array present?</field>
	        hasRadii = ByteConvert.readBool(stream, nifVer);
	        //<field name="Radii" type="float" length="Num Vertices" cond="Has Radii" since="10.1.0.0" vercond="!#BS202#">The individual particle sizes.</field>
			if (!nifVer.BS202()) {
				if (hasRadii) {
					radii = new float[numVertices];
					for (int i = 0; i < numVertices; i++) {
						radii[i] = ByteConvert.readFloat(stream);
					}
				}
			}
		}		
       	
		//<field name="Num Active" type="ushort">The number of active particles at the time the system was saved. This is also the number of valid entries in the following arrays.</field>
        numActive = ByteConvert.readShort(stream);

        //<field name="Has Sizes" type="bool">Is the particle size array present?</field>
		hasSizes = ByteConvert.readBool(stream, nifVer);
		
		//<field name="Sizes" type="float" length="Num Vertices" cond="Has Sizes" vercond="!#BS202#">The individual particle sizes.</field>        
		if (!nifVer.BS202()) {
			if (hasSizes) {
				sizes = new float[numVertices];
				for (int i = 0; i < numVertices; i++) {
					sizes[i] = ByteConvert.readFloat(stream);
				}
			}
		}		
		     
		if (nifVer.LOAD_VER >= NifVer.VER_10_0_1_0) {
			//<field name="Has Rotations" type="bool" since="10.0.1.0">Is the particle rotation array present?</field>   
			hasRotations1 = ByteConvert.readBool(stream, nifVer);
			//<field name="Rotations" type="Quaternion" length="Num Vertices" cond="Has Rotations" since="10.0.1.0" vercond="!#BS202#">The individual particle rotations.</field>
			if (!nifVer.BS202()) {
				if (hasRotations1) {
					rotations1 = new NifQuaternion[numVertices];
					for (int i = 0; i < numVertices; i++) {
						rotations1[i] = new NifQuaternion(stream);
					}
				}
			}
		}

		if (nifVer.LOAD_VER >= NifVer.VER_20_0_0_4) {
			//<field name="Has Rotation Angles" type="bool" since="20.0.0.4">Are the angles of rotation present?</field>
			hasRotationAngles = ByteConvert.readBool(stream, nifVer);
			//<field name="Rotation Angles" type="float" length="Num Vertices" cond="Has Rotation Angles" vercond="!#BS202#">Angles of rotation</field>
			if (!nifVer.BS202()) {
				if (hasRotationAngles) {
					rotationAngles = new float[numVertices];
					for (int i = 0; i < numVertices; i++) {
						rotationAngles[i] = ByteConvert.readFloat(stream);
					}
				}
			}
		 
			//<field name="Has Rotation Axes" type="bool" since="20.0.0.4">Are axes of rotation present?</field>
	        hasRotationAxes = ByteConvert.readBool(stream, nifVer);
	        //<field name="Rotation Axes" type="Vector3" length="Num Vertices" cond="Has Rotation Axes" since="20.0.0.4" vercond="!#BS202#">Axes of rotation.</field>
	        if (!nifVer.BS202()) {
				if (hasRotationAxes) {
					rotationAxes = new NifVector3[numVertices];
					for (int i = 0; i < numVertices; i++) {
						rotationAxes[i] = new NifVector3(stream);
					}
				}
			}
		}
		
		//<field name="Has Texture Indices" type="bool" vercond="#BS202#" />
		if (nifVer.BS202()) 
			HasTextureIndices = ByteConvert.readBool(stream, nifVer);
 		
		if (nifVer.LOAD_VER == NifVer.VER_20_2_0_7) { 			
			//<field name="Num Subtexture Offsets" type="uint" since="20.2.0.7" until="20.2.0.7" vercond="#BS_GT_FO3#">How many quads to use in BSPSysSubTexModifier for texture atlasing</field>
			//<field name="Num Subtexture Offsets" type="byte" since="20.2.0.7" until="20.2.0.7" vercond="#BSSTREAM# #AND# #NI_BS_LTE_FO3#">2,4,8,16,32,64 are potential values. If "Has" was no then this should be 256, which represents a 16x16 framed image, which is invalid</field>
			if(nifVer.BS_GT_FO3())
				NumSubtextureOffsetUVs = ByteConvert.readInt(stream);
			if(nifVer.BSSTREAM() && nifVer.NI_BS_LTE_FO3())
				NumSubtextureOffsetUVs = ByteConvert.readByte(stream);
		
			//<field name="Subtexture Offsets" type="Vector4" length="Num Subtexture Offsets" vercond="#BS202#">Defines UV offsets</field>
			if (nifVer.BS202()) {
				SubtextureOffsetUVs = new NifVector4[NumSubtextureOffsetUVs];
				for (int i = 0; i < NumSubtextureOffsetUVs; i++) {
					SubtextureOffsetUVs[i] = new NifVector4(stream);
				}
			}			
			
			if(nifVer.BS_GT_FO3()) {
				//<field name="Aspect Ratio" type="float" since="20.2.0.7" until="20.2.0.7" vercond="#BS_GT_FO3#">Sets aspect ratio for Subtexture Offset UV quads</field>
				AspectRatio = ByteConvert.readFloat(stream);				
				//<field name="Aspect Flags" type="AspectFlags" since="20.2.0.7" until="20.2.0.7" vercond="#BS_GT_FO3#" />		        
				AspectFlags = ByteConvert.readUnsignedShort(stream);
				//<field name="Speed to Aspect Aspect 2" type="float" since="20.2.0.7" until="20.2.0.7" vercond="#BS_GT_FO3#" />		        
				SpeedtoAspectAspect2 = ByteConvert.readFloat(stream);
				//<field name="Speed to Aspect Speed 1" type="float" since="20.2.0.7" until="20.2.0.7" vercond="#BS_GT_FO3#" />		       
				SpeedtoAspectSpeed1 = ByteConvert.readFloat(stream);
				//<field name="Speed to Aspect Speed 2" type="float" since="20.2.0.7" until="20.2.0.7" vercond="#BS_GT_FO3#" />
				SpeedtoAspectSpeed2 = ByteConvert.readFloat(stream);	
 
			}
		}
		
		return success;
	}
}