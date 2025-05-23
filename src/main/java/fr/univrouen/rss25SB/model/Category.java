package fr.univrouen.rss25SB.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@Embeddable
public class Category {
    
    @XmlAttribute(name = "term")
    @Column(name = "term")
    private String term;
    
    public Category() {}
    
    public Category(String term) {
        this.term = term;
    }
    
    public String getTerm() {
        return term;
    }
    
    public void setTerm(String term) {
        this.term = term;
    }
}