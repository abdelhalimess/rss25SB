package fr.univrouen.rss25SB.model;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.time.LocalDateTime;

@XmlAccessorType(XmlAccessType.FIELD)
public class ItemSummaryDTO {

    @XmlElement
    private Long id;

    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime publicationDate;

    @XmlElement
    private String guid;

    public ItemSummaryDTO() {}

    public ItemSummaryDTO(Long id, LocalDateTime publicationDate, String guid) {
        this.id = id;
        this.publicationDate = publicationDate;
        this.guid = guid;
    }

    public Long getId() {
        return id;
    }
    
    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public String getGuid() {
        return guid;
    }
}

