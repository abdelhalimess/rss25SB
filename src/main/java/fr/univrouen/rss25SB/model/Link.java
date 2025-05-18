package fr.univrouen.rss25SB.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    private Long id;

    @XmlAttribute
    private String rel;

    @XmlAttribute
    private String type;

    @XmlAttribute
    private String href;

    @ManyToOne
    @JoinColumn(name = "feed_id", nullable = false)
    @XmlTransient
    private Feed feed;
    
    public Link() {}
}
