//
// Ce fichier a été généré par Eclipse Implementation of JAXB, v3.0.0 
// Voir https://eclipse-ee4j.github.io/jaxb-ri 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2025.05.27 à 02:50:33 AM CEST 
//


package fr.univrouen.rss25SB.model;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour imageTypeType.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * <pre>
 * &lt;simpleType name="imageTypeType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="GIF"/&gt;
 *     &lt;enumeration value="JPEG"/&gt;
 *     &lt;enumeration value="JPG"/&gt;
 *     &lt;enumeration value="BMP"/&gt;
 *     &lt;enumeration value="PNG"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "imageTypeType")
@XmlEnum
public enum ImageTypeType {

    GIF,
    JPEG,
    JPG,
    BMP,
    PNG;

    public String value() {
        return name();
    }

    public static ImageTypeType fromValue(String v) {
        return valueOf(v);
    }

}
