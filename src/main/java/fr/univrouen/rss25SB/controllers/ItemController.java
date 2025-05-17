package fr.univrouen.rss25SB.controllers;

import org.springframework.web.bind.annotation.*;

import fr.univrouen.rss25SB.model.ItemSummaryList;
import fr.univrouen.rss25SB.service.ItemService;

import org.springframework.http.MediaType;


@RestController
@RequestMapping("/rss25SB")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping(value = "/resume/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public ItemSummaryList getItemSummariesAsXml() {
        return new ItemSummaryList(itemService.getAllItemSummaries());
    }
}

