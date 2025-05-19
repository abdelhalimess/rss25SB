package fr.univrouen.rss25SB.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.time.LocalDateTime;
import java.util.*;

@XmlRootElement(name = "item", namespace = "http://www.w3.org/2005/Atom")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    private Long id;

    @XmlElement(name = "guid", namespace = "http://www.w3.org/2005/Atom")
    @Column(unique = true, nullable = false)
    private String guid;

    @XmlElement(name = "title", namespace = "http://www.w3.org/2005/Atom")
    @Column(length = 128, nullable = false)
    private String title;

    // Correction ici: suppression de l'élément wrapper categories
    @XmlElement(name = "category", namespace = "http://www.w3.org/2005/Atom")
    @ElementCollection
    @CollectionTable(name = "item_category", joinColumns = @JoinColumn(name = "item_id"))
    @Column(name = "term")
    private List<Category> categories = new ArrayList<>();

    @XmlElement(name = "published", namespace = "http://www.w3.org/2005/Atom")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    @Column(name = "published_date", nullable = true)
    private LocalDateTime published;
    
    @XmlElement(name = "updated", namespace = "http://www.w3.org/2005/Atom")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    @Column(name = "updated_date")
    private LocalDateTime updated;

    @XmlElement(name = "image", namespace = "http://www.w3.org/2005/Atom")
    @Embedded
    private Image image;

    @XmlElement(name = "content", namespace = "http://www.w3.org/2005/Atom")
    @Embedded
    private Content content;

    // Correction ici: suppression de l'élément wrapper authors
    @XmlElement(name = "author", namespace = "http://www.w3.org/2005/Atom")
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Author> authors = new ArrayList<>();
    
    // Correction ici: suppression de l'élément wrapper contributors
    @XmlElement(name = "contributor", namespace = "http://www.w3.org/2005/Atom")
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contributor> contributors = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "feed_id", nullable = false)
    @XmlTransient
    private Feed feed;

    public Item() {}
    
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

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public LocalDateTime getPublished() {
        return published;
    }

    public void setPublished(LocalDateTime published) {
        this.published = published;
        // Si updated est null ou si published est après updated, on met à jour updated aussi
        if (updated == null || (published != null && published.isAfter(updated))) {
            this.updated = published;
        }
    }
    
    public LocalDateTime getUpdated() {
        return updated;
    }
    
    

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
        // Si published est null et qu'on a une valeur pour updated, on utilise updated comme published
        if (published == null && updated != null) {
            this.published = updated;
        }
    }
    
    public void synchronizeDates() {
        if (published == null && updated != null) {
            published = updated;
        }
        if (updated == null && published != null) {
            updated = published;
        }
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
    
    public List<Contributor> getContributors() {
        return contributors;
    }

    public void setContributors(List<Contributor> contributors) {
        this.contributors = contributors;
    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }
    // for update/published
    @PrePersist
    @PreUpdate
    private void prepareDatesBeforeSaving() {
        synchronizeDates();
    }

}