package fr.univrouen.rss25SB.model;

<<<<<<< HEAD
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;

=======
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;
@XmlAccessorType(XmlAccessType.FIELD)
>>>>>>> 38b053df97a6fdc8dc4b5bb569670d4915b4c034
@Embeddable
@XmlAccessorType(XmlAccessType.FIELD)
public class Image {

    @XmlAttribute
    @Column(nullable = true)
    private String type;

    @XmlAttribute
    @Column(nullable = true)
    private String href;

    @XmlAttribute
    @Column(nullable = true)
    private String alt;

    @XmlAttribute
    private Integer length;

<<<<<<< HEAD
    
    public Image() {}
    // Getters and setters
=======
    // Getters and Setters
>>>>>>> 38b053df97a6fdc8dc4b5bb569670d4915b4c034
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