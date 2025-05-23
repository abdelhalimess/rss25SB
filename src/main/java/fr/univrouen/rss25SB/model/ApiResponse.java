package fr.univrouen.rss25SB.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class ApiResponse {

    @XmlElement
    private Long id;

    @XmlElement
    private String status;

    @XmlElement
    private String description;

    public ApiResponse() {
    }

    public ApiResponse(Long id, String status) {
        this.id = id;
        this.status = status;
    }

    public ApiResponse(String status) {
        this.status = status;
    }

    public ApiResponse(String status, String description) {
        this.status = status;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}