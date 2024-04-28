package nif.niobject.hkx.reader;

import java.nio.ByteBuffer;

/**
 * Handles connexion between a {@link ByteBuffer} and a {@link HKXReader}.
 * <p>
 * Created and managed by {@link HKXReader}.
 */
public class HKXReaderConnector {
	public HeaderData header;
	public SectionData classnamesHead;
	public SectionData dataHead;
	public ClassnamesData classnamesdata;
	public DataInterface data;
	public Data1Interface data1;
	public Data2Interface data2;
	public Data3Interface data3;

	HKXReaderConnector(final ByteBuffer file) {
		// Extract the header
		HeaderInterface headInt = new HeaderInterface();
		headInt.connect(file);
		header = headInt.extract();
		
		// are we an unsporrted fiel format?
		if(header == null)
			return;

		// Extract the section interfaces
		SectionInterface sectInt = new SectionInterface();
		sectInt.connect(file, header);
		classnamesHead = sectInt.extract(0);
		dataHead = sectInt.extract(2);

		// Extract the classnames
		ClassnamesInterface cnamesInt = new ClassnamesInterface();
		cnamesInt.connect(file, classnamesHead);
		classnamesdata = cnamesInt.extract();

		// Connect the interfaces
		data1 = new Data1Interface();
		data1.connect(file, dataHead);
		data2 = new Data2Interface();
		data2.connect(file, dataHead);
		data3 = new Data3Interface();
		data3.connect(file, dataHead);
		data = new DataInterface();
		data.connect(file, dataHead);
	}
}
