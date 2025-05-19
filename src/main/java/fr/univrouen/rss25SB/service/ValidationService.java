package fr.univrouen.rss25SB.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.StringReader;

@Service
public class ValidationService {
    
    @Value("classpath:xsd/rss25SB.xsd")
    private Resource xsdSchema;
    
    /**
     * Valide un contenu XML selon le schéma XSD rss25SB
     * 
     * @param xmlContent Le contenu XML à valider
     * @return true si le XML est valide selon le schéma, false sinon
     */
    public boolean validateXmlAgainstXsd(String xmlContent) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(xsdSchema.getFile());
            Validator validator = schema.newValidator();
            
            // Désactiver les connexions externes pour prévenir les attaques XXE
            validator.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            validator.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
            
            validator.validate(new StreamSource(new StringReader(xmlContent)));
            return true;
        } catch (SAXException | IOException e) {
            // Le XML n'est pas valide ou une erreur est survenue lors de la validation
            return false;
        }
    }
}