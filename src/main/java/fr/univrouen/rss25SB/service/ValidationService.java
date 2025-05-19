package fr.univrouen.rss25SB.service;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ValidationService {

    private static final Logger LOGGER = Logger.getLogger(ValidationService.class.getName());
    private static final String XSD_PATH = "/xsd/rss25SB.xsd"; // Chemin vers le fichier XSD dans les ressources

    public boolean validateXmlAgainstXsd(String xmlContent) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            factory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            factory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
            
            // Chargement du schéma XSD
            Schema schema = factory.newSchema(getClass().getResource(XSD_PATH));
            Validator validator = schema.newValidator();
            
            // Validation du XML
            Source source = new StreamSource(new StringReader(xmlContent));
            validator.validate(source);
            
            LOGGER.info("XML validé avec succès contre le schéma XSD");
            return true;
            
        } catch (SAXException e) {
            LOGGER.log(Level.WARNING, "Erreur de validation XML: {0}", e.getMessage());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erreur IO lors de la validation XML: {0}", e.getMessage());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de la validation XML: {0}", e.getMessage());
        }
        
        return false;
    }
}