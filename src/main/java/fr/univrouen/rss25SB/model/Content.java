package fr.univrouen.rss25SB.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@Embeddable
public class Content {

    @Column(name = "content_value", nullable = false, columnDefinition = "text")   
    @XmlValue
    private String value;

    @Column(name = "content_type")
    @XmlAttribute
    private String type;

    @Column(name = "content_src")
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

