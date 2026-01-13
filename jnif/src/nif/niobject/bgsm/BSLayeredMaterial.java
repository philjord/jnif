package nif.niobject.bgsm;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;

public class BSLayeredMaterial
{
	/**
	 * I wonder if abstract="true" means it an external file to a nif
    <struct name="BSLayeredMaterial" abstract="true" module="BSMain" versions="#STF#">
        <field name="Name" type="SizedString" />
        <field name="Layer Enable Mask" type="uint" />
        <field name="Layer 1" type="BSLayer" cond="Layer Enable Mask #BITAND# 1" />
        <field name="Layer 2" type="BSLayer" cond="Layer Enable Mask #BITAND# 2" />
        <field name="Blender Enable Mask" type="uint" />
        <field name="Blender 1" type="BSBlender" cond="Blender Enable Mask #BITAND# 1" />
        <field name="Layer 3" type="BSLayer" cond="Layer Enable Mask #BITAND# 4" />
        <field name="Blender 2" type="BSBlender" cond="Blender Enable Mask #BITAND# 2" />
        <field name="Layer 4" type="BSLayer" cond="Layer Enable Mask #BITAND# 8" />
        <field name="Blender 3" type="BSBlender" cond="Blender Enable Mask #BITAND# 4" />
        <field name="Layer 5" type="BSLayer" cond="Layer Enable Mask #BITAND# 16" />
        <field name="Blender 4" type="BSBlender" cond="Blender Enable Mask #BITAND# 8" />
        <field name="Layer 6" type="BSLayer" cond="Layer Enable Mask #BITAND# 32" />
        <field name="Blender 5" type="BSBlender" cond="Blender Enable Mask #BITAND# 16" />
        <field name="Shader Model" type="SizedString" default="BaseMaterial" />
        <field name="Shader Route" type="BSShaderRoute" />
        <field name="Two Sided" type="bool" />
        <field name="Physics Material Type" type="BSPhysicsMaterialType" />
        <field name="Has Opacity" type="bool" cond="Shader Route #NEQ# 1" />
        <field name="Alpha Settings" type="BSAlphaSettingsComponent" cond="(Shader Route #NEQ# 1) #AND# Has Opacity" />
        <field name="Detail Blend Mask Supported" type="bool" />
        <field name="Detail Blender Settings" type="BSDetailBlenderSettings" cond="Detail Blend Mask Supported" />
        <field name="Has Opacity Component" type="bool" cond="Shader Route #EQ# 1" />
        <field name="Opacity Settings" type="BSOpacityComponent" cond="(Shader Route #EQ# 1) #AND# Has Opacity Component" />
        <field name="Is Effect" type="bool" />
        <field name="Effect Settings" type="BSEffectSettingsComponent" cond="Is Effect" />
        <field name="Use Layered Edge Falloff" type="bool" cond="Is Effect" />
        <field name="Layered Edge Falloff" type="BSLayeredEdgeFalloffComponent" cond="Is Effect #AND# Use Layered Edge Falloff" />
        <field name="Is Decal" type="bool" />
        <field name="Decal Settings" type="BSDecalSettingsComponent" cond="Is Decal" />
        <field name="Is Water" type="bool" />
        <field name="Water Settings" type="BSWaterSettingsComponent" cond="Is Water" />
        <field name="Is Emissive" type="bool" />
        <field name="Emissive Settings" type="BSEmissiveSettingsComponent" cond="Is Emissive" />
        <field name="Layered Emissivity" type="bool" />
        <field name="Layered Emissivity Settings" type="BSLayeredEmissivityComponent" cond="Layered Emissivity" />
        <field name="Is Translucent" type="bool" />
        <field name="Translucency Settings" type="BSTranslucencySettingsComponent" cond="Is Translucent" />
        <field name="Is Hair" type="bool" />
        <field name="Hair Settings" type="BSHairSettingsComponent" cond="Is Hair" />
        <field name="Is Vegetation" type="bool" />
        <field name="Vegetation Settings" type="BSVegetationSettingsComponent" cond="Is Vegetation" />
        <field name="Is Modified" type="bool" />
    </struct>

	 */
/*	
	public String							Name;
	public int								LayerEnableMask;
	public BSLayer							Layer1;
	public BSLayer							Layer2;
	public int								BlenderEnableMask;
	public BSBlender						Blender1;
	public BSLayer							Layer3;
	public BSBlender						Blender2;
	public BSLayer							Layer4;
	public BSBlender						Blender3;
	public BSLayer							Layer5;
	public BSBlender						Blender4;
	public BSLayer							Layer6;
	public BSBlender						Blender5;
	public String							ShaderModel;
	public BSShaderRoute					ShaderRoute;
	public boolean							TwoSided;
	public BSPhysicsMaterialType			PhysicsMaterialType;
	public boolean							HasOpacity;
	public BSAlphaSettingsComponent			AlphaSettings;
	public boolean							DetailBlendMaskSupported;
	public BSDetailBlenderSettings			DetailBlenderSettings;
	public boolean							HasOpacityComponent;
	public BSOpacityComponent				OpacitySettings;
	public boolean							IsEffect;
	public BSEffectSettingsComponent		EffectSettings;
	public boolean							UseLayeredEdgeFalloff;
	public BSLayeredEdgeFalloffComponent	LayeredEdgeFalloff;
	public boolean							IsDecal;
	public BSDecalSettingsComponent			DecalSettings;
	public boolean							IsWater;
	public BSWaterSettingsComponent			WaterSettings;
	public boolean							IsEmissive;
	public BSEmissiveSettingsComponent		EmissiveSettings;
	public boolean							LayeredEmissivity;
	public BSLayeredEmissivityComponent		LayeredEmissivitySettings;
	public boolean							IsTranslucent;
	public BSTranslucencySettingsComponent	TranslucencySettings;
	public boolean							IsHair;
	public BSHairSettingsComponent			HairSettings;
	public boolean							IsVegetation;
	public BSVegetationSettingsComponent	VegetationSettings;
	public boolean							IsModified;
   */  
   

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer)  throws IOException
	{
		System.err.println("Not finished BSLayeredMaterial");
		return true;
		 //<field name="Name" type="SizedString" />
	        //<field name="Layer Enable Mask" type="uint" />
	        //<field name="Layer 1" type="BSLayer" cond="Layer Enable Mask #BITAND# 1" />
	        //<field name="Layer 2" type="BSLayer" cond="Layer Enable Mask #BITAND# 2" />
	        //<field name="Blender Enable Mask" type="uint" />
	        //<field name="Blender 1" type="BSBlender" cond="Blender Enable Mask #BITAND# 1" />
	        //<field name="Layer 3" type="BSLayer" cond="Layer Enable Mask #BITAND# 4" />
	        //<field name="Blender 2" type="BSBlender" cond="Blender Enable Mask #BITAND# 2" />
	        //<field name="Layer 4" type="BSLayer" cond="Layer Enable Mask #BITAND# 8" />
	        //<field name="Blender 3" type="BSBlender" cond="Blender Enable Mask #BITAND# 4" />
	        //<field name="Layer 5" type="BSLayer" cond="Layer Enable Mask #BITAND# 16" />
	        //<field name="Blender 4" type="BSBlender" cond="Blender Enable Mask #BITAND# 8" />
	        //<field name="Layer 6" type="BSLayer" cond="Layer Enable Mask #BITAND# 32" />
	        //<field name="Blender 5" type="BSBlender" cond="Blender Enable Mask #BITAND# 16" />
	        //<field name="Shader Model" type="SizedString" default="BaseMaterial" />
	        //<field name="Shader Route" type="BSShaderRoute" />
	        //<field name="Two Sided" type="bool" />
	        //<field name="Physics Material Type" type="BSPhysicsMaterialType" />
	        //<field name="Has Opacity" type="bool" cond="Shader Route #NEQ# 1" />
	        //<field name="Alpha Settings" type="BSAlphaSettingsComponent" cond="(Shader Route #NEQ# 1) #AND# Has Opacity" />
	        //<field name="Detail Blend Mask Supported" type="bool" />
	        //<field name="Detail Blender Settings" type="BSDetailBlenderSettings" cond="Detail Blend Mask Supported" />
	        //<field name="Has Opacity Component" type="bool" cond="Shader Route #EQ# 1" />
	        //<field name="Opacity Settings" type="BSOpacityComponent" cond="(Shader Route #EQ# 1) #AND# Has Opacity Component" />
	        //<field name="Is Effect" type="bool" />
	        //<field name="Effect Settings" type="BSEffectSettingsComponent" cond="Is Effect" />
	        //<field name="Use Layered Edge Falloff" type="bool" cond="Is Effect" />
	        //<field name="Layered Edge Falloff" type="BSLayeredEdgeFalloffComponent" cond="Is Effect #AND# Use Layered Edge Falloff" />
	        //<field name="Is Decal" type="bool" />
	        //<field name="Decal Settings" type="BSDecalSettingsComponent" cond="Is Decal" />
	        //<field name="Is Water" type="bool" />
	        //<field name="Water Settings" type="BSWaterSettingsComponent" cond="Is Water" />
	        //<field name="Is Emissive" type="bool" />
	        //<field name="Emissive Settings" type="BSEmissiveSettingsComponent" cond="Is Emissive" />
	        //<field name="Layered Emissivity" type="bool" />
	        //<field name="Layered Emissivity Settings" type="BSLayeredEmissivityComponent" cond="Layered Emissivity" />
	        //<field name="Is Translucent" type="bool" />
	        //<field name="Translucency Settings" type="BSTranslucencySettingsComponent" cond="Is Translucent" />
	        //<field name="Is Hair" type="bool" />
	        //<field name="Hair Settings" type="BSHairSettingsComponent" cond="Is Hair" />
	        //<field name="Is Vegetation" type="bool" />
	        //<field name="Vegetation Settings" type="BSVegetationSettingsComponent" cond="Is Vegetation" />
	        //<field name="Is Modified" type="bool" />
	        
	        

	}
}
