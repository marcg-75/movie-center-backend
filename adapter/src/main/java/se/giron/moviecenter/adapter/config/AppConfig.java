package se.giron.moviecenter.adapter.config;
 

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.client.RestTemplate;
import se.giron.moviecenter.adapter.transform.dvdprofiler.XmlConverter;

import java.nio.charset.Charset;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class AppConfig {
	
    @Value("${adapter.schemaResource}")
    private String[] schemaResources;
    
	@javax.annotation.Resource
	ConfigurableApplicationContext appContext;

	@Value("${adapter.packagesToScan}")
	String[] packages;

	@Bean 
	public XmlConverter xmlConverter() {
		XmlConverter xmlConverter = new XmlConverter(jaxb2UnMarshaller());
		return xmlConverter;
	}
			
	@Bean
	public Jaxb2Marshaller jaxb2UnMarshaller(){
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setPackagesToScan(packages);		
        marshaller.setSchemas(getResources());
		return marshaller;
	}

	@Bean
    public RestTemplate restTemplate() {
		
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
	    return restTemplate;
	}

	@Bean("threadPoolTaskExecutor")
    public ThreadPoolExecutor asyncExecutor() {
        ThreadPoolExecutor executor = (ThreadPoolExecutor)Executors.newFixedThreadPool(1);
        return executor;
    }

	/**
	 * Converts array of strings to ClassPathResources.
	 * @return
	 */
	private Resource [] getResources() {

		Resource[] r = new Resource[schemaResources.length];
		int i=0;
		for(String s : schemaResources) {
			// Assigns a ClassPathResource to r[i].
			r[i++] = appContext.getResource("classpath:" +s);
		}
		return r;
	}

}