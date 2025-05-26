package fr.univrouen.rss25SB.client;

import org.w3c.dom.*;
import org.w3c.dom.Element;

import fr.univrouen.rss25SB.model.Category;
import fr.univrouen.rss25SB.model.Content;
import fr.univrouen.rss25SB.model.Feed;
import fr.univrouen.rss25SB.model.Image;
import fr.univrouen.rss25SB.model.Item;
import fr.univrouen.rss25SB.model.Link;
import fr.univrouen.rss25SB.model.Author;

import javax.xml.parsers.*;
import jakarta.xml.bind.*;
import java.io.*;
import java.net.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

public class RssConverterApp {

	public static final Map<String, String> SOURCES = Map.ofEntries(
		    Map.entry("lemonde", "https://www.lemonde.fr/rss/une.xml"),
		    Map.entry("fonction-publique", "https://www.fonction-publique.gouv.fr/flux-rss-actualites"),
		    Map.entry("franceinfo", "https://www.francetvinfo.fr/titres.rss"),
		    Map.entry("lefigaro", "https://www.lefigaro.fr/rss/figaro_actualites.xml"),
		    Map.entry("bfmtv", "https://www.bfmtv.com/rss/news-24-7/"),
		    Map.entry("futura-sciences", "https://www.futura-sciences.com/rss/actualites.xml")
		);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Sélection de la source
        System.out.println("Sources disponibles :");
        SOURCES.keySet().forEach(key -> System.out.println("- " + key));
        System.out.print("Choisissez une source : ");
        String sourceChoice = scanner.nextLine();
        
        String sourceUrl = SOURCES.get(sourceChoice);
        if (sourceUrl == null) {
            System.err.println("Source inconnue : " + sourceChoice);
            return;
        }

        try {
            System.out.println("Téléchargement du flux depuis : " + sourceUrl);
            convertRssToRss25(sourceUrl, sourceChoice);
        } catch (Exception e) {
            System.err.println("Erreur lors de la conversion : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void convertRssToRss25(String sourceUrl, String sourceName) throws Exception {
        List<Item> rss25Items = new ArrayList<>();

        // Parse XML source
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new URL(sourceUrl).openStream());

        // Récupération des informations du channel
        Element channelElement = (Element) doc.getElementsByTagName("channel").item(0);
        if (channelElement == null) {
            throw new Exception("Aucun élément 'channel' trouvé dans le flux RSS");
        }

        String feedTitle = getText(channelElement, "title");
        String feedLang = getText(channelElement, "language");
        String feedCopyright = getText(channelElement, "copyright");
        String feedPubDateStr = getText(channelElement, "pubDate");

        // Conversion spécifique selon la source
        NodeList itemNodes = doc.getElementsByTagName("item");
        for (int i = 0; i < Math.min(itemNodes.getLength(), 10); i++) {
            Element itemElement = (Element) itemNodes.item(i);
            Item rss25Item = convertItemBasedOnSource(itemElement, sourceName, i + 1);
            rss25Items.add(rss25Item);
        }

        // Création du Feed RSS25
        Feed feed = new Feed();
        feed.setTitle(truncateText(feedTitle, 128));
        feed.setLang(normalizeLang(feedLang));
        feed.setVersion("25");
        feed.setPubDate(convertDateToRfc3339(feedPubDateStr));
        feed.setCopyright(truncateText(feedCopyright.isEmpty() ? "© " + extractDomain(sourceUrl) : feedCopyright, 128));

        // Liens du feed
        List<Link> feedLinks = new ArrayList<>();
        
        Link selfLink = new Link();
        selfLink.setRel("self");
        selfLink.setType("application/rss+xml");
        selfLink.setHref(sourceUrl);
        feedLinks.add(selfLink);

        Link alternateLink = new Link();
        alternateLink.setRel("alternate");
        alternateLink.setType("text/html");
        alternateLink.setHref(extractBaseUrl(sourceUrl));
        feedLinks.add(alternateLink);

        feed.setLinks(feedLinks);

        // Association des items au feed
        rss25Items.forEach(item -> item.setFeed(feed));
        feed.setItems(rss25Items);

        // Export XML RSS25
        JAXBContext context = JAXBContext.newInstance(Feed.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        File output = new File("rss25SB-" + sourceName + ".xml");
        marshaller.marshal(feed, output);

        System.out.println("Conversion terminée : fichier généré ➜ " + output.getAbsolutePath());
        System.out.println("Nombre d'articles convertis : " + rss25Items.size());
    }

    private static Item convertItemBasedOnSource(Element itemElement, String sourceName, int itemIndex) {
        Item item = new Item();

        String title = getText(itemElement, "title");
        String link = getText(itemElement, "link");
        String description = getText(itemElement, "description");
        String pubDateStr = getText(itemElement, "pubDate");

        // GUID obligatoire (URL RFC4266)
        item.setGuid(link.isEmpty() ? generateGuid(sourceName, itemIndex) : link);
        
        // Title obligatoire (max 128 car)
        item.setTitle(truncateText(title.isEmpty() ? "Article sans titre" : title, 128));
        
        // Déterminer s'il faut utiliser published ou updated
        String updatedStr = getText(itemElement, "updated");
        if (!updatedStr.isEmpty()) {
            LocalDateTime updatedDate = convertDateToRfc3339(updatedStr);
            item.setUpdated(updatedDate);
        } else {
            LocalDateTime pubDate = convertDateToRfc3339(pubDateStr);
            item.setPublished(pubDate);
        }

        // Catégories obligatoires (au moins 1)
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(sourceName));
        
        // Catégories spécifiques selon la source
        switch (sourceName) {
        case "lemonde":
            categories.add(new Category("actualite"));
            categories.add(new Category("france"));
            break;
        case "fonction-publique":
            categories.add(new Category("administration"));
            categories.add(new Category("public"));
            break;
        case "franceinfo":
            categories.add(new Category("actualité"));
            categories.add(new Category("radio"));
            break;
        case "lefigaro":
            categories.add(new Category("presse"));
            categories.add(new Category("droite"));
            break;
        case "bfmtv":
            categories.add(new Category("tv"));
            categories.add(new Category("news"));
            break;
        case "futura-sciences":
            categories.add(new Category("sciences"));
            categories.add(new Category("technologie"));
            break;
        default:
            categories.add(new Category("general"));
    }
        item.setCategories(categories);

        // Image optionnelle
        Image image = new Image();
        image.setType("JPEG");
        image.setHref(getDefaultImageForSource(sourceName));
        image.setAlt("Image par défaut pour " + sourceName);
        image.setLength(1024); // Taille fictive
        item.setImage(image);

        // Contenu obligatoire
        Content content = new Content();
        content.setType("text");
        content.setValue(description.isEmpty() ? "Contenu généré automatiquement" : truncateText(description, 500));
        item.setContent(content);

        // Auteur obligatoire (au moins 1)
        List<Author> authors = new ArrayList<>();
        Author author = new Author();
        author.setName(getDefaultAuthorForSource(sourceName));
        author.setEmail(getDefaultEmailForSource(sourceName));
        author.setUri(extractBaseUrl(getText(itemElement, "link")));
        authors.add(author);
        item.setAuthors(authors);

        return item;
    }

