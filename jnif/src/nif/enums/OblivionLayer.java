package nif.enums;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class OblivionLayer
{
	/**
	 * Sets mesh color in Oblivion Construction Set. Anything higher than 57 is also null.
	 */
	public static final int OL_UNIDENTIFIED = 0;// Unidentified (white)

	public static final int OL_STATIC = 1;// Static (red)

	public static final int OL_ANIM_STATIC = 2;// AnimStatic (magenta)

	public static final int OL_TRANSPARENT = 3;// Transparent (light pink)

	public static final int OL_CLUTTER = 4;// Clutter (light blue)

	public static final int OL_WEAPON = 5;// Weapon (orange)

	public static final int OL_PROJECTILE = 6;// Projectile (light orange)

	public static final int OL_SPELL = 7;// Spell (cyan)

	public static final int OL_BIPED = 8;// Biped (green) Seems to apply to all creatures/NPCs

	public static final int OL_TREES = 9;// Trees (light brown)

	public static final int OL_PROPS = 10;// Props (magenta)

	public static final int OL_WATER = 11;// Water (cyan)

	public static final int OL_TRIGGER = 12;// Trigger (light grey)

	public static final int OL_TERRAIN = 13;// Terrain (light yellow)

	public static final int OL_TRAP = 14;// Trap (light grey)

	public static final int OL_NONCOLLIDABLE = 15;// NonCollidable (white)

	public static final int OL_CLOUD_TRAP = 16;// CloudTrap (greenish grey)

	public static final int OL_GROUND = 17;// Ground (none)

	public static final int OL_PORTAL = 18;// Portal (green)

	public static final int OL_STAIRS = 19;// Stairs (white)

	public static final int OL_CHAR_CONTROLLER = 20;// CharController (yellow)

	public static final int OL_AVOID_BOX = 21;// AvoidBox (dark yellow)

	public static final int OL_UNKNOWN1 = 22;// ? (white)

	public static final int OL_UNKNOWN2 = 23;// ? (white)

	public static final int OL_CAMERA_PICK = 24;// CameraPick (white)

	public static final int OL_ITEM_PICK = 25;// ItemPick (white)

	public static final int OL_LINE_OF_SIGHT = 26;// LineOfSight (white)

	public static final int OL_PATH_PICK = 27;// PathPick (white)

	public static final int OL_CUSTOM_PICK_1 = 28;// CustomPick1 (white)

	public static final int OL_CUSTOM_PICK_2 = 29;// CustomPick2 (white)

	public static final int OL_SPELL_EXPLOSION = 30;// SpellExplosion (white)

	public static final int OL_DROPPING_PICK = 31;// DroppingPick (white)

	public static final int OL_OTHER = 32;// Other (white)

	public static final int OL_HEAD = 33;// Head

	public static final int OL_BODY = 34;// Body

	public static final int OL_SPINE1 = 35;// Spine1

	public static final int OL_SPINE2 = 36;// Spine2

	public static final int OL_L_UPPER_ARM = 37;// LUpperArm

	public static final int OL_L_FOREARM = 38;// LForeArm

	public static final int OL_L_HAND = 39;// LHand

	public static final int OL_L_THIGH = 40;// LThigh

	public static final int OL_L_CALF = 41;// LCalf

	public static final int OL_L_FOOT = 42;// LFoot

	public static final int OL_R_UPPER_ARM = 43;// RUpperArm

	public static final int OL_R_FOREARM = 44;// RForeArm

	public static final int OL_R_HAND = 45;// RHand

	public static final int OL_R_THIGH = 46;// RThigh

	public static final int OL_R_CALF = 47;// RCalf

	public static final int OL_R_FOOT = 48;// RFoot

	public static final int OL_TAIL = 49;// Tail

	public static final int OL_SIDE_WEAPON = 50;// SideWeapon

	public static final int OL_SHIELD = 51;// Shield

	public static final int OL_QUIVER = 52;// Quiver

	public static final int OL_BACK_WEAPON = 53;// BackWeapon

	public static final int OL_BACK_WEAPON2 = 54;// BackWeapon (?)

	public static final int OL_PONYTAIL = 55;// PonyTail

	public static final int OL_WING = 56;// Wing

	public static final int OL_NULL = 57;// Null

	public int layer;

	public OblivionLayer(InputStream stream) throws IOException
	{
		layer = ByteConvert.readByte(stream);
	}
}
