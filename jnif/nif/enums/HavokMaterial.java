package nif.enums;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class HavokMaterial
{
	/**
	 * A material, used by havok shape objects.
	 */
	public static final int HAV_MAT_STONE = 0;// Stone

	public static final int HAV_MAT_CLOTH = 1;// Cloth

	public static final int HAV_MAT_DIRT = 2;// Dirt

	public static final int HAV_MAT_GLASS = 3;// Glass

	public static final int HAV_MAT_GRASS = 4;// Grass

	public static final int HAV_MAT_METAL = 5;// Metal

	public static final int HAV_MAT_ORGANIC = 6;// Organic

	public static final int HAV_MAT_SKIN = 7;// Skin

	public static final int HAV_MAT_WATER = 8;// Water

	public static final int HAV_MAT_WOOD = 9;// Wood

	public static final int HAV_MAT_HEAVY_STONE = 10;// Heavy Stone

	public static final int HAV_MAT_HEAVY_METAL = 11;// Heavy Metal

	public static final int HAV_MAT_HEAVY_WOOD = 12;// Heavy Wood

	public static final int HAV_MAT_CHAIN = 13;// Chain

	public static final int HAV_MAT_SNOW = 14;// Snow

	public static final int HAV_MAT_STONE_STAIRS = 15;// Stone Stairs

	public static final int HAV_MAT_CLOTH_STAIRS = 16;// Cloth Stairs

	public static final int HAV_MAT_DIRT_STAIRS = 17;// Dirt Stairs

	public static final int HAV_MAT_GLASS_STAIRS = 18;// Glass Stairs

	public static final int HAV_MAT_GRASS_STAIRS = 19;// Grass Stairs

	public static final int HAV_MAT_METAL_STAIRS = 20;// Metal Stairs

	public static final int HAV_MAT_ORGANIC_STAIRS = 21;// Organic Stairs

	public static final int HAV_MAT_SKIN_STAIRS = 22;// Skin Stairs

	public static final int HAV_MAT_WATER_STAIRS = 23;// Water Stairs

	public static final int HAV_MAT_WOOD_STAIRS = 24;// Wood Stairs

	public static final int HAV_MAT_HEAVY_STONE_STAIRS = 25;// Heavy Stone Stairs

	public static final int HAV_MAT_HEAVY_METAL_STAIRS = 26;// Heavy Metal Stairs

	public static final int HAV_MAT_HEAVY_WOOD_STAIRS = 27;// Heavy Wood Stairs

	public static final int HAV_MAT_CHAIN_STAIRS = 28;// Chain Stairs

	public static final int HAV_MAT_SNOW_STAIRS = 29;// Snow Stairs

	public static final int HAV_MAT_ELEVATOR = 30;// Elevator

	public int material; // uint

	public HavokMaterial(InputStream stream) throws IOException
	{
		material = ByteConvert.readInt(stream);
	}
}
