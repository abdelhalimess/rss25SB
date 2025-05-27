//
// Ce fichier a été généré par Eclipse Implementation of JAXB, v3.0.0 
// Voir https://eclipse-ee4j.github.io/jaxb-ri 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2025.05.27 à 02:50:33 AM CEST 
//


package fr.univrouen.rss25SB.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlElementRefs;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour itemType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="itemType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="guid" type="{http://www.w3.org/2005/Atom}urlType"/&gt;
 *         &lt;element name="title" type="{http://www.w3.org/2005/Atom}textType"/&gt;
 *         &lt;element name="category" type="{http://www.w3.org/2005/Atom}categoryType" maxOccurs="unbounded"/&gt;
 *         &lt;choice&gt;
 *           &lt;element name="published" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *           &lt;element name="updated" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;/choice&gt;
 *         &lt;element name="image" type="{http://www.w3.org/2005/Atom}imageType" minOccurs="0"/&gt;
 *         &lt;element name="content" type="{http://www.w3.org/2005/Atom}contentType"/&gt;
 *         &lt;choice maxOccurs="unbounded"&gt;
 *           &lt;element name="author" type="{http://www.w3.org/2005/Atom}personType"/&gt;
 *           &lt;element name="contributor" type="{http://www.w3.org/2005/Atom}personType"/&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "itemType", propOrder = {
    "guid",
    "title",
    "category",
    "published",
    "updated",
    "image",
    "content",
    "authorOrContributor"
})
public class ItemType {

    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
    protected String guid;
    @XmlElement(required = true)
    protected String title;
    @XmlElement(required = true)
    protected List<CategoryType> category;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar published;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar updated;
    protected ImageType image;
    @XmlElement(required = true)
    protected ContentType content;
    @XmlElementRefs({
        @XmlElementRef(name = "author", namespace = "http://www.w3.org/2005/Atom", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "contributor", namespace = "http://www.w3.org/2005/Atom", type = JAXBElement.class, required = false)
    })
    protected List<JAXBElement<PersonType>> authorOrContributor;

    /**
     * Obtient la valeur de la propriété guid.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGuid() {
        return guid;
    }

    /**
     * Définit la valeur de la propriété guid.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGuid(String value) {
        this.guid = value;
    }

    /**
     * Obtient la valeur de la propriété title.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Définit la valeur de la propriété title.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the category property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the category property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCategory().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CategoryType }
     * 
     * 
     */
    public List<CategoryType> getCategory() {
        if (category == null) {
            category = new ArrayList<CategoryType>();
        }
        return this.category;
    }

    /**
     * Obtient la valeur de la propriété published.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPublished() {
        return published;
    }

    /**
     * Définit la valeur de la propriété published.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPublished(XMLGregorianCalendar value) {
        this.published = value;
    }

    /**
     * Obtient la valeur de la propriété updated.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getUpdated() {
        return updated;
    }

    /**
     * Définit la valeur de la propriété updated.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setUpdated(XMLGregorianCalendar value) {
        this.updated = value;
    }

    /**
     * Obtient la valeur de la propriété image.
     * 
     * @return
     *     possible object is
     *     {@link ImageType }
     *     
     */
    public ImageType getImage() {
        return image;
    }

    /**
     * Définit la valeur de la propriété image.
     * 
     * @param value
     *     allowed object is
     *     {@link ImageType }
     *     
     */
    public void setImage(ImageType value) {
        this.image = value;
    }

    /**
     * Obtient la valeur de la propriété content.
     * 
     * @return
     *     possible object is
     *     {@link ContentType }
     *     
     */
    public ContentType getContent() {
        return content;
    }

    /**
     * Définit la valeur de la propriété content.
     * 
     * @param value
     *     allowed object is
     *     {@link ContentType }
     *     
     */
    public void setContent(ContentType value) {
        this.content = value;
    }

    /**
     * Gets the value of the authorOrContributor property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the authorOrContributor property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAuthorOrContributor().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link PersonType }{@code >}
     * {@link JAXBElement }{@code <}{@link PersonType }{@code >}
     * 
     * 
     */
    public List<JAXBElement<PersonType>> getAuthorOrContributor() {
        if (authorOrContributor == null) {
            authorOrContributor = new ArrayList<JAXBElement<PersonType>>();
        }
        return this.authorOrContributor;
    }

}
