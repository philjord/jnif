package nif;

import java.util.ArrayList;

public class NifVer
{
	/**
	 * #BS_GT_130#
	 * #BS_GTE_F76#
	 * #BS_FO4#
	 * #FO4# 
	 * #F76#
	 * #BS_GTE_132#
	 * #NI_BS_LTE_FO4#
	 * #BS_GTE_F76#
	 * #BS_GTE_STF#
	 * #BS_SSE_FO4_FO76#
	 * 
	 * 
	 * 
	 * 
	 * 
	 <version num="2.3">Dark Age of Camelot</version>
	 <version num="3.0">Star Trek:  IV</version>
	 <version num="4.2.1.0">Dark Age of Camelot, Civilization IV</version>
	 <version num="4.2.2.0">
	 Dark Age of Camelot, Civilization IV, Empire Earth II, Culpa InnataBridge Commander</version>
	 <version num="3.03">Dark Age of Camelot</version>
	 <version num="3.1">Dark Age of Camelot, Star Trek: Bridge Commander</version>
	 <version num="3.3.0.13">Oblivion</version>
	 <version num="4.0.0.0">Freedom Force</version>
	 <version num="4.0.0.2">Morrowind, Freedom Force</version>
	 <version num="4.1.0.12">Dark Age of Camelot</version>
	 <version num="4.2.0.2">Civilization </version>
	 <version num="4.2.2.0">? </version>
	 <version num="10.0.1.0">Zoo Tycoon 2, Civilization IV</version>
	 <version num="10.0.1.2">Oblivion</version>
	 <version num="10.0.1.3">?</version>
	 <version num="10.1.0.0">
	 Dark Age of Camelot, Civilization IV, Freedom Force vs. the 3rd Reich, Axis and Allies, Kohan 2, Entropia Universe, Wildlife Park 2, The Guild 2
	 </version>
	 <version num="10.1.0.101">Oblivion</version>
	 <version num="10.1.0.106">Oblivion</version>
	 <version num="10.2.0.0">Civilization IV, Oblivion, Loki, Pro Cycling Manager, Prison Tycoon, Red Ocean, Wildlife Park 2 </version>
	 <version num="20.0.0.4">Civilization IV, Oblivion, Sid Meier's Railroads</version>
	 <version num="20.0.0.5">Oblivion</version>
	 <version num="20.1.0.3">Megami Tensei: Imagine</version>
	 <version num="20.2.0.7">Emerge, Empire Earth III, Fallout 3, Skyrim</version>
	 <version num="20.2.0.8">Emerge, Empire Earth III</version>
	 <!--
	 version 20.3.0.1 is found in Props/GDCLargeContainer.nif
	 -->
	 <version num="20.3.0.1">Emerge</version>
	 <!--
	 version 20.3.0.2 is found in PFX/fx_muzzle_flash_example.nif 
	 -->
	 <version num="20.3.0.2">Emerge</version>
	 <version num="20.3.0.3">Emerge</version>
	 <version num="20.3.0.6">Emerge</version>
	 <version num="20.3.0.9">Warhammer, Lazeska, Divinity 2, Howling Sword, Bully SE</version>
	 <version num="20.5.0.0">KrazyRain</version>
	 <version num="20.6.0.0">KrazyRain</version>
	 <version num="20.6.5.0">Epic Mickey</version>
	 <version num="30.0.0.2">Emerge</version>
	
	
	 */

	//Skyrim version= 20.2.0.7, User version 1= 11, User Version 2=83.
	//Right now I have a few messy edits in the header.

	//	NIF Version Constants

	public static final int VER_2_3 = 0x02030000;

	public static final int VER_3_0 = 0x03000000;

	public static final int VER_3_03 = 0x03000300;

	public static final int VER_3_1 = 0x03010000;

	public static final int VER_3_3_0_13 = 0x0303000D;

	public static final int VER_4_0_0_0 = 0x04000000;

	public static final int VER_4_0_0_2 = 0x04000002;

	public static final int VER_4_1_0_1 = 0x04010001;

	public static final int VER_4_1_0_12 = 0x0401000C;

	public static final int VER_4_2_0_2 = 0x04020002;

	public static final int VER_4_2_1_0 = 0x04020100;

	public static final int VER_4_2_2_0 = 0x04020200;

