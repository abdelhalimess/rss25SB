package fr.univrouen.rss25SB.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.ui.Model;
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
}