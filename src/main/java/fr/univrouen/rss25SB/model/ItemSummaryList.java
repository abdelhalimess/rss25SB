package fr.univrouen.rss25SB.model;

import jakarta.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "articles")
@XmlAccessorType(XmlAccessType.FIELD)
public class ItemSummaryList {

    @XmlElement(name = "article")
    private List<ItemSummaryDTO> articles;

    public ItemSummaryList() {}

    public ItemSummaryList(List<ItemSummaryDTO> articles) {
        this.articles = articles;
    }

    public List<ItemSummaryDTO> getArticles() {
        return articles;
    }

    public void setArticles(List<ItemSummaryDTO> articles) {
        this.articles = articles;
    }
}

