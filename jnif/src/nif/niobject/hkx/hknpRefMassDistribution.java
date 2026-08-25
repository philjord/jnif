package nif.niobject.hkx;

import nif.niobject.hkx.reader.TAG0Reader.Havok_TagItem;
import nif.niobject.hkx.reader.TAG0Reader.Havok_TagObject;

 
public class hknpRefMassDistribution extends hkReferencedObject {

public hknpMassDistribution massDistribution;
	/**	
	Outline for Havok_TagType hknpRefMassDistribution
	Havok_TagMember massDistribution of type hknpMassDistribution
	 */
	@Override
	public int readFromTAG0(Havok_TagItem item) {
		int memberIdx = super.readFromTAG0(item);
		//item.outputOutline();
		Havok_TagObject value0 = item.value.get(0);
		//FIXME: vlaue0 seems to just be a hknpRefMassDistribution, so not sre about it all
		//massDistribution = new hknpMassDistribution(value0);
		return memberIdx;
	}
}