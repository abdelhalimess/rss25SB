package fr.univrouen.rss25SB.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Contributor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    private Long id;

    @XmlElement
    @Column(length = 64, nullable = false)
    private String name;

    @XmlElement
    private String email;

    @XmlElement
    private String uri;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    @XmlTransient
    private Item item;
    
    public Contributor() {}
    
    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUri() {
        return uri;
    }

    public Item getItem() {
        return item;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}