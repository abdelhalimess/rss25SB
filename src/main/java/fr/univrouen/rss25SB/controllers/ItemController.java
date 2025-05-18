package fr.univrouen.rss25SB.controllers;

import org.springframework.web.bind.annotation.*;

import fr.univrouen.rss25SB.model.ItemSummaryList;
import fr.univrouen.rss25SB.service.ItemService;
import fr.univrouen.rss25SB.model.ErrorResponse;
import fr.univrouen.rss25SB.model.Item;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.util.Optional;


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
    
    @GetMapping(value = "/resume/xml/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> getItemByIdAsXml(@PathVariable Long id) {
        Optional<Item> item = itemService.getItemById(id);
        
        if (item.isPresent()) {
            return ResponseEntity.ok(item.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(id, "ERROR"));
        }
    }

}

