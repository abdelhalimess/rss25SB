package fr.univrouen.rss25SB.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.*;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    private Long id;

    @XmlElement
    @Column(unique = true, nullable = false)
    private String guid;

    @XmlElement
    @Column(length = 128, nullable = false)
    private String title;

    @XmlElement(name = "category")
    @ElementCollection
    @CollectionTable(name = "item_category", joinColumns = @JoinColumn(name = "item_id"))
    @Column(name = "term")
    private List<String> categories = new ArrayList<>();

    @XmlElement(name = "published") // ou "updated"
    @Column(nullable = false)
    private LocalDateTime publicationDate;

    @XmlElement
    @Embedded
    private Image image;

    @XmlElement
    @Embedded
    private Content content;

    @XmlElement(name = "author")
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Author> authors = new ArrayList<>();

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

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }
}