	public static final int VER_5_0_0_1 = 0x05000001;

	public static final int VER_10_0_1_0 = 0x0A000100;

	public static final int VER_10_0_1_2 = 0x0A000102;

	public static final int VER_10_0_1_3 = 0x0A000103;

	public static final int VER_10_1_0_0 = 0x0A010000;

	public static final int VER_10_1_0_101 = 0x0A010065;

	public static final int VER_10_1_0_106 = 0x0A01006A;

	public static final int VER_10_2_0_0 = 0x0A020000;

	public static final int VER_10_4_0_1 = 0x0A040001;

	public static final int VER_20_0_0_4 = 0x14000004;

	public static final int VER_20_0_0_5 = 0x14000005;

	public static final int VER_20_1_0_3 = 0x14010003;

	public static final int VER_20_2_0_6 = 0x14020006;

	public static final int VER_20_2_0_7 = 0x14020007; // skyrim wih userversion1=12 and 2=83, fallout4 uv1=12 uv2=130, FO76 uv1=12 and uv2=155

	public static final int VER_20_2_0_8 = 0x14020008;

	public static final int VER_20_3_0_1 = 0x14030001;

	public static final int VER_20_3_0_2 = 0x14030002;

	public static final int VER_20_3_0_3 = 0x14030003;

	public static final int VER_20_3_0_6 = 0x14030006;

	public static final int VER_20_3_0_9 = 0x14030009;// special, black prohecy disagrees with nif.xml (userversion == 12||userversion == 9)

	public static final int VER_20_6_0_0 = 0x14060000;

	public static final int VER_UNSUPPORTED = 0xFFFFFFFF; /*!< Unsupported Nif Version */

	public static final int VER_INVALID = 0xFFFFFFFE; /*!< Not a Nif file */

	// instanced strings list to be handed around
	public String[] indexStrings;

	public int LOAD_VER = VER_INVALID;

	public int LOAD_USER_VER = 0;

	public int BS_Version = 0;

	public String fileName;

	public NifVer(String fileName, int LOAD_VER, int LOAD_USER_VER, int LOAD_USER_VER2)
	{
		this.fileName = fileName;
		this.LOAD_VER = LOAD_VER;
		this.LOAD_USER_VER = LOAD_USER_VER;
		this.BS_Version = LOAD_USER_VER2;
	}

	public String toString()
	{
		return "nifVer " + fileName + " " + LOAD_VER + " " + LOAD_USER_VER + " " + BS_Version;
	}

	/** 
	 * crazy undocumented madness case
	 * @return
	 */
	public boolean isBP()
	{
		return (LOAD_VER == NifVer.VER_20_3_0_9 && (LOAD_USER_VER == 9 || LOAD_USER_VER == 11 || LOAD_USER_VER == 12));
	}
	
	/**
	 * An insane system to easily translate nifxml conditions into my code
	 * 
	 * 
	 */
	
