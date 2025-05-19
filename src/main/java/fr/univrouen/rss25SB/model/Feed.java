package fr.univrouen.rss25SB.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.time.LocalDateTime;
import java.util.*;

@XmlRootElement(name = "feed", namespace = "http://www.w3.org/2005/Atom")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    private Long id;

    @XmlElement(name = "title", namespace = "http://www.w3.org/2005/Atom")
    @Column(length = 128, nullable = false)
    private String title;

    @XmlElement(name = "pubDate", namespace = "http://www.w3.org/2005/Atom")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    @Column(nullable = false)
    private LocalDateTime pubDate;

    @XmlElement(name = "copyright", namespace = "http://www.w3.org/2005/Atom")
    @Column(length = 128, nullable = false)
    private String copyright;

    @XmlAttribute(name = "lang")
    @Column(nullable = false)
    private String lang;

    @XmlAttribute(name = "version")
    @Column(nullable = false)
    private String version = "25";

    // Correction ici: suppression de l'élément wrapper links
    @XmlElement(name = "link", namespace = "http://www.w3.org/2005/Atom")
    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Link> links = new ArrayList<>();

    // Correction ici: suppression de l'élément wrapper items
    @XmlElement(name = "item", namespace = "http://www.w3.org/2005/Atom")
    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items = new ArrayList<>();

    // Constructeurs
    public Feed() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getPubDate() {
        return pubDate;
    }

    public void setPubDate(LocalDateTime pubDate) {
        this.pubDate = pubDate;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public void addLink(Link link) {
        links.add(link);
        link.setFeed(this);
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void addItem(Item item) {
        items.add(item);
        item.setFeed(this);
    }
}