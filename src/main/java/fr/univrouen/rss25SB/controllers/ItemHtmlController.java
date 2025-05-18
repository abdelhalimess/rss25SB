package fr.univrouen.rss25SB.controllers;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.ui.Model;

import fr.univrouen.rss25SB.model.Item;
import fr.univrouen.rss25SB.service.ItemService;

@Controller
@RequestMapping("/rss25SB")
public class ItemHtmlController {
    private final ItemService itemService;

    public ItemHtmlController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/resume/html")
    public String showItemSummariesHtml(Model model) {
        model.addAttribute("articles", itemService.getAllItemSummaries());
        return "summary"; // va chercher summary.html dans /templates/
    }
    
    @GetMapping("/resume/html/{id}")
    public String getItemByIdAsHtml(@PathVariable Long id, Model model) {
        Optional<Item> item = itemService.getItemById(id);

        if (item.isPresent()) {
            model.addAttribute("item", item.get());
            return "itemDetails";  // nom du template Thymeleaf pour afficher l’item
        } else {
            model.addAttribute("errorMessage", "Item with id " + id + " not found.");
            return "errorPage";  // nom du template Thymeleaf pour afficher l’erreur
        }
    }

}