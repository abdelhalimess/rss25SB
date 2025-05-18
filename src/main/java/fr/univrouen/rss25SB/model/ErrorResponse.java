package fr.univrouen.rss25SB.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "error")
public class ErrorResponse {

    private Long id;
    private String status;

    public ErrorResponse() {}

    public ErrorResponse(Long id, String status) {
        this.id = id;
        this.status = status;
    }

    @XmlElement
    public Long getId() {
        return id;
    }

    @XmlElement
    public String getStatus() {
        return status;
    }
}
