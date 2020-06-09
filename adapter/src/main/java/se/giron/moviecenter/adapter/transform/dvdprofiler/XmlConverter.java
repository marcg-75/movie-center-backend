package se.giron.moviecenter.adapter.transform.dvdprofiler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import se.giron.moviecenter.dvdprofiler.DVD;

import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

public class XmlConverter {
	
	private final Unmarshaller unmarshaller;
	private static final Logger LOG = LoggerFactory.getLogger(XmlConverter.class);

	public XmlConverter(Jaxb2Marshaller jaxb2UnMarshaller) {
		this.unmarshaller = jaxb2UnMarshaller;
	}
 
	public DVD convertFromXMLToObject(Path xmlfile) throws IOException {
		try (FileInputStream is = new FileInputStream(xmlfile.toFile())) {
			final Object unmarshalled = unmarshaller.unmarshal(new StreamSource(is));
			if (unmarshalled instanceof DVD) {
				return (DVD) unmarshalled;
			}
			return null;
		} catch (Exception e) {
			LOG.error("Failed to read xml file: {}", xmlfile, e);
			throw e;
		}
	}
	
	public <T> T unmarshal(byte [] chars, Class<T> clazz) throws XmlMappingException, IOException {
		StreamSource source = new StreamSource(new ByteArrayInputStream(chars));
		return (T) unmarshaller.unmarshal(source);
	}
}