	//<verexpr token="#NI_BS_LTE_16#" string="(#BSVER# #LTE# 16)">All NI + BS until BSVER 16.</verexpr>
	public boolean NI_BS_LTE_16() {
		return BS_Version < 16;
	}
    //<verexpr token="#NI_BS_LT_FO3#" string="(#BSVER# #LT# 34)">All NI + BS before Fallout 3.</verexpr>
	public boolean NI_BS_LT_FO3() {
		return BS_Version < 34;
	}
    //<verexpr token="#NI_BS_LTE_FO3#" string="(#BSVER# #LTE# 34)">All NI + BS until Fallout 3.</verexpr>
	public boolean NI_BS_LTE_FO3() {
		return BS_Version <= 34;
	}
    //<verexpr token="#NI_BS_LT_SSE#" string="(#BSVER# #LT# 100)">All NI + BS before SSE.</verexpr>
	public boolean NI_BS_LT_SSE() {
		return BS_Version < 100;
	}
    //<verexpr token="#NI_BS_LT_FO4#" string="(#BSVER# #LT# 130)">All NI + BS before Fallout 4.</verexpr>
	public boolean NI_BS_LT_FO4() {
		return BS_Version < 130;
	}
    //<verexpr token="#NI_BS_LTE_FO4#" string="(#BSVER# #LTE# 139)">All NI + BS until Fallout 4.</verexpr>
	public boolean NI_BS_LTE_FO4() {
		return BS_Version <= 139;
	}
	//<verexpr token="#BS_GT_FO3#" string="(#BSVER# #GT# 34)">Skyrim, SSE, and Fallout 4</verexpr>
	public boolean BS_GT_FO3() {
		return BS_Version > 34;
	}
	//<verexpr token="#BS_GTE_FO3#" string="(#BSVER# #GTE# 34)">FO3 and later.</verexpr>
	public boolean BS_GTE_FO3() {
		return BS_Version >= 34;
	}
	//<verexpr token="#BS_GTE_SKY#" string="(#BSVER# #GTE# 83)">Skyrim and later.</verexpr>    
	public boolean BS_GTE_SKY() {
		return BS_Version >= 83;
	}
	//<verexpr token="#BS_GTE_SSE#" string="(#BSVER# #GTE# 100)">SSE and later.</verexpr>
	public boolean BS_GTE_SSE() {
		return BS_Version >= 100;
	}
	//<verexpr token="#BS_GTE_F76#" string="(#BSVER# #GTE# 155)">Fallout 76 and later.</verexpr>
	public boolean BS_GTE_F76() {
		return BS_Version >= 155;
	}
	//<verexpr token="#BS_GTE_STF#" string="(#BSVER# #GTE# 172)">Starfield and later.</verexpr>
	public boolean BS_GTE_STF() {
		return BS_Version >= 172;
	}
	//<verexpr token="#BS_SSE#" string="(#BSVER# #EQ# 100)">SSE only.</verexpr>
	public boolean BS_SSE() {
		return BS_Version == 100;
	}
	//<verexpr token="#BS_FO4#" string="(#BSVER# #EQ# 130)">Fallout 4 strictly, excluding stream 132 and 139 in dev files.</verexpr>
	public boolean BS_FO4() {
		return BS_Version == 130;
	}
	//<verexpr token="#BS_FO4_2#" string="((#BSVER# #GTE# 130) #AND# (#BSVER# #LTE# 139))">Fallout 4/76 including dev files.</verexpr>
	public boolean BS_FO4_2() {
		return BS_Version >= 130 && BS_Version <= 139;
	}
	//<verexpr token="#BS_GT_130#" string="(#BSVER# #GT# 130)">Later than Bethesda 130.</verexpr>
	public boolean BS_GT_130() {
		return BS_Version > 130;
	}
	//<verexpr token="#BS_GTE_130#" string="(#BSVER# #GTE# 130)">Bethesda 130 and later.</verexpr>
	public boolean BS_GTE_130() {
		return BS_Version >= 130;
	}
	//<verexpr token="#BS_GTE_132#" string="(#BSVER# #GTE# 132)">Bethesda 132 and later.</verexpr>
	public boolean BS_GTE_132() {
		return BS_Version >= 132;
	}
	//<verexpr token="#BS_GTE_152#" string="(#BSVER# #GTE# 152)">Bethesda 152 and later.</verexpr>
	public boolean BS_GTE_152() {
		return BS_Version >= 152;
	}
	//<verexpr token="#BS_F76#" string="(#BSVER# #EQ# 155)">Fallout 76 stream 155 only.</verexpr>
	public boolean BS_F76() {
		return BS_Version == 155;
	}
	//<verexpr token="#BS_SSE_FO4_FO76#" string="((#BSVER# #GTE# 100) #AND# (#BSVER# #LT# 172))">SSE, FO4, FO76</verexpr>
	public boolean BS_SSE_FO4_FO76() {
		return BS_Version >= 100 && BS_Version < 172;
	}
	//<verexpr token="#BS202#" string="((#VER# #EQ# 20.2.0.7) #AND# (#BSVER# #GT# 0))">Bethesda 20.2 only.</verexpr>
	public boolean BS202(NifVer nifVer) {
		return LOAD_VER == VER_20_2_0_7 && BS_Version > 0;
	}

	//TODO: nifVer is handed to all node to loading, so it is really more like teh niffile root, 
	//and should have the header adn everything availible from it, it should be the header really

	public ArrayList<Integer> niGeometryDataToLoadMorphably = new ArrayList<Integer>();
	public ArrayList<Integer> niGeometryDataExtraDataArriving = new ArrayList<Integer>();

}
