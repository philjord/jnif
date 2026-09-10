package nif.enums;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class SkyrimLayer {
	/**
	 * Bethesda Havok. Describes the collision layer a body belongs to in Skyrim.
	 */

	public static final int	SKYL_UNIDENTIFIED			= 0;	//Unidentified
	public static final int	SKYL_STATIC					= 1;	//Static
	public static final int	SKYL_ANIMSTATIC				= 2;	//Anim Static
	public static final int	SKYL_TRANSPARENT			= 3;	//Transparent
	public static final int	SKYL_CLUTTER				= 4;	//Clutter. Object with this layer will float on water surface.
	public static final int	SKYL_WEAPON					= 5;	//Weapon
	public static final int	SKYL_PROJECTILE				= 6;	//Projectile
	public static final int	SKYL_SPELL					= 7;	//Spell
	public static final int	SKYL_BIPED					= 8;	//Biped. Seems to apply to all creatures/NPCs
	public static final int	SKYL_TREES					= 9;	//Trees
	public static final int	SKYL_PROPS					= 10;	//Props
	public static final int	SKYL_WATER					= 11;	//Water
	public static final int	SKYL_TRIGGER				= 12;	//Trigger
	public static final int	SKYL_TERRAIN				= 13;	//Terrain
	public static final int	SKYL_TRAP					= 14;	//Trap
	public static final int	SKYL_NONCOLLIDABLE			= 15;	//NonCollidable
	public static final int	SKYL_CLOUD_TRAP				= 16;	//CloudTrap
	public static final int	SKYL_GROUND					= 17;	//Ground. It seems that produces no sound when collide.
	public static final int	SKYL_PORTAL					= 18;	//Portal
	public static final int	SKYL_DEBRIS_SMALL			= 19;	//Debris Small
	public static final int	SKYL_DEBRIS_LARGE			= 20;	//Debris Large
	public static final int	SKYL_ACOUSTIC_SPACE			= 21;	//Acoustic Space
	public static final int	SKYL_ACTORZONE				= 22;	//Actor Zone
	public static final int	SKYL_PROJECTILEZONE			= 23;	//Projectile Zone
	public static final int	SKYL_GASTRAP				= 24;	//Gas Trap
	public static final int	SKYL_SHELLCASING			= 25;	//Shell Casing
	public static final int	SKYL_TRANSPARENT_SMALL		= 26;	//Transparent Small
	public static final int	SKYL_INVISIBLE_WALL			= 27;	//Invisible Wall
	public static final int	SKYL_TRANSPARENT_SMALL_ANIM	= 28;	//Transparent Small Anim
	public static final int	SKYL_WARD					= 29;	//Ward
	public static final int	SKYL_CHARCONTROLLER			= 30;	//Char Controller
	public static final int	SKYL_STAIRHELPER			= 31;	//Stair Helper
	public static final int	SKYL_DEADBIP				= 32;	//Dead Bip
	public static final int	SKYL_BIPED_NO_CC			= 33;	//Biped No CC
	public static final int	SKYL_AVOIDBOX				= 34;	//Avoid Box
	public static final int	SKYL_COLLISIONBOX			= 35;	//Collision Box
	public static final int	SKYL_CAMERASHPERE			= 36;	//Camera Sphere
	public static final int	SKYL_DOORDETECTION			= 37;	//Door Detection
	public static final int	SKYL_CONEPROJECTILE			= 38;	//Cone Projectile
	public static final int	SKYL_CAMERAPICK				= 39;	//Camera Pick
	public static final int	SKYL_ITEMPICK				= 40;	//Item Pick
	public static final int	SKYL_LINEOFSIGHT			= 41;	//Line of Sight
	public static final int	SKYL_PATHPICK				= 42;	//Path Pick
	public static final int	SKYL_CUSTOMPICK1			= 43;	//Custom Pick 1
	public static final int	SKYL_CUSTOMPICK2			= 44;	//Custom Pick 2
	public static final int	SKYL_SPELLEXPLOSION			= 45;	//Spell Explosion
	public static final int	SKYL_DROPPINGPICK			= 46;	//Dropping Pick
	public static final int	SKYL_DEADACTORZONE			= 47;	//Dead Actor Zone
	public static final int	SKYL_TRIGGER_FALLINGTRAP	= 48;	//Falling Trap Trigger
	public static final int	SKYL_NAVCUT					= 49;	//Nav Cut
	public static final int	SKYL_CRITTER				= 50;	//Critter
	public static final int	SKYL_SPELLTRIGGER			= 51;	//Spell Trigger
	public static final int	SKYL_LIVING_AND_DEAD_ACTORS	= 52;	//Living And Dead Actors
	public static final int	SKYL_DETECTION				= 53;	//Detection
	public static final int	SKYL_TRAP_TRIGGER			= 54;	//Trap Trigger

	public int				layer;

	public SkyrimLayer(ByteBuffer stream) throws IOException {
		layer = ByteConvert.readByte(stream);
	}
}
