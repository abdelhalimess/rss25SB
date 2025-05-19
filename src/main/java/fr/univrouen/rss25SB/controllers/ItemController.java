package fr.univrouen.rss25SB.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import fr.univrouen.rss25SB.model.ItemSummaryList;
import fr.univrouen.rss25SB.service.ItemService;
import fr.univrouen.rss25SB.model.Feed;
import fr.univrouen.rss25SB.model.Item;
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
        return new ItemSummaryList(itemService.getAllItemSummaries());
    }

    @GetMapping("/insert")
    public ModelAndView showInsertForm() {
        return new ModelAndView("insertForm");
    }

    @PostMapping(value = "/insert", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public ResponseEntity<String> insertFeed(@RequestBody String xmlContent) {
        try {
            LOGGER.info("Réception d'une requête d'insertion de flux XML");
            LOGGER.log(Level.INFO, "Contenu XML reçu: {0}", xmlContent);

            if (!validationService.validateXmlAgainstXsd(xmlContent)) {
                LOGGER.warning("Le flux XML ne respecte pas le schéma XSD.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(createErrorResponse("Le flux XML ne respecte pas le schéma XSD."));
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
                        .body(createErrorResponse("Un flux avec le même titre et la même date existe déjà."));
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

            String responseXml = createSuccessResponse((lastItemId != null ? lastItemId : savedFeed.getId()));
            LOGGER.info("Flux inséré avec succès, ID: " + (lastItemId != null ? lastItemId : savedFeed.getId()));
            return ResponseEntity.status(HttpStatus.CREATED).body(responseXml);

        } catch (JAXBException e) {
            LOGGER.log(Level.SEVERE, "Erreur JAXB lors du traitement du XML: {0}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(createErrorResponse("Erreur lors du traitement du XML: " + e.getMessage()));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erreur serveur: {0}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Erreur serveur: " + e.getMessage()));
        }
    }

    private String createErrorResponse(String message) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
               "<response><status>ERROR</status><message>" + message + "</message></response>";
    }

    private String createSuccessResponse(Long id) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
               "<response><id>" + id + "</id><status>INSERTED</status></response>";
    }
}