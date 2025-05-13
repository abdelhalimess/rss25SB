package fr.univrouen.rss25SB.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;

@Embeddable
public class Content {

    @Lob
    @Column(nullable = false)
    @XmlValue
    private String value;

    @XmlAttribute
    @Column(insertable = false, updatable = false) // Preventing database mapping for 'type' to avoid conflicts
    private String type;

    @XmlAttribute
    private String src;

    // Getters and setters
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}

