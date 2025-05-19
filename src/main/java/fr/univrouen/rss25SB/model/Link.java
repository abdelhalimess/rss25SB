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
    @Column(nullable = false)
    private String rel;

    @XmlAttribute
    @Column(nullable = false)
    private String type;

    @XmlAttribute
    @Column(nullable = false)
    private String href;

    @ManyToOne
    @JoinColumn(name = "feed_id", nullable = false)
    @XmlTransient
    private Feed feed;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

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

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }
}