package fr.univrouen.rss25SB.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;



@Embeddable
@XmlAccessorType(XmlAccessType.FIELD)
public class Content {

	@Column(nullable = false, columnDefinition = "TEXT")
	@XmlValue
	private String value;

    @Column(insertable = false, updatable = false)
    @XmlAttribute
    private String type;

    @Column(insertable = false, updatable = false)
    @XmlAttribute
    private String src;
    
    public Content() {}

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

