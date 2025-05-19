package fr.univrouen.rss25SB.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "person")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "person_type", discriminatorType = DiscriminatorType.STRING)
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    private Long id;

    @XmlElement(namespace = "http://www.w3.org/2005/Atom")
    @Column(length = 64, nullable = false)
    private String name;

    @XmlElement(namespace = "http://www.w3.org/2005/Atom")
    @Column
    private String email;

    @XmlElement(namespace = "http://www.w3.org/2005/Atom")
    @Column
    private String uri;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    @XmlTransient
    private Item item;

    // Constructeurs
    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    public Person(String name, String email, String uri) {
        this.name = name;
        this.email = email;
        this.uri = uri;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
