//
// Ce fichier a été généré par Eclipse Implementation of JAXB, v3.0.0 
// Voir https://eclipse-ee4j.github.io/jaxb-ri 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2025.05.27 à 02:50:33 AM CEST 
//


package fr.univrouen.rss25SB.model;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour relType.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * <pre>
 * &lt;simpleType name="relType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="self"/&gt;
 *     &lt;enumeration value="alternate"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "relType")
@XmlEnum
public enum RelType {

    @XmlEnumValue("self")
    SELF("self"),
    @XmlEnumValue("alternate")
    ALTERNATE("alternate");
    private final String value;

    RelType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RelType fromValue(String v) {
        for (RelType c: RelType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
