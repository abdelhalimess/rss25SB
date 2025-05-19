package fr.univrouen.rss25SB.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import fr.univrouen.rss25SB.model.ItemSummaryList;
import fr.univrouen.rss25SB.service.ItemService;

import org.springframework.http.MediaType;
import fr.univrouen.rss25SB.model.Feed;
import fr.univrouen.rss25SB.model.Item;
import fr.univrouen.rss25SB.repository.FeedRepository;
import fr.univrouen.rss25SB.service.ValidationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.StringReader;
import java.util.Optional;

@Controller
@RequestMapping("/rss25SB")
public class ItemController {
    
    @Autowired
    private FeedRepository feedRepository;
    
    @Autowired
    private ValidationService validationService;

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping(value = "/resume/xml", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public ItemSummaryList getItemSummariesAsXml() {
        return new ItemSummaryList(itemService.getAllItemSummaries());
    }
    
    // Afficher le formulaire d'insertion
    @GetMapping("/insert")
    public ModelAndView showInsertForm() {
        ModelAndView modelAndView = new ModelAndView("insertForm");
        return modelAndView;
    }
    
    // Traiter la soumission du formulaire
    @PostMapping(value = "/insert", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public ResponseEntity<String> insertFeed(@RequestBody String xmlContent) {
        try {
            // Validation du XML contre le schéma XSD
        	System.out.println("here");
            if (!validationService.validateXmlAgainstXsd(xmlContent)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("<?xml version=\"1.0\" encoding=\"UTF-8\"?><response><status>ERROR</status><message>Le flux XML ne respecte pas le schéma XSD.</message></response>");
            }

            // Unmarshalling du XML vers l'objet Feed
            JAXBContext jaxbContext = JAXBContext.newInstance(Feed.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            StringReader reader = new StringReader(xmlContent);
            Feed feed = (Feed) unmarshaller.unmarshal(reader);

            // Vérifier si le flux existe déjà (même titre et date)
            Optional<Feed> existingFeed = feedRepository.findByTitleAndPubDate(feed.getTitle(), feed.getPubDate());
            if (existingFeed.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("<?xml version=\"1.0\" encoding=\"UTF-8\"?><response><status>ERROR</status><message>Un flux avec le même titre et la même date existe déjà.</message></response>");
            }

            // Sauvegarde du flux dans la base de données
            Feed savedFeed = feedRepository.save(feed);
            
            // Construction de la réponse XML
            Long lastItemId = null;
            if (!savedFeed.getItems().isEmpty()) {
                Item lastItem = savedFeed.getItems().get(savedFeed.getItems().size() - 1);
                lastItemId = lastItem.getId();
            }
            
            String responseXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                    + "<response>"
                    + "<id>" + (lastItemId != null ? lastItemId : savedFeed.getId()) + "</id>"
                    + "<status>INSERTED</status>"
                    + "</response>";

            return ResponseEntity.status(HttpStatus.CREATED).body(responseXml);
            
        } catch (JAXBException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("<?xml version=\"1.0\" encoding=\"UTF-8\"?><response><status>ERROR</status><message>Erreur lors du traitement du XML: " + e.getMessage() + "</message></response>");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("<?xml version=\"1.0\" encoding=\"UTF-8\"?><response><status>ERROR</status><message>Erreur serveur: " + e.getMessage() + "</message></response>");
        }
    }
    
    // Ajoutez ici les autres méthodes de contrôleur mentionnées dans le sujet
    // comme getItemDetailAsXml, getItemDetailAsHtml, deleteItem, etc.
}