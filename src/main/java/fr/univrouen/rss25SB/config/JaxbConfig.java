package fr.univrouen.rss25SB.config;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import fr.univrouen.rss25SB.model.*;

@Configuration
public class JaxbConfig {

    @Bean
    public JAXBContext jaxbContext() throws JAXBException {
        // Inclure toutes les classes du modèle qui doivent être marshallées/unmarshallées
        return JAXBContext.newInstance(
            Feed.class, 
            Item.class, 
            Link.class, 
            Author.class, 
            Contributor.class, 
            Content.class, 
            Image.class
        );
    }

    @Bean
    public Marshaller jaxbMarshaller(JAXBContext jaxbContext) throws JAXBException {
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        // Ne pas définir JAXB_SCHEMA_LOCATION comme une URI, mais comme une paire namespace-schemaLocation
        marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, 
                               "http://www.w3.org/2005/Atom http://www.w3.org/2005/Atom/schema.xsd");
        return marshaller;
    }

    @Bean
    public Unmarshaller jaxbUnmarshaller(JAXBContext jaxbContext) throws JAXBException {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return unmarshaller;
    }
}