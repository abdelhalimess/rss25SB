package fr.univrouen.rss25SB.controllers;

import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import fr.univrouen.rss25SB.model.Item;
import fr.univrouen.rss25SB.service.ItemService;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

@Controller
@RequestMapping("/rss25SB")
public class ItemHtmlController {

    private static final Logger LOGGER = Logger.getLogger(ItemHtmlController.class.getName());

    private final ItemService itemService;

    public ItemHtmlController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/resume/html")
    public String showItemSummariesHtml(Model model) {
        LOGGER.info("Début de l'affichage des résumés d'items en HTML");
        
        try {
            // Récupération des données
            LOGGER.info("Récupération de tous les résumés d'items depuis le service");
            List<?> itemSummaries = itemService.getAllItemSummaries();
            
            if (itemSummaries == null) {
                LOGGER.warning("Le service a retourné une liste null pour les résumés d'items");
                model.addAttribute("articles", List.of()); // Liste vide pour éviter les erreurs dans la vue
                model.addAttribute("errorMessage", "Aucune donnée disponible");
            } else {
                int itemCount = itemSummaries.size();
                LOGGER.log(Level.INFO, "Récupération réussie de {0} résumés d'items", itemCount);
                model.addAttribute("articles", itemSummaries);
                
                if (itemCount == 0) {
                    LOGGER.info("Aucun résumé d'item trouvé - liste vide");
                    model.addAttribute("infoMessage", "Aucun article disponible pour le moment");
                }
            }
            
            LOGGER.info("Préparation du modèle terminée, redirection vers la vue 'summary'");
            return "summary";
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de la récupération des résumés d'items: {0}", e.getMessage());
            LOGGER.log(Level.SEVERE, "Stack trace: ", e);
            
            // En cas d'erreur, on prépare une page d'erreur
            model.addAttribute("errorMessage", "Une erreur est survenue lors du chargement des articles");
            model.addAttribute("technicalError", e.getMessage());
            return "errorPage";
        }
    }
    
    @GetMapping("/resume/html/{id}")
    public String getItemByIdAsHtml(@PathVariable Long id, Model model) {
        LOGGER.log(Level.INFO, "Début de la récupération de l'item avec ID: {0} pour affichage HTML", id);
        
        try {
            // Validation de l'ID
            if (id == null) {
                LOGGER.warning("ID fourni est null pour la récupération HTML");
                model.addAttribute("errorMessage", "Identifiant invalide");
                return "errorPage";
            }
            
            if (id <= 0) {
                LOGGER.log(Level.WARNING, "ID invalide fourni: {0} (doit être positif)", id);
                model.addAttribute("errorMessage", "Identifiant invalide: " + id);
                return "errorPage";
            }
            
            LOGGER.log(Level.INFO, "Recherche de l'item avec ID: {0}", id);
            Optional<Item> item = itemService.getItemById(id);
            
            if (item.isPresent()) {
                Item foundItem = item.get();
                LOGGER.log(Level.INFO, "Item trouvé avec succès - ID: {0}, Titre: {1}", 
                          new Object[]{id, foundItem.getTitle()});
                
                // Ajout de l'item au modèle
                model.addAttribute("item", foundItem);
                
                // Informations supplémentaires pour le debugging
                LOGGER.log(Level.FINE, "Détails de l'item - Auteur: {0}, Date: {1}", 
                          new Object[]{
                              foundItem.getAuthors() != null && !foundItem.getAuthors().isEmpty() 
                                  ? foundItem.getAuthors().get(0).getName() : "Non spécifié",
                              foundItem.getPublished()
                          });
                
                LOGGER.info("Préparation du modèle terminée, redirection vers la vue 'itemDetails'");
                return "itemDetails";
                
            } else {
                LOGGER.log(Level.WARNING, "Aucun item trouvé pour l'ID: {0}", id);
                model.addAttribute("errorMessage", "Article avec l'identifiant " + id + " non trouvé");
                model.addAttribute("requestedId", id);
                return "errorPage";
            }
            
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Format d'ID invalide: {0} - {1}", new Object[]{id, e.getMessage()});
            model.addAttribute("errorMessage", "Format d'identifiant invalide: " + id);
            model.addAttribute("technicalError", "L'identifiant doit être un nombre");
            return "errorPage";
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de la récupération de l'item avec ID {0}: {1}", 
                      new Object[]{id, e.getMessage()});
            LOGGER.log(Level.SEVERE, "Stack trace: ", e);
            
            model.addAttribute("errorMessage", "Une erreur est survenue lors du chargement de l'article");
            model.addAttribute("requestedId", id);
            model.addAttribute("technicalError", e.getMessage());
            return "errorPage";
        }
    }
}