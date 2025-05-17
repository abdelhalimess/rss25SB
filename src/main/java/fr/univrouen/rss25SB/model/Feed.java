package fr.univrouen.rss25SB.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.*;

@XmlRootElement(name = "feed")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    private Long id;

    @XmlElement
    @Column(length = 128, nullable = false)
    private String title;

    @XmlElement
    @Column(nullable = false)
    private LocalDateTime pubDate;

    @XmlElement
    @Column(length = 128, nullable = false)
    private String copyright;

    @XmlAttribute
    @Column(nullable = false)
    private String lang;

    @XmlAttribute
    @Column(nullable = false)
    private String version = "25";

    @XmlElement(name = "link")
    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Link> links = new ArrayList<>();

//    @XmlElement(name = "item")
//    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Item> items = new ArrayList<>();
}
