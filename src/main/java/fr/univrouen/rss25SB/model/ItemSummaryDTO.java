package fr.univrouen.rss25SB.model;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.time.LocalDateTime;

@XmlRootElement(name = "item")
@XmlAccessorType(XmlAccessType.FIELD)
public class ItemSummaryDTO {

    @XmlElement
    private Long id;
    
    @XmlElement
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime date;

    @XmlElement
    private String guid;

    public ItemSummaryDTO() {}

    public ItemSummaryDTO(Long id, LocalDateTime date, String guid) {
        this.id = id;
        this.date = date;
        this.guid = guid;
    }

    public Long getId() {
        return id;
    }
    
    public LocalDateTime getDate() {
        return date;
    }

    public String getGuid() {
        return guid;
    }
}


