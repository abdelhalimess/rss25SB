package fr.univrouen.rss25SB.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.xml.bind.annotation.XmlAttribute;

@Embeddable
public class Image {

    @XmlAttribute
    @Column(insertable = false, updatable = false)
    private String type;

    @XmlAttribute
    private String href;

    @XmlAttribute
    private String alt;

    @XmlAttribute
    private Integer length;

    // Getters and setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }
}

