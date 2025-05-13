package fr.univrouen.rss25SB.model;

import jakarta.xml.bind.annotation.*;
import java.time.LocalDateTime;

@XmlAccessorType(XmlAccessType.FIELD)
public class ItemSummaryDTO {

    @XmlElement
    private Long id;

    @XmlElement(name = "date")
    private LocalDateTime publicationDate;

    @XmlElement
    private String guid;

    public ItemSummaryDTO() {}

    public ItemSummaryDTO(Long id, LocalDateTime publicationDate, String guid) {
        this.id = id;
        this.publicationDate = publicationDate;
        this.guid = guid;
    }

    // Getters et setters...
}

