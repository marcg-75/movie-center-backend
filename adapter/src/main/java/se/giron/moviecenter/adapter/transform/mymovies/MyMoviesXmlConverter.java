package se.giron.moviecenter.adapter.transform.mymovies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import se.giron.moviecenter.mymovies.TitlesType;

import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

public class MyMoviesXmlConverter {
	
	private final Unmarshaller unmarshaller;
	private static final Logger LOG = LoggerFactory.getLogger(MyMoviesXmlConverter.class);

	public MyMoviesXmlConverter(Jaxb2Marshaller jaxb2UnMarshaller) {
		this.unmarshaller = jaxb2UnMarshaller;
	}
 
	public TitlesType convertFromXMLToObject(Path xmlfile) throws IOException {
		try (FileInputStream is = new FileInputStream(xmlfile.toFile())) {
			final Object unmarshalled = unmarshaller.unmarshal(new StreamSource(is));
			if (unmarshalled instanceof TitlesType) {
				return (TitlesType) unmarshalled;
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
