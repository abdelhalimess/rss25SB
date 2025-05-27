//
// Ce fichier a été généré par Eclipse Implementation of JAXB, v3.0.0 
// Voir https://eclipse-ee4j.github.io/jaxb-ri 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2025.05.27 à 02:50:33 AM CEST 
//


package fr.univrouen.rss25SB.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour linkType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="linkType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="rel" use="required" type="{http://www.w3.org/2005/Atom}relType" /&gt;
 *       &lt;attribute name="type" use="required" type="{http://www.w3.org/2005/Atom}mimeType" /&gt;
 *       &lt;attribute name="href" use="required" type="{http://www.w3.org/2005/Atom}urlType" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "linkType")
public class LinkType {

    @XmlAttribute(name = "rel", required = true)
    protected RelType rel;
    @XmlAttribute(name = "type", required = true)
    protected String type;
    @XmlAttribute(name = "href", required = true)
    protected String href;

    /**
     * Obtient la valeur de la propriété rel.
     * 
     * @return
     *     possible object is
     *     {@link RelType }
     *     
     */
    public RelType getRel() {
        return rel;
    }

    /**
     * Définit la valeur de la propriété rel.
     * 
     * @param value
     *     allowed object is
     *     {@link RelType }
     *     
     */
    public void setRel(RelType value) {
        this.rel = value;
    }

    /**
     * Obtient la valeur de la propriété type.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Définit la valeur de la propriété type.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Obtient la valeur de la propriété href.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHref() {
        return href;
    }

    /**
     * Définit la valeur de la propriété href.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHref(String value) {
        this.href = value;
    }

}
