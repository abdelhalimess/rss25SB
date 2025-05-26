package fr.univrouen.rss25SB.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import fr.univrouen.rss25SB.model.ItemSummaryList;
import fr.univrouen.rss25SB.service.ItemService;
import fr.univrouen.rss25SB.model.Feed;
import fr.univrouen.rss25SB.model.Item;
import fr.univrouen.rss25SB.model.ApiResponse;
import fr.univrouen.rss25SB.model.ErrorResponse;
import fr.univrouen.rss25SB.model.SuccessResponse;

import fr.univrouen.rss25SB.repository.FeedRepository;
import fr.univrouen.rss25SB.service.ValidationService;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.Marshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

import javax.xml.namespace.QName;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/rss25SB")
public class ItemController {

    private static final Logger LOGGER = Logger.getLogger(ItemController.class.getName());
    private static final String ATOM_NAMESPACE = "http://www.w3.org/2005/Atom";

    @Autowired
    private FeedRepository feedRepository;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private JAXBContext jaxbContext;

    @Autowired
    private Unmarshaller jaxbUnmarshaller;

    @Autowired
    private Marshaller jaxbMarshaller;

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping(value = "/resume/xml", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public ItemSummaryList getItemSummariesAsXml() {
    	LOGGER.info("Début de la récupération de tous les résumés d'items en XML");
    	try {
    		ItemSummaryList summaries = new ItemSummaryList(itemService.getAllItemSummaries());
    		LOGGER.log(Level.INFO, "Récupération réussie de résumés d'items");
            return summaries;
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de la récupération des résumés d'items: {0}", e.getMessage());
            LOGGER.log(Level.SEVERE, "Stack trace: ", e);
            throw e;
        }
    }

    @GetMapping(value = "/resume/xml/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> getItemByIdAsXml(@PathVariable Long id) {
        LOGGER.log(Level.INFO, "Début de la récupération de l'item avec ID: {0}", id);
        
        try {
            if (id == null) {
                LOGGER.warning("ID fourni est null");
                return ResponseEntity.badRequest()
                        .body(new ErrorResponse(null, "ERROR"));
            }
            
            Optional<Item> item = itemService.getItemById(id);

            if (item.isPresent()) {
                LOGGER.log(Level.INFO, "Item trouvé avec succès pour l'ID: {0}", id);
                return ResponseEntity.ok(item.get());
            } else {
                LOGGER.log(Level.WARNING, "Aucun item trouvé pour l'ID: {0}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse(id, "ERROR"));
            }
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de la récupération de l'item avec ID {0}: {1}", 
                      new Object[]{id, e.getMessage()});
            LOGGER.log(Level.SEVERE, "Stack trace: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(id, "ERROR"));
        }
    }

    @GetMapping("/insert")
    public ModelAndView showInsertForm() {
        return new ModelAndView("insertForm");
    }

    @PostMapping(value = "/insert", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public ResponseEntity<?> insertFeed(@RequestBody String xmlContent) {
        try {
            LOGGER.info("Réception d'une requête d'insertion de flux XML");
            LOGGER.log(Level.INFO, "Contenu XML reçu: {0}", xmlContent);

            if (!validationService.validateXmlAgainstXsd(xmlContent)) {
                LOGGER.warning("Le flux XML ne respecte pas le schéma XSD.");
                return ResponseEntity.badRequest()
                        .body(new ErrorResponse("ERROR"));
            }

            StringReader reader = new StringReader(xmlContent);
            Feed feed = (Feed) jaxbUnmarshaller.unmarshal(reader);

            if (feed == null) {
                throw new JAXBException("Impossible de convertir le contenu XML en Feed");
            }

            Optional<Feed> existingFeed = feedRepository.findByTitleAndPubDate(feed.getTitle(), feed.getPubDate());
            if (existingFeed.isPresent()) {
                 LOGGER.warning("Un flux avec le même titre et la même date existe déjà.");
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ErrorResponse("ERROR"));
            }

            // Lier chaque élément au parent feed
            if (feed.getItems() != null) {
                feed.getItems().forEach(item -> {
                    item.setFeed(feed);
                    item.synchronizeDates();
                    if (item.getAuthors() != null) {
                        item.getAuthors().forEach(author -> author.setItem(item));
                    }
                    if (item.getContributors() != null) {
                        item.getContributors().forEach(contributor -> contributor.setItem(item));
                    }

                });
            }

            if (feed.getLinks() != null) {
                feed.getLinks().forEach(link -> link.setFeed(feed));
            }

            Feed savedFeed = feedRepository.save(feed);

            Long lastItemId = null;
            if (!savedFeed.getItems().isEmpty()) {
                Item lastItem = savedFeed.getItems().get(savedFeed.getItems().size() - 1);
                lastItemId = lastItem.getId();
            }

            LOGGER.info("Flux inséré avec succès, ID: " + (lastItemId != null ? lastItemId : savedFeed.getId()));
            return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse(lastItemId, "INSERTED"));

        } catch (JAXBException e) {
            LOGGER.log(Level.SEVERE, "Erreur JAXB lors du traitement du XML: {0}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("ERROR"));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erreur serveur: {0}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("ERROR"));
        }
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {
        LOGGER.log(Level.INFO, "Début de la suppression de l'item avec ID: {0}", id);
        
        try {
            if (id == null) {
                LOGGER.warning("ID fourni pour la suppression est null");
                ErrorResponse errorResponse = new ErrorResponse(null, "ERROR");
                return ResponseEntity.badRequest().body(errorResponse);
            }
            
            LOGGER.log(Level.INFO, "Appel du service de suppression pour l'item ID: {0}", id);
            ApiResponse response = itemService.deleteItemById(id);
            
            if ("DELETED".equals(response.getStatus())) {
                LOGGER.log(Level.INFO, "Suppression réussie de l'item avec ID: {0}", id);
                return ResponseEntity.ok(response);
            } else {
                LOGGER.log(Level.WARNING, "Échec de la suppression - Item non trouvé avec ID: {0}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de la suppression de l'item avec ID {0}: {1}", 
                      new Object[]{id, e.getMessage()});
            LOGGER.log(Level.SEVERE, "Stack trace: ", e);
            
            ErrorResponse errorResponse = new ErrorResponse(id, "ERROR");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}