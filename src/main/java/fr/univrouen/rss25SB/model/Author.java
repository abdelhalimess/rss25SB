package fr.univrouen.rss25SB.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Author {

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
}