    private static String getText(Element parent, String tag) {
        NodeList nodeList = parent.getElementsByTagName(tag);
        if (nodeList.getLength() > 0 && nodeList.item(0).getTextContent() != null) {
            return nodeList.item(0).getTextContent().trim();
        }
        return "";
    }

    private static LocalDateTime convertDateToRfc3339(String dateStr) {
        if (dateStr.isEmpty()) {
            return LocalDateTime.now();
        }
        
        try {
            // Essai format RFC 1123 (RSS standard)
            ZonedDateTime zdt = ZonedDateTime.parse(dateStr, DateTimeFormatter.RFC_1123_DATE_TIME);
            return zdt.toLocalDateTime();
        } catch (Exception e1) {
            try {
                // Essai format ISO
                return LocalDateTime.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            } catch (Exception e2) {
                return LocalDateTime.now();
            }
        }
    }

    private static String truncateText(String text, int maxLength) {
        if (text == null || text.length() <= maxLength) {
            return text;
        }
        return text.substring(0, maxLength - 3) + "...";
    }

    private static String normalizeLang(String lang) {
        if (lang.isEmpty()) {
            return "fr";
        }
        // Conversion au format IANA (xx[x][-yy[y]])
        lang = lang.toLowerCase();
        if (lang.equals("fr-fr")) return "fr-FR";
        if (lang.equals("en-us")) return "en-US";
        return lang;
    }

    private static String generateGuid(String sourceName, int index) {
        return "https://generated-guid.example.com/" + sourceName + "/" + index + "/" + System.currentTimeMillis();
    }

    private static String getDefaultImageForSource(String sourceName) {
        switch (sourceName) {
            case "lemonde":
                return "https://www.lemonde.fr/dist/assets/img/icons/icon-192x192.png";
            case "fonction-publique":
                return "https://www.fonction-publique.gouv.fr/themes/custom/marianne/images/logo.png";
            case "franceinfo":
                return "https://www.francetvinfo.fr/favicon.ico";
            case "lefigaro":
                return "https://www.lefigaro.fr/assets/img/favicon/favicon-32x32.png";
            case "bfmtv":
                return "https://www.bfmtv.com/favicon.ico";
            case "futura-sciences":
                return "https://www.futura-sciences.com/favicon.ico";
                
            default:
                return "https://example.com/default-image.jpg";
        }
    }

    private static String getDefaultAuthorForSource(String sourceName) {
        switch (sourceName) {
            case "lemonde":
                return "Rédaction Le Monde";
            case "fonction-publique":
                return "Fonction Publique";
            case "franceinfo":
                return "Rédaction France Info";
            case "lefigaro":
                return "Le Figaro";
            case "bfmtv":
                return "BFM TV";
            case "futura-sciences":
                return "Futura Sciences";    
            default:
                return "Auteur anonyme";
                
        }
    }

    private static String getDefaultEmailForSource(String sourceName) {
        switch (sourceName) {
        case "lemonde":
            return "contact@lemonde.fr";
        case "fonction-publique":
            return "communication@fonction-publique.gouv.fr";
        case "franceinfo":
            return "redaction@radiofrance.com";
        case "lefigaro":
            return "redaction@lefigaro.fr";
        case "bfmtv":
            return "redaction@bfmtv.fr";
        case "futura-sciences":
            return "contact@futura-sciences.com";
        default:
            return "contact@default-source.org";
        }
    }

    private static String extractDomain(String url) {
        try {
            URL urlObj = new URL(url);
            return urlObj.getHost();
        } catch (Exception e) {
            return "unknown";
        }
    }

    private static String extractBaseUrl(String url) {
        try {
            URL urlObj = new URL(url);
            return urlObj.getProtocol() + "://" + urlObj.getHost();
        } catch (Exception e) {
            return "https://example.com";
        }
    }
}